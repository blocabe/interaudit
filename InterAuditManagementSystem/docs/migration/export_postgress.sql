--
-- PostgreSQL database dump
--

-- Started on 2010-12-27 11:01:27

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 7 (class 2615 OID 2200)
-- Name: interaudit; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA interaudit;


ALTER SCHEMA interaudit OWNER TO postgres;

--
-- TOC entry 2240 (class 0 OID 0)
-- Dependencies: 7
-- Name: SCHEMA interaudit; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA interaudit IS 'standard public schema';


--
-- TOC entry 5 (class 2615 OID 17209)
-- Name: pgagent; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA pgagent;


ALTER SCHEMA pgagent OWNER TO postgres;

--
-- TOC entry 2242 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA pgagent; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA pgagent IS 'pgAgent system tables';


--
-- TOC entry 447 (class 2612 OID 17208)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = pgagent, pg_catalog;

--
-- TOC entry 25 (class 1255 OID 17378)
-- Dependencies: 447 5
-- Name: pga_exception_trigger(); Type: FUNCTION; Schema: pgagent; Owner: postgres
--

CREATE FUNCTION pga_exception_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE

    jobid int4 := 0;

BEGIN

     IF TG_OP = 'DELETE' THEN

        SELECT INTO jobid jscjobid FROM pgagent.pga_schedule WHERE jscid = OLD.jexscid;

        -- update pga_job from remaining schedules
        -- the actual calculation of jobnextrun will be performed in the trigger
        UPDATE pgagent.pga_job
           SET jobnextrun = NULL
         WHERE jobenabled AND jobid=jobid;
        RETURN OLD;
    ELSE

        SELECT INTO jobid jscjobid FROM pgagent.pga_schedule WHERE jscid = NEW.jexscid;

        UPDATE pgagent.pga_job
           SET jobnextrun = NULL
         WHERE jobenabled AND jobid=jobid;
        RETURN NEW;
    END IF;
END;
$$;


ALTER FUNCTION pgagent.pga_exception_trigger() OWNER TO postgres;

--
-- TOC entry 2243 (class 0 OID 0)
-- Dependencies: 25
-- Name: FUNCTION pga_exception_trigger(); Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON FUNCTION pga_exception_trigger() IS 'Update the job''s next run time whenever an exception changes';


--
-- TOC entry 22 (class 1255 OID 17373)
-- Dependencies: 447 5
-- Name: pga_is_leap_year(smallint); Type: FUNCTION; Schema: pgagent; Owner: postgres
--

CREATE FUNCTION pga_is_leap_year(smallint) RETURNS boolean
    LANGUAGE plpgsql IMMUTABLE
    AS $_$
BEGIN
    IF $1 % 4 != 0 THEN
        RETURN FALSE;
    END IF;

    IF $1 % 100 != 0 THEN
        RETURN TRUE;
    END IF;

    RETURN $1 % 400 = 0;
END;
$_$;


ALTER FUNCTION pgagent.pga_is_leap_year(smallint) OWNER TO postgres;

--
-- TOC entry 2244 (class 0 OID 0)
-- Dependencies: 22
-- Name: FUNCTION pga_is_leap_year(smallint); Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON FUNCTION pga_is_leap_year(smallint) IS 'Returns TRUE is $1 is a leap year';


--
-- TOC entry 23 (class 1255 OID 17374)
-- Dependencies: 5 447
-- Name: pga_job_trigger(); Type: FUNCTION; Schema: pgagent; Owner: postgres
--

CREATE FUNCTION pga_job_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.jobenabled THEN
        IF NEW.jobnextrun IS NULL THEN
             SELECT INTO NEW.jobnextrun
                    MIN(pgagent.pga_next_schedule(jscid, jscstart, jscend, jscminutes, jschours, jscweekdays, jscmonthdays, jscmonths))
               FROM pgagent.pga_schedule
              WHERE jscenabled AND jscjobid=OLD.jobid;
        END IF;
    ELSE
        NEW.jobnextrun := NULL;
    END IF;
    RETURN NEW;
END;
$$;


ALTER FUNCTION pgagent.pga_job_trigger() OWNER TO postgres;

--
-- TOC entry 2245 (class 0 OID 0)
-- Dependencies: 23
-- Name: FUNCTION pga_job_trigger(); Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON FUNCTION pga_job_trigger() IS 'Update the job''s next run time.';


--
-- TOC entry 21 (class 1255 OID 17371)
-- Dependencies: 447 5
-- Name: pga_next_schedule(integer, timestamp with time zone, timestamp with time zone, boolean[], boolean[], boolean[], boolean[], boolean[]); Type: FUNCTION; Schema: pgagent; Owner: postgres
--

CREATE FUNCTION pga_next_schedule(integer, timestamp with time zone, timestamp with time zone, boolean[], boolean[], boolean[], boolean[], boolean[]) RETURNS timestamp with time zone
    LANGUAGE plpgsql
    AS $_$
DECLARE
    jscid           ALIAS FOR $1;
    jscstart        ALIAS FOR $2;
    jscend          ALIAS FOR $3;
    jscminutes      ALIAS FOR $4;
    jschours        ALIAS FOR $5;
    jscweekdays     ALIAS FOR $6;
    jscmonthdays    ALIAS FOR $7;
    jscmonths       ALIAS FOR $8;

    nextrun         timestamp := '1970-01-01 00:00:00-00';
    runafter        timestamp := '1970-01-01 00:00:00-00';

    bingo            bool := FALSE;
    gotit            bool := FALSE;
    foundval        bool := FALSE;
    daytweak        bool := FALSE;
    minutetweak        bool := FALSE;

    i                int2 := 0;
    d                int2 := 0;

    nextminute        int2 := 0;
    nexthour        int2 := 0;
    nextday            int2 := 0;
    nextmonth       int2 := 0;
    nextyear        int2 := 0;


BEGIN
    -- No valid start date has been specified
    IF jscstart IS NULL THEN RETURN NULL; END IF;

    -- The schedule is past its end date
    IF jscend IS NOT NULL AND jscend < now() THEN RETURN NULL; END IF;

    -- Get the time to find the next run after. It will just be the later of
    -- now() + 1m and the start date for the time being, however, we might want to
    -- do more complex things using this value in the future.
    IF date_trunc('MINUTE', jscstart) > date_trunc('MINUTE', (now() + '1 Minute'::interval)) THEN
        runafter := date_trunc('MINUTE', jscstart);
    ELSE
        runafter := date_trunc('MINUTE', (now() + '1 Minute'::interval));
    END IF;

    --
    -- Enter a loop, generating next run timestamps until we find one
    -- that falls on the required weekday, and is not matched by an exception
    --

    WHILE bingo = FALSE LOOP

        --
        -- Get the next run year
        --
        nextyear := date_part('YEAR', runafter);

        --
        -- Get the next run month
        --
        nextmonth := date_part('MONTH', runafter);
        gotit := FALSE;
        FOR i IN (nextmonth) .. 12 LOOP
            IF jscmonths[i] = TRUE THEN
                nextmonth := i;
                gotit := TRUE;
                foundval := TRUE;
                EXIT;
            END IF;
        END LOOP;
        IF gotit = FALSE THEN
            FOR i IN 1 .. (nextmonth - 1) LOOP
                IF jscmonths[i] = TRUE THEN
                    nextmonth := i;

                    -- Wrap into next year
                    nextyear := nextyear + 1;
                    gotit := TRUE;
                    foundval := TRUE;
                    EXIT;
                END IF;
           END LOOP;
        END IF;

        --
        -- Get the next run day
        --
        -- If the year, or month have incremented, get the lowest day,
        -- otherwise look for the next day matching or after today.
        IF (nextyear > date_part('YEAR', runafter) OR nextmonth > date_part('MONTH', runafter)) THEN
            nextday := 1;
            FOR i IN 1 .. 32 LOOP
                IF jscmonthdays[i] = TRUE THEN
                    nextday := i;
                    foundval := TRUE;
                    EXIT;
                END IF;
            END LOOP;
        ELSE
            nextday := date_part('DAY', runafter);
            gotit := FALSE;
            FOR i IN nextday .. 32 LOOP
                IF jscmonthdays[i] = TRUE THEN
                    nextday := i;
                    gotit := TRUE;
                    foundval := TRUE;
                    EXIT;
                END IF;
            END LOOP;
            IF gotit = FALSE THEN
                FOR i IN 1 .. (nextday - 1) LOOP
                    IF jscmonthdays[i] = TRUE THEN
                        nextday := i;

                        -- Wrap into next month
                        IF nextmonth = 12 THEN
                            nextyear := nextyear + 1;
                            nextmonth := 1;
                        ELSE
                            nextmonth := nextmonth + 1;
                        END IF;
                        gotit := TRUE;
                        foundval := TRUE;
                        EXIT;
                    END IF;
                END LOOP;
            END IF;
        END IF;

        -- Was the last day flag selected?
        IF nextday = 32 THEN
            IF nextmonth = 1 THEN
                nextday := 31;
            ELSIF nextmonth = 2 THEN
                IF pgagent.pga_is_leap_year(nextyear) = TRUE THEN
                    nextday := 29;
                ELSE
                    nextday := 28;
                END IF;
            ELSIF nextmonth = 3 THEN
                nextday := 31;
            ELSIF nextmonth = 4 THEN
                nextday := 30;
            ELSIF nextmonth = 5 THEN
                nextday := 31;
            ELSIF nextmonth = 6 THEN
                nextday := 30;
            ELSIF nextmonth = 7 THEN
                nextday := 31;
            ELSIF nextmonth = 8 THEN
                nextday := 31;
            ELSIF nextmonth = 9 THEN
                nextday := 30;
            ELSIF nextmonth = 10 THEN
                nextday := 31;
            ELSIF nextmonth = 11 THEN
                nextday := 30;
            ELSIF nextmonth = 12 THEN
                nextday := 31;
            END IF;
        END IF;

        --
        -- Get the next run hour
        --
        -- If the year, month or day have incremented, get the lowest hour,
        -- otherwise look for the next hour matching or after the current one.
        IF (nextyear > date_part('YEAR', runafter) OR nextmonth > date_part('MONTH', runafter) OR nextday > date_part('DAY', runafter) OR daytweak = TRUE) THEN
            nexthour := 0;
            FOR i IN 1 .. 24 LOOP
                IF jschours[i] = TRUE THEN
                    nexthour := i - 1;
                    foundval := TRUE;
                    EXIT;
                END IF;
            END LOOP;
        ELSE
            nexthour := date_part('HOUR', runafter);
            gotit := FALSE;
            FOR i IN (nexthour + 1) .. 24 LOOP
                IF jschours[i] = TRUE THEN
                    nexthour := i - 1;
                    gotit := TRUE;
                    foundval := TRUE;
                    EXIT;
                END IF;
            END LOOP;
            IF gotit = FALSE THEN
                FOR i IN 1 .. nexthour LOOP
                    IF jschours[i] = TRUE THEN
                        nexthour := i - 1;

                        -- Wrap into next month
                        IF (nextmonth = 1 OR nextmonth = 3 OR nextmonth = 5 OR nextmonth = 7 OR nextmonth = 8 OR nextmonth = 10 OR nextmonth = 12) THEN
                            d = 31;
                        ELSIF (nextmonth = 4 OR nextmonth = 6 OR nextmonth = 9 OR nextmonth = 11) THEN
                            d = 30;
                        ELSE
                            IF pgagent.pga_is_leap_year(nextyear) = TRUE THEN
                                d := 29;
                            ELSE
                                d := 28;
                            END IF;
                        END IF;

                        IF nextday = d THEN
                            nextday := 1;
                            IF nextmonth = 12 THEN
                                nextyear := nextyear + 1;
                                nextmonth := 1;
                            ELSE
                                nextmonth := nextmonth + 1;
                            END IF;
                        ELSE
                            nextday := nextday + 1;
                        END IF;

                        gotit := TRUE;
                        foundval := TRUE;
                        EXIT;
                    END IF;
                END LOOP;
            END IF;
        END IF;

        --
        -- Get the next run minute
        --
        -- If the year, month day or hour have incremented, get the lowest minute,
        -- otherwise look for the next minute matching or after the current one.
        IF (nextyear > date_part('YEAR', runafter) OR nextmonth > date_part('MONTH', runafter) OR nextday > date_part('DAY', runafter) OR nexthour > date_part('HOUR', runafter) OR daytweak = TRUE) THEN
            nextminute := 0;
            IF minutetweak = TRUE THEN
        d := 1;
            ELSE
        d := date_part('YEAR', runafter)::int2;
            END IF;
            FOR i IN d .. 60 LOOP
                IF jscminutes[i] = TRUE THEN
                    nextminute := i - 1;
                    foundval := TRUE;
                    EXIT;
                END IF;
            END LOOP;
        ELSE
            nextminute := date_part('MINUTE', runafter);
            gotit := FALSE;
            FOR i IN (nextminute + 1) .. 60 LOOP
                IF jscminutes[i] = TRUE THEN
                    nextminute := i - 1;
                    gotit := TRUE;
                    foundval := TRUE;
                    EXIT;
                END IF;
            END LOOP;
            IF gotit = FALSE THEN
                FOR i IN 1 .. nextminute LOOP
                    IF jscminutes[i] = TRUE THEN
                        nextminute := i - 1;

                        -- Wrap into next hour
                        IF (nextmonth = 1 OR nextmonth = 3 OR nextmonth = 5 OR nextmonth = 7 OR nextmonth = 8 OR nextmonth = 10 OR nextmonth = 12) THEN
                            d = 31;
                        ELSIF (nextmonth = 4 OR nextmonth = 6 OR nextmonth = 9 OR nextmonth = 11) THEN
                            d = 30;
                        ELSE
                            IF pgagent.pga_is_leap_year(nextyear) = TRUE THEN
                                d := 29;
                            ELSE
                                d := 28;
                            END IF;
                        END IF;

                        IF nexthour = 23 THEN
                            nexthour = 0;
                            IF nextday = d THEN
                                nextday := 1;
                                IF nextmonth = 12 THEN
                                    nextyear := nextyear + 1;
                                    nextmonth := 1;
                                ELSE
                                    nextmonth := nextmonth + 1;
                                END IF;
                            ELSE
                                nextday := nextday + 1;
                            END IF;
                        ELSE
                            nexthour := nexthour + 1;
                        END IF;

                        gotit := TRUE;
                        foundval := TRUE;
                        EXIT;
                    END IF;
                END LOOP;
            END IF;
        END IF;

        -- Build the result, and check it is not the same as runafter - this may
        -- happen if all array entries are set to false. In this case, add a minute.

        nextrun := (nextyear::varchar || '-'::varchar || nextmonth::varchar || '-' || nextday::varchar || ' ' || nexthour::varchar || ':' || nextminute::varchar)::timestamptz;

        IF nextrun = runafter AND foundval = FALSE THEN
                nextrun := nextrun + INTERVAL '1 Minute';
        END IF;

        -- If the result is past the end date, exit.
        IF nextrun > jscend THEN
            RETURN NULL;
        END IF;

        -- Check to ensure that the nextrun time is actually still valid. Its
        -- possible that wrapped values may have carried the nextrun onto an
        -- invalid time or date.
        IF ((jscminutes = '{f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f}' OR jscminutes[date_part('MINUTE', nextrun) + 1] = TRUE) AND
            (jschours = '{f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f}' OR jschours[date_part('HOUR', nextrun) + 1] = TRUE) AND
            (jscmonthdays = '{f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f}' OR jscmonthdays[date_part('DAY', nextrun)] = TRUE OR
            (jscmonthdays = '{f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t}' AND
             ((date_part('MONTH', nextrun) IN (1,3,5,7,8,10,12) AND date_part('DAY', nextrun) = 31) OR
              (date_part('MONTH', nextrun) IN (4,6,9,11) AND date_part('DAY', nextrun) = 30) OR
              (date_part('MONTH', nextrun) = 2 AND ((pgagent.pga_is_leap_year(date_part('DAY', nextrun)::int2) AND date_part('DAY', nextrun) = 29) OR date_part('DAY', nextrun) = 28))))) AND
            (jscmonths = '{f,f,f,f,f,f,f,f,f,f,f,f}' OR jscmonths[date_part('MONTH', nextrun)] = TRUE)) THEN


            -- Now, check to see if the nextrun time found is a) on an acceptable
            -- weekday, and b) not matched by an exception. If not, set
            -- runafter = nextrun and try again.

            -- Check for a wildcard weekday
            gotit := FALSE;
            FOR i IN 1 .. 7 LOOP
                IF jscweekdays[i] = TRUE THEN
                    gotit := TRUE;
                    EXIT;
                END IF;
            END LOOP;

            -- OK, is the correct weekday selected, or a wildcard?
            IF (jscweekdays[date_part('DOW', nextrun) + 1] = TRUE OR gotit = FALSE) THEN

                -- Check for exceptions
                SELECT INTO d jexid FROM pgagent.pga_exception WHERE jexscid = jscid AND ((jexdate = nextrun::date AND jextime = nextrun::time) OR (jexdate = nextrun::date AND jextime IS NULL) OR (jexdate IS NULL AND jextime = nextrun::time));
                IF FOUND THEN
                    -- Nuts - found an exception. Increment the time and try again
                    runafter := nextrun + INTERVAL '1 Minute';
                    bingo := FALSE;
                    minutetweak := TRUE;
            daytweak := FALSE;
                ELSE
                    bingo := TRUE;
                END IF;
            ELSE
                -- We're on the wrong week day - increment a day and try again.
                runafter := nextrun + INTERVAL '1 Day';
                bingo := FALSE;
                minutetweak := FALSE;
                daytweak := TRUE;
            END IF;

        ELSE
            runafter := nextrun + INTERVAL '1 Minute';
            bingo := FALSE;
            minutetweak := TRUE;
        daytweak := FALSE;
        END IF;

    END LOOP;

    RETURN nextrun;
END;
$_$;


ALTER FUNCTION pgagent.pga_next_schedule(integer, timestamp with time zone, timestamp with time zone, boolean[], boolean[], boolean[], boolean[], boolean[]) OWNER TO postgres;

--
-- TOC entry 2246 (class 0 OID 0)
-- Dependencies: 21
-- Name: FUNCTION pga_next_schedule(integer, timestamp with time zone, timestamp with time zone, boolean[], boolean[], boolean[], boolean[], boolean[]); Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON FUNCTION pga_next_schedule(integer, timestamp with time zone, timestamp with time zone, boolean[], boolean[], boolean[], boolean[], boolean[]) IS 'Calculates the next runtime for a given schedule';


--
-- TOC entry 24 (class 1255 OID 17376)
-- Dependencies: 447 5
-- Name: pga_schedule_trigger(); Type: FUNCTION; Schema: pgagent; Owner: postgres
--

CREATE FUNCTION pga_schedule_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF TG_OP = 'DELETE' THEN
        -- update pga_job from remaining schedules
        -- the actual calculation of jobnextrun will be performed in the trigger
        UPDATE pgagent.pga_job
           SET jobnextrun = NULL
         WHERE jobenabled AND jobid=OLD.jscjobid;
        RETURN OLD;
    ELSE
        UPDATE pgagent.pga_job
           SET jobnextrun = NULL
         WHERE jobenabled AND jobid=NEW.jscjobid;
        RETURN NEW;
    END IF;
END;
$$;


ALTER FUNCTION pgagent.pga_schedule_trigger() OWNER TO postgres;

--
-- TOC entry 2247 (class 0 OID 0)
-- Dependencies: 24
-- Name: FUNCTION pga_schedule_trigger(); Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON FUNCTION pga_schedule_trigger() IS 'Update the job''s next run time whenever a schedule changes';


--
-- TOC entry 20 (class 1255 OID 17370)
-- Dependencies: 5 447
-- Name: pgagent_schema_version(); Type: FUNCTION; Schema: pgagent; Owner: postgres
--

CREATE FUNCTION pgagent_schema_version() RETURNS smallint
    LANGUAGE plpgsql
    AS $$
BEGIN
    -- RETURNS PGAGENT MAJOR VERSION
    -- WE WILL CHANGE THE MAJOR VERSION, ONLY IF THERE IS A SCHEMA CHANGE
    RETURN 3;
END;
$$;


ALTER FUNCTION pgagent.pgagent_schema_version() OWNER TO postgres;

SET search_path = interaudit, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1652 (class 1259 OID 17429)
-- Dependencies: 7
-- Name: access_rights; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE access_rights (
    id bigint NOT NULL,
    active boolean NOT NULL,
    fk_employee bigint NOT NULL,
    fk_right bigint NOT NULL
);


ALTER TABLE interaudit.access_rights OWNER TO postgres;

--
-- TOC entry 1653 (class 1259 OID 17434)
-- Dependencies: 7
-- Name: activities; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE activities (
    id bigint NOT NULL,
    status character varying(255),
    todo character varying(255),
    comments character varying(255),
    version integer NOT NULL,
    ordre integer NOT NULL,
    startdate date NOT NULL,
    updatedate date,
    enddate date,
    mission_id bigint,
    task_id bigint
);


ALTER TABLE interaudit.activities OWNER TO postgres;

--
-- TOC entry 1654 (class 1259 OID 17442)
-- Dependencies: 7
-- Name: banks; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE banks (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    code character varying(255) NOT NULL,
    account character varying(255) NOT NULL,
    contactperson character varying(255),
    contactpersonemail character varying(255),
    contactpersonphone character varying(255),
    contactpersonfax character varying(255),
    active boolean NOT NULL
);


ALTER TABLE interaudit.banks OWNER TO postgres;

--
-- TOC entry 1655 (class 1259 OID 17456)
-- Dependencies: 7
-- Name: budgets; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE budgets (
    id bigint NOT NULL,
    version integer NOT NULL,
    exp_amount double precision NOT NULL,
    real_amount double precision,
    rep_amount double precision,
    comments character varying(255),
    status character varying(255),
    fiable boolean NOT NULL,
    fk_exercise bigint NOT NULL,
    fk_contract bigint NOT NULL,
    fk_associe bigint NOT NULL,
    fk_manager bigint NOT NULL
);


ALTER TABLE interaudit.budgets OWNER TO postgres;

--
-- TOC entry 1656 (class 1259 OID 17464)
-- Dependencies: 7
-- Name: comments; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE comments (
    id bigint NOT NULL,
    text text,
    created timestamp without time zone,
    sent timestamp without time zone,
    sender_address character varying(255),
    receiver_address character varying(255),
    fk_employee bigint NOT NULL,
    fk_timesheet bigint NOT NULL
);


ALTER TABLE interaudit.comments OWNER TO postgres;

--
-- TOC entry 1657 (class 1259 OID 17472)
-- Dependencies: 7
-- Name: contacts; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE contacts (
    id bigint NOT NULL,
    sex character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    f_name character varying(255) NOT NULL,
    l_name character varying(255) NOT NULL,
    job character varying(255),
    b_phone character varying(255),
    b_mobile character varying(255),
    b_fax character varying(255),
    p_phone character varying(255) NOT NULL,
    p_mobile character varying(255),
    b_email character varying(255),
    p_email character varying(255),
    b_web_addr character varying(255),
    active boolean NOT NULL,
    b_activity character varying(255),
    updateduser character varying(255),
    company character varying(255) NOT NULL,
    address character varying(255),
    createdate date,
    updatedate date,
    version integer NOT NULL,
    fk_customer bigint NOT NULL
);


ALTER TABLE interaudit.contacts OWNER TO postgres;

--
-- TOC entry 1658 (class 1259 OID 17480)
-- Dependencies: 7
-- Name: contracts; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE contracts (
    id bigint NOT NULL,
    reference character varying(255),
    description character varying(2000),
    fromdate timestamp without time zone,
    todate timestamp without time zone,
    duration integer,
    language character varying(255),
    val double precision NOT NULL,
    cur character varying(255) NOT NULL,
    version integer NOT NULL,
    missiontype character varying(255) NOT NULL,
    agreed boolean,
    fk_customer bigint
);


ALTER TABLE interaudit.contracts OWNER TO postgres;

--
-- TOC entry 1659 (class 1259 OID 17488)
-- Dependencies: 7
-- Name: customers; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE customers (
    id bigint NOT NULL,
    web_address character varying(255),
    active boolean NOT NULL,
    country character varying(255),
    mainaddress character varying(500),
    mainbillingaddress character varying(500),
    activity character varying(2000),
    createdate date,
    updatedate date,
    updateduser character varying(255),
    compy_name character varying(255),
    fax character varying(255),
    phone character varying(255),
    mobile character varying(255),
    p_phone character varying(255),
    p_mobile character varying(255),
    email character varying(255),
    code character varying(255) NOT NULL,
    version integer NOT NULL,
    fk_associe bigint NOT NULL,
    fk_manager bigint NOT NULL,
    fk_origin bigint,
    fk_contracttype bigint,
    contract_amount double precision
);


ALTER TABLE interaudit.customers OWNER TO postgres;

--
-- TOC entry 1660 (class 1259 OID 17500)
-- Dependencies: 7
-- Name: declarations; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE declarations (
    id bigint NOT NULL,
    exercise character varying(255),
    customer character varying(255),
    requestdate timestamp without time zone,
    receiptdate timestamp without time zone,
    validitydate timestamp without time zone,
    declaration boolean NOT NULL,
    passport boolean NOT NULL,
    active boolean NOT NULL
);


ALTER TABLE interaudit.declarations OWNER TO postgres;

--
-- TOC entry 1649 (class 1259 OID 17418)
-- Dependencies: 7
-- Name: docs_invoices; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE docs_invoices (
    id_invoice bigint NOT NULL,
    id_document bigint NOT NULL
);


ALTER TABLE interaudit.docs_invoices OWNER TO postgres;

--
-- TOC entry 1650 (class 1259 OID 17421)
-- Dependencies: 7
-- Name: docs_missions; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE docs_missions (
    id_mission bigint NOT NULL,
    id_document bigint NOT NULL
);


ALTER TABLE interaudit.docs_missions OWNER TO postgres;

--
-- TOC entry 1661 (class 1259 OID 17508)
-- Dependencies: 7
-- Name: documents; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE documents (
    id bigint NOT NULL,
    file_name character varying(255),
    description character varying(255),
    createdate timestamp without time zone,
    updatedate timestamp without time zone,
    title character varying(255),
    version integer NOT NULL,
    owner bigint
);


ALTER TABLE interaudit.documents OWNER TO postgres;

--
-- TOC entry 1662 (class 1259 OID 17516)
-- Dependencies: 7
-- Name: email_data; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE email_data (
    id bigint NOT NULL,
    sender_address character varying(255),
    receiver_address character varying(255),
    mailsubject character varying(255),
    mailcontents character varying(255),
    created timestamp without time zone,
    sentdate timestamp without time zone,
    status character varying(255),
    type character varying(255),
    fk_sender bigint,
    fk_receiver bigint
);


ALTER TABLE interaudit.email_data OWNER TO postgres;

--
-- TOC entry 1663 (class 1259 OID 17524)
-- Dependencies: 7
-- Name: employees; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE employees (
    id bigint NOT NULL,
    version integer NOT NULL,
    firstname character varying(255),
    lastname character varying(255),
    jobtitle character varying(255),
    email character varying(255),
    code character varying(255),
    active boolean,
    password character varying(255),
    login character varying(255),
    updateuser character varying(255),
    date_of_hiring date,
    createdate timestamp without time zone,
    modifieddate timestamp without time zone,
    prixvente double precision NOT NULL,
    prixrevient double precision NOT NULL,
    couthoraire double precision NOT NULL,
    fk_position bigint
);


ALTER TABLE interaudit.employees OWNER TO postgres;

--
-- TOC entry 1665 (class 1259 OID 17542)
-- Dependencies: 7
-- Name: event_planning; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE event_planning (
    id bigint NOT NULL,
    year integer NOT NULL,
    weeknumber integer NOT NULL,
    validated boolean NOT NULL,
    fk_employee bigint NOT NULL,
    fk_planning bigint NOT NULL
);


ALTER TABLE interaudit.event_planning OWNER TO postgres;

--
-- TOC entry 1664 (class 1259 OID 17534)
-- Dependencies: 7
-- Name: events; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE events (
    id bigint NOT NULL,
    year integer NOT NULL,
    month integer NOT NULL,
    day integer NOT NULL,
    starthour integer NOT NULL,
    endhour integer NOT NULL,
    type character varying(255),
    title character varying(255),
    dateofevent timestamp without time zone,
    valid boolean NOT NULL,
    fk_employee bigint NOT NULL,
    fk_activity bigint,
    task_id bigint
);


ALTER TABLE interaudit.events OWNER TO postgres;

--
-- TOC entry 1666 (class 1259 OID 17547)
-- Dependencies: 7
-- Name: exercises; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE exercises (
    id bigint NOT NULL,
    version integer NOT NULL,
    year integer NOT NULL,
    status character varying(255),
    startdate timestamp without time zone,
    enddate timestamp without time zone,
    isappproved boolean NOT NULL,
    tot_exp_amount double precision NOT NULL,
    inflationpercentage real NOT NULL,
    tot_rep_amount double precision NOT NULL,
    tot_real_amount double precision,
    tot_inactif_amount double precision
);


ALTER TABLE interaudit.exercises OWNER TO postgres;

--
-- TOC entry 1687 (class 1259 OID 17991)
-- Dependencies: 7
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: interaudit; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE interaudit.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 2248 (class 0 OID 0)
-- Dependencies: 1687
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: interaudit; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 414, true);


--
-- TOC entry 1668 (class 1259 OID 17564)
-- Dependencies: 7
-- Name: invoice_deduction_fees; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE invoice_deduction_fees (
    id bigint NOT NULL,
    createdate date,
    codejustification character varying(255),
    justification character varying(255),
    value double precision NOT NULL,
    version integer NOT NULL,
    fk_facture bigint NOT NULL
);


ALTER TABLE interaudit.invoice_deduction_fees OWNER TO postgres;

--
-- TOC entry 1669 (class 1259 OID 17572)
-- Dependencies: 7
-- Name: invoice_fees; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE invoice_fees (
    id bigint NOT NULL,
    createdate date,
    codejustification character varying(255),
    justification character varying(255),
    value double precision NOT NULL,
    version integer NOT NULL,
    fk_facture bigint NOT NULL
);


ALTER TABLE interaudit.invoice_fees OWNER TO postgres;

--
-- TOC entry 1670 (class 1259 OID 17580)
-- Dependencies: 7
-- Name: invoice_reminds; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE invoice_reminds (
    id bigint NOT NULL,
    reminddate date,
    sent boolean,
    libelle character varying(255) NOT NULL,
    libfin character varying(255) NOT NULL,
    lang character varying(255) NOT NULL,
    ordre integer NOT NULL,
    year integer NOT NULL,
    refasssec character varying(255),
    version integer NOT NULL,
    fk_sender bigint,
    fk_customer bigint NOT NULL
);


ALTER TABLE interaudit.invoice_reminds OWNER TO postgres;

--
-- TOC entry 1651 (class 1259 OID 17426)
-- Dependencies: 7
-- Name: invoice_reminds_relation; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE invoice_reminds_relation (
    remindid bigint NOT NULL,
    invoiceid bigint NOT NULL
);


ALTER TABLE interaudit.invoice_reminds_relation OWNER TO postgres;

--
-- TOC entry 1688 (class 1259 OID 18000)
-- Dependencies: 7
-- Name: invoice_translations; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE invoice_translations (
    key character varying(100),
    lang character varying(10),
    value character varying(2000)
);


ALTER TABLE interaudit.invoice_translations OWNER TO postgres;

--
-- TOC entry 1667 (class 1259 OID 17554)
-- Dependencies: 7
-- Name: invoices; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE invoices (
    id bigint NOT NULL,
    version integer NOT NULL,
    type character varying(255),
    language character varying(255),
    tva double precision NOT NULL,
    delaipaiement integer NOT NULL,
    datefacture date,
    sentdate date,
    dateofpayment date,
    currency character varying(3) NOT NULL,
    year character varying(255) NOT NULL,
    mois character varying(255) NOT NULL,
    libelle character varying(255) NOT NULL,
    honoraires double precision NOT NULL,
    amount double precision NOT NULL,
    amount_net double precision NOT NULL,
    reference character varying(255) NOT NULL,
    parent_reference character varying(255),
    refasssec character varying(255),
    status character varying(255),
    pays character varying(255) NOT NULL,
    libhonoraires character varying(255) NOT NULL,
    libdelai character varying(255) NOT NULL,
    billaddress character varying(255) NOT NULL,
    custcode character varying(255) NOT NULL,
    custname character varying(255) NOT NULL,
    approved boolean,
    sent boolean,
    exercisemandat character varying(255),
    bank_id bigint,
    project_id bigint,
    fk_sender bigint,
    fk_partner bigint NOT NULL,
    fk_creator bigint NOT NULL
);


ALTER TABLE interaudit.invoices OWNER TO postgres;

--
-- TOC entry 1671 (class 1259 OID 17588)
-- Dependencies: 7
-- Name: item_event_planning; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE item_event_planning (
    id bigint NOT NULL,
    mission boolean NOT NULL,
    identity bigint,
    title character varying(255),
    dateofevent timestamp without time zone,
    durationtype character varying(255),
    duration double precision NOT NULL,
    expectedstartdate timestamp without time zone,
    expectedenddate timestamp without time zone,
    realstartdate timestamp without time zone,
    realenddate timestamp without time zone,
    totalhoursspent double precision NOT NULL,
    sformat character varying(255),
    fk_eventplanning bigint NOT NULL
);


ALTER TABLE interaudit.item_event_planning OWNER TO postgres;

--
-- TOC entry 1672 (class 1259 OID 17596)
-- Dependencies: 7
-- Name: message; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE message (
    id bigint NOT NULL,
    subject character varying(255),
    contents text,
    createdate timestamp without time zone,
    sentdate timestamp without time zone,
    emailslist character varying(255),
    read boolean NOT NULL,
    parent_id bigint,
    from_id bigint,
    to_id bigint,
    mission_id bigint
);


ALTER TABLE interaudit.message OWNER TO postgres;

--
-- TOC entry 1675 (class 1259 OID 17620)
-- Dependencies: 7
-- Name: mission_costs; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE mission_costs (
    id bigint NOT NULL,
    version integer NOT NULL,
    costcode character varying(255),
    description character varying(255),
    amount double precision NOT NULL,
    currency character varying(3) NOT NULL,
    createdate timestamp without time zone,
    owner_id bigint,
    mission_id bigint
);


ALTER TABLE interaudit.mission_costs OWNER TO postgres;

--
-- TOC entry 1673 (class 1259 OID 17604)
-- Dependencies: 7
-- Name: missions; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE missions (
    id bigint NOT NULL,
    exercise character varying(255),
    title character varying(255),
    startweek integer NOT NULL,
    refer character varying(255) NOT NULL,
    comments character varying(255) NOT NULL,
    createdate date NOT NULL,
    startdate date,
    duedate date,
    datecloture date,
    updatedate date,
    updateduser character varying(255),
    status character varying(255),
    typ character varying(255) NOT NULL,
    version integer NOT NULL,
    language character varying(255),
    job_status character varying(255) NOT NULL,
    todo character varying(255),
    jobcomment character varying(255),
    tofinish character varying(255),
    annualbudget_id bigint,
    fk_parent bigint
);


ALTER TABLE interaudit.missions OWNER TO postgres;

--
-- TOC entry 1674 (class 1259 OID 17612)
-- Dependencies: 7
-- Name: missiontype_task; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE missiontype_task (
    id bigint NOT NULL,
    missiontypecode character varying(255),
    libelle character varying(255),
    taskcode character varying(255),
    defaultvalue double precision
);


ALTER TABLE interaudit.missiontype_task OWNER TO postgres;

--
-- TOC entry 1676 (class 1259 OID 17628)
-- Dependencies: 7
-- Name: origins; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE origins (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    code character varying(255) NOT NULL
);


ALTER TABLE interaudit.origins OWNER TO postgres;

--
-- TOC entry 1677 (class 1259 OID 17638)
-- Dependencies: 7
-- Name: payments; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE payments (
    id bigint NOT NULL,
    code character varying(255) NOT NULL,
    reference character varying(255) NOT NULL,
    year character varying(255),
    customername character varying(255) NOT NULL,
    totaldu double precision NOT NULL,
    totalnc double precision NOT NULL,
    totalpaid double precision NOT NULL,
    totalremaining double precision NOT NULL,
    amount double precision NOT NULL,
    currency character varying(3) NOT NULL,
    paymentdate date NOT NULL,
    version integer NOT NULL,
    fk_facture bigint NOT NULL
);


ALTER TABLE interaudit.payments OWNER TO postgres;

--
-- TOC entry 1678 (class 1259 OID 17648)
-- Dependencies: 7
-- Name: planning_annuel; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE planning_annuel (
    id bigint NOT NULL,
    lastupdate date,
    year integer NOT NULL
);


ALTER TABLE interaudit.planning_annuel OWNER TO postgres;

--
-- TOC entry 1679 (class 1259 OID 17653)
-- Dependencies: 7
-- Name: profiles; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE profiles (
    id bigint NOT NULL,
    version integer NOT NULL,
    name character varying(255),
    fromdate timestamp without time zone,
    todate timestamp without time zone,
    active boolean NOT NULL,
    ordre integer NOT NULL
);


ALTER TABLE interaudit.profiles OWNER TO postgres;

--
-- TOC entry 1680 (class 1259 OID 17658)
-- Dependencies: 7
-- Name: rights; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE rights (
    id bigint NOT NULL,
    name character varying(255),
    description character varying(500),
    code character varying(255),
    type character varying(255)
);


ALTER TABLE interaudit.rights OWNER TO postgres;

--
-- TOC entry 1681 (class 1259 OID 17666)
-- Dependencies: 7
-- Name: tasks; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE tasks (
    id bigint NOT NULL,
    name character varying(255),
    description character varying(255),
    codeprestation character varying(255),
    chargeable boolean NOT NULL,
    code character varying(255),
    optional boolean NOT NULL,
    version integer NOT NULL
);


ALTER TABLE interaudit.tasks OWNER TO postgres;

--
-- TOC entry 1683 (class 1259 OID 17682)
-- Dependencies: 7
-- Name: timesheet_cells; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE timesheet_cells (
    id bigint NOT NULL,
    version integer NOT NULL,
    daynumber integer,
    value double precision,
    fk_row bigint NOT NULL
);


ALTER TABLE interaudit.timesheet_cells OWNER TO postgres;

--
-- TOC entry 1684 (class 1259 OID 17687)
-- Dependencies: 7
-- Name: timesheet_rows; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE timesheet_rows (
    id bigint NOT NULL,
    version integer NOT NULL,
    label character varying(255),
    codeprestation character varying(255),
    custnumber character varying(255),
    asscode character varying(255),
    mancode character varying(255),
    year character varying(255),
    taskidentifier character varying(255),
    fk_activity bigint,
    fk_timesheet bigint NOT NULL
);


ALTER TABLE interaudit.timesheet_rows OWNER TO postgres;

--
-- TOC entry 1682 (class 1259 OID 17674)
-- Dependencies: 7
-- Name: timesheets; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE timesheets (
    id bigint NOT NULL,
    version integer NOT NULL,
    exercise character varying(255),
    createdate date NOT NULL,
    updatedate date,
    accepteddate date,
    rejecteddate date,
    submitdate date,
    validationdate date,
    startdateofweek date,
    enddateofweek date,
    prixvente double precision NOT NULL,
    prixrevient double precision NOT NULL,
    couthoraire double precision NOT NULL,
    status character varying(255),
    weeknumber integer NOT NULL,
    userid bigint NOT NULL,
    fk_employee bigint
);


ALTER TABLE interaudit.timesheets OWNER TO postgres;

--
-- TOC entry 1685 (class 1259 OID 17695)
-- Dependencies: 7
-- Name: trainings; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE trainings (
    id bigint NOT NULL,
    title character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    startdate timestamp without time zone NOT NULL,
    enddate timestamp without time zone NOT NULL,
    companyname character varying(255) NOT NULL,
    beneficiaire_id bigint
);


ALTER TABLE interaudit.trainings OWNER TO postgres;

--
-- TOC entry 1686 (class 1259 OID 17703)
-- Dependencies: 7
-- Name: user_actions; Type: TABLE; Schema: interaudit; Owner: postgres; Tablespace: 
--

CREATE TABLE user_actions (
    id bigint NOT NULL,
    action character varying(255),
    entityclassname character varying(255),
    entityid bigint,
    "time" timestamp without time zone,
    fk_employee bigint NOT NULL
);


ALTER TABLE interaudit.user_actions OWNER TO postgres;

SET search_path = pgagent, pg_catalog;

--
-- TOC entry 1644 (class 1259 OID 17315)
-- Dependencies: 5
-- Name: pga_exception; Type: TABLE; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE TABLE pga_exception (
    jexid integer NOT NULL,
    jexscid integer NOT NULL,
    jexdate date,
    jextime time without time zone
);


ALTER TABLE pgagent.pga_exception OWNER TO postgres;

--
-- TOC entry 1643 (class 1259 OID 17313)
-- Dependencies: 5 1644
-- Name: pga_exception_jexid_seq; Type: SEQUENCE; Schema: pgagent; Owner: postgres
--

CREATE SEQUENCE pga_exception_jexid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE pgagent.pga_exception_jexid_seq OWNER TO postgres;

--
-- TOC entry 2249 (class 0 OID 0)
-- Dependencies: 1643
-- Name: pga_exception_jexid_seq; Type: SEQUENCE OWNED BY; Schema: pgagent; Owner: postgres
--

ALTER SEQUENCE pga_exception_jexid_seq OWNED BY pga_exception.jexid;


--
-- TOC entry 2250 (class 0 OID 0)
-- Dependencies: 1643
-- Name: pga_exception_jexid_seq; Type: SEQUENCE SET; Schema: pgagent; Owner: postgres
--

SELECT pg_catalog.setval('pga_exception_jexid_seq', 1, false);


--
-- TOC entry 1638 (class 1259 OID 17233)
-- Dependencies: 1969 1970 1971 1972 1973 5
-- Name: pga_job; Type: TABLE; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE TABLE pga_job (
    jobid integer NOT NULL,
    jobjclid integer NOT NULL,
    jobname text NOT NULL,
    jobdesc text DEFAULT ''::text NOT NULL,
    jobhostagent text DEFAULT ''::text NOT NULL,
    jobenabled boolean DEFAULT true NOT NULL,
    jobcreated timestamp with time zone DEFAULT now() NOT NULL,
    jobchanged timestamp with time zone DEFAULT now() NOT NULL,
    jobagentid integer,
    jobnextrun timestamp with time zone,
    joblastrun timestamp with time zone
);


ALTER TABLE pgagent.pga_job OWNER TO postgres;

--
-- TOC entry 2251 (class 0 OID 0)
-- Dependencies: 1638
-- Name: TABLE pga_job; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON TABLE pga_job IS 'Job main entry';


--
-- TOC entry 2252 (class 0 OID 0)
-- Dependencies: 1638
-- Name: COLUMN pga_job.jobagentid; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON COLUMN pga_job.jobagentid IS 'Agent that currently executes this job.';


--
-- TOC entry 1637 (class 1259 OID 17231)
-- Dependencies: 1638 5
-- Name: pga_job_jobid_seq; Type: SEQUENCE; Schema: pgagent; Owner: postgres
--

CREATE SEQUENCE pga_job_jobid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE pgagent.pga_job_jobid_seq OWNER TO postgres;

--
-- TOC entry 2253 (class 0 OID 0)
-- Dependencies: 1637
-- Name: pga_job_jobid_seq; Type: SEQUENCE OWNED BY; Schema: pgagent; Owner: postgres
--

ALTER SEQUENCE pga_job_jobid_seq OWNED BY pga_job.jobid;


--
-- TOC entry 2254 (class 0 OID 0)
-- Dependencies: 1637
-- Name: pga_job_jobid_seq; Type: SEQUENCE SET; Schema: pgagent; Owner: postgres
--

SELECT pg_catalog.setval('pga_job_jobid_seq', 1, false);


--
-- TOC entry 1634 (class 1259 OID 17210)
-- Dependencies: 1966 5
-- Name: pga_jobagent; Type: TABLE; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE TABLE pga_jobagent (
    jagpid integer NOT NULL,
    jaglogintime timestamp with time zone DEFAULT now() NOT NULL,
    jagstation text NOT NULL
);


ALTER TABLE pgagent.pga_jobagent OWNER TO postgres;

--
-- TOC entry 2255 (class 0 OID 0)
-- Dependencies: 1634
-- Name: TABLE pga_jobagent; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON TABLE pga_jobagent IS 'Active job agents';


--
-- TOC entry 1636 (class 1259 OID 17221)
-- Dependencies: 5
-- Name: pga_jobclass; Type: TABLE; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE TABLE pga_jobclass (
    jclid integer NOT NULL,
    jclname text NOT NULL
);


ALTER TABLE pgagent.pga_jobclass OWNER TO postgres;

--
-- TOC entry 2256 (class 0 OID 0)
-- Dependencies: 1636
-- Name: TABLE pga_jobclass; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON TABLE pga_jobclass IS 'Job classification';


--
-- TOC entry 1635 (class 1259 OID 17219)
-- Dependencies: 5 1636
-- Name: pga_jobclass_jclid_seq; Type: SEQUENCE; Schema: pgagent; Owner: postgres
--

CREATE SEQUENCE pga_jobclass_jclid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE pgagent.pga_jobclass_jclid_seq OWNER TO postgres;

--
-- TOC entry 2257 (class 0 OID 0)
-- Dependencies: 1635
-- Name: pga_jobclass_jclid_seq; Type: SEQUENCE OWNED BY; Schema: pgagent; Owner: postgres
--

ALTER SEQUENCE pga_jobclass_jclid_seq OWNED BY pga_jobclass.jclid;


--
-- TOC entry 2258 (class 0 OID 0)
-- Dependencies: 1635
-- Name: pga_jobclass_jclid_seq; Type: SEQUENCE SET; Schema: pgagent; Owner: postgres
--

SELECT pg_catalog.setval('pga_jobclass_jclid_seq', 5, true);


--
-- TOC entry 1646 (class 1259 OID 17330)
-- Dependencies: 2000 2001 2002 5
-- Name: pga_joblog; Type: TABLE; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE TABLE pga_joblog (
    jlgid integer NOT NULL,
    jlgjobid integer NOT NULL,
    jlgstatus character(1) DEFAULT 'r'::bpchar NOT NULL,
    jlgstart timestamp with time zone DEFAULT now() NOT NULL,
    jlgduration interval,
    CONSTRAINT pga_joblog_jlgstatus_check CHECK ((jlgstatus = ANY (ARRAY['r'::bpchar, 's'::bpchar, 'f'::bpchar, 'i'::bpchar, 'd'::bpchar])))
);


ALTER TABLE pgagent.pga_joblog OWNER TO postgres;

--
-- TOC entry 2259 (class 0 OID 0)
-- Dependencies: 1646
-- Name: TABLE pga_joblog; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON TABLE pga_joblog IS 'Job run logs.';


--
-- TOC entry 2260 (class 0 OID 0)
-- Dependencies: 1646
-- Name: COLUMN pga_joblog.jlgstatus; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON COLUMN pga_joblog.jlgstatus IS 'Status of job: r=running, s=successfully finished, f=failed, i=no steps to execute, d=aborted';


--
-- TOC entry 1645 (class 1259 OID 17328)
-- Dependencies: 1646 5
-- Name: pga_joblog_jlgid_seq; Type: SEQUENCE; Schema: pgagent; Owner: postgres
--

CREATE SEQUENCE pga_joblog_jlgid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE pgagent.pga_joblog_jlgid_seq OWNER TO postgres;

--
-- TOC entry 2261 (class 0 OID 0)
-- Dependencies: 1645
-- Name: pga_joblog_jlgid_seq; Type: SEQUENCE OWNED BY; Schema: pgagent; Owner: postgres
--

ALTER SEQUENCE pga_joblog_jlgid_seq OWNED BY pga_joblog.jlgid;


--
-- TOC entry 2262 (class 0 OID 0)
-- Dependencies: 1645
-- Name: pga_joblog_jlgid_seq; Type: SEQUENCE SET; Schema: pgagent; Owner: postgres
--

SELECT pg_catalog.setval('pga_joblog_jlgid_seq', 1, false);


--
-- TOC entry 1640 (class 1259 OID 17259)
-- Dependencies: 1975 1976 1977 1978 1979 1980 1981 1982 1983 5
-- Name: pga_jobstep; Type: TABLE; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE TABLE pga_jobstep (
    jstid integer NOT NULL,
    jstjobid integer NOT NULL,
    jstname text NOT NULL,
    jstdesc text DEFAULT ''::text NOT NULL,
    jstenabled boolean DEFAULT true NOT NULL,
    jstkind character(1) NOT NULL,
    jstcode text NOT NULL,
    jstconnstr text DEFAULT ''::text NOT NULL,
    jstdbname name DEFAULT ''::name NOT NULL,
    jstonerror character(1) DEFAULT 'f'::bpchar NOT NULL,
    jscnextrun timestamp with time zone,
    CONSTRAINT pga_jobstep_check CHECK ((((jstconnstr <> ''::text) AND (jstkind = 's'::bpchar)) OR ((jstconnstr = ''::text) AND ((jstkind = 'b'::bpchar) OR (jstdbname <> ''::name))))),
    CONSTRAINT pga_jobstep_check1 CHECK ((((jstdbname <> ''::name) AND (jstkind = 's'::bpchar)) OR ((jstdbname = ''::name) AND ((jstkind = 'b'::bpchar) OR (jstconnstr <> ''::text))))),
    CONSTRAINT pga_jobstep_jstkind_check CHECK ((jstkind = ANY (ARRAY['b'::bpchar, 's'::bpchar]))),
    CONSTRAINT pga_jobstep_jstonerror_check CHECK ((jstonerror = ANY (ARRAY['f'::bpchar, 's'::bpchar, 'i'::bpchar])))
);


ALTER TABLE pgagent.pga_jobstep OWNER TO postgres;

--
-- TOC entry 2263 (class 0 OID 0)
-- Dependencies: 1640
-- Name: TABLE pga_jobstep; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON TABLE pga_jobstep IS 'Job step to be executed';


--
-- TOC entry 2264 (class 0 OID 0)
-- Dependencies: 1640
-- Name: COLUMN pga_jobstep.jstkind; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON COLUMN pga_jobstep.jstkind IS 'Kind of jobstep: s=sql, b=batch';


--
-- TOC entry 2265 (class 0 OID 0)
-- Dependencies: 1640
-- Name: COLUMN pga_jobstep.jstonerror; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON COLUMN pga_jobstep.jstonerror IS 'What to do if step returns an error: f=fail the job, s=mark step as succeeded and continue, i=mark as fail but ignore it and proceed';


--
-- TOC entry 1639 (class 1259 OID 17257)
-- Dependencies: 5 1640
-- Name: pga_jobstep_jstid_seq; Type: SEQUENCE; Schema: pgagent; Owner: postgres
--

CREATE SEQUENCE pga_jobstep_jstid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE pgagent.pga_jobstep_jstid_seq OWNER TO postgres;

--
-- TOC entry 2266 (class 0 OID 0)
-- Dependencies: 1639
-- Name: pga_jobstep_jstid_seq; Type: SEQUENCE OWNED BY; Schema: pgagent; Owner: postgres
--

ALTER SEQUENCE pga_jobstep_jstid_seq OWNED BY pga_jobstep.jstid;


--
-- TOC entry 2267 (class 0 OID 0)
-- Dependencies: 1639
-- Name: pga_jobstep_jstid_seq; Type: SEQUENCE SET; Schema: pgagent; Owner: postgres
--

SELECT pg_catalog.setval('pga_jobstep_jstid_seq', 1, false);


--
-- TOC entry 1648 (class 1259 OID 17347)
-- Dependencies: 2004 2005 2006 5
-- Name: pga_jobsteplog; Type: TABLE; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE TABLE pga_jobsteplog (
    jslid integer NOT NULL,
    jsljlgid integer NOT NULL,
    jsljstid integer NOT NULL,
    jslstatus character(1) DEFAULT 'r'::bpchar NOT NULL,
    jslresult integer,
    jslstart timestamp with time zone DEFAULT now() NOT NULL,
    jslduration interval,
    jsloutput text,
    CONSTRAINT pga_jobsteplog_jslstatus_check CHECK ((jslstatus = ANY (ARRAY['r'::bpchar, 's'::bpchar, 'i'::bpchar, 'f'::bpchar, 'd'::bpchar])))
);


ALTER TABLE pgagent.pga_jobsteplog OWNER TO postgres;

--
-- TOC entry 2268 (class 0 OID 0)
-- Dependencies: 1648
-- Name: TABLE pga_jobsteplog; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON TABLE pga_jobsteplog IS 'Job step run logs.';


--
-- TOC entry 2269 (class 0 OID 0)
-- Dependencies: 1648
-- Name: COLUMN pga_jobsteplog.jslstatus; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON COLUMN pga_jobsteplog.jslstatus IS 'Status of job step: r=running, s=successfully finished,  f=failed stopping job, i=ignored failure, d=aborted';


--
-- TOC entry 2270 (class 0 OID 0)
-- Dependencies: 1648
-- Name: COLUMN pga_jobsteplog.jslresult; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON COLUMN pga_jobsteplog.jslresult IS 'Return code of job step';


--
-- TOC entry 1647 (class 1259 OID 17345)
-- Dependencies: 1648 5
-- Name: pga_jobsteplog_jslid_seq; Type: SEQUENCE; Schema: pgagent; Owner: postgres
--

CREATE SEQUENCE pga_jobsteplog_jslid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE pgagent.pga_jobsteplog_jslid_seq OWNER TO postgres;

--
-- TOC entry 2271 (class 0 OID 0)
-- Dependencies: 1647
-- Name: pga_jobsteplog_jslid_seq; Type: SEQUENCE OWNED BY; Schema: pgagent; Owner: postgres
--

ALTER SEQUENCE pga_jobsteplog_jslid_seq OWNED BY pga_jobsteplog.jslid;


--
-- TOC entry 2272 (class 0 OID 0)
-- Dependencies: 1647
-- Name: pga_jobsteplog_jslid_seq; Type: SEQUENCE SET; Schema: pgagent; Owner: postgres
--

SELECT pg_catalog.setval('pga_jobsteplog_jslid_seq', 1, false);


--
-- TOC entry 1642 (class 1259 OID 17285)
-- Dependencies: 1985 1986 1987 1988 1989 1990 1991 1992 1993 1994 1995 1996 1997 5
-- Name: pga_schedule; Type: TABLE; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE TABLE pga_schedule (
    jscid integer NOT NULL,
    jscjobid integer NOT NULL,
    jscname text NOT NULL,
    jscdesc text DEFAULT ''::text NOT NULL,
    jscenabled boolean DEFAULT true NOT NULL,
    jscstart timestamp with time zone DEFAULT now() NOT NULL,
    jscend timestamp with time zone,
    jscminutes boolean[] DEFAULT '{f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f}'::boolean[] NOT NULL,
    jschours boolean[] DEFAULT '{f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f}'::boolean[] NOT NULL,
    jscweekdays boolean[] DEFAULT '{f,f,f,f,f,f,f}'::boolean[] NOT NULL,
    jscmonthdays boolean[] DEFAULT '{f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f}'::boolean[] NOT NULL,
    jscmonths boolean[] DEFAULT '{f,f,f,f,f,f,f,f,f,f,f,f}'::boolean[] NOT NULL,
    CONSTRAINT pga_schedule_jschours_size CHECK ((array_upper(jschours, 1) = 24)),
    CONSTRAINT pga_schedule_jscminutes_size CHECK ((array_upper(jscminutes, 1) = 60)),
    CONSTRAINT pga_schedule_jscmonthdays_size CHECK ((array_upper(jscmonthdays, 1) = 32)),
    CONSTRAINT pga_schedule_jscmonths_size CHECK ((array_upper(jscmonths, 1) = 12)),
    CONSTRAINT pga_schedule_jscweekdays_size CHECK ((array_upper(jscweekdays, 1) = 7))
);


ALTER TABLE pgagent.pga_schedule OWNER TO postgres;

--
-- TOC entry 2273 (class 0 OID 0)
-- Dependencies: 1642
-- Name: TABLE pga_schedule; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON TABLE pga_schedule IS 'Job schedule exceptions';


--
-- TOC entry 1641 (class 1259 OID 17283)
-- Dependencies: 1642 5
-- Name: pga_schedule_jscid_seq; Type: SEQUENCE; Schema: pgagent; Owner: postgres
--

CREATE SEQUENCE pga_schedule_jscid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE pgagent.pga_schedule_jscid_seq OWNER TO postgres;

--
-- TOC entry 2274 (class 0 OID 0)
-- Dependencies: 1641
-- Name: pga_schedule_jscid_seq; Type: SEQUENCE OWNED BY; Schema: pgagent; Owner: postgres
--

ALTER SEQUENCE pga_schedule_jscid_seq OWNED BY pga_schedule.jscid;


--
-- TOC entry 2275 (class 0 OID 0)
-- Dependencies: 1641
-- Name: pga_schedule_jscid_seq; Type: SEQUENCE SET; Schema: pgagent; Owner: postgres
--

SELECT pg_catalog.setval('pga_schedule_jscid_seq', 1, false);


--
-- TOC entry 1998 (class 2604 OID 17318)
-- Dependencies: 1644 1643 1644
-- Name: jexid; Type: DEFAULT; Schema: pgagent; Owner: postgres
--

ALTER TABLE pga_exception ALTER COLUMN jexid SET DEFAULT nextval('pga_exception_jexid_seq'::regclass);


--
-- TOC entry 1968 (class 2604 OID 17236)
-- Dependencies: 1637 1638 1638
-- Name: jobid; Type: DEFAULT; Schema: pgagent; Owner: postgres
--

ALTER TABLE pga_job ALTER COLUMN jobid SET DEFAULT nextval('pga_job_jobid_seq'::regclass);


--
-- TOC entry 1967 (class 2604 OID 17224)
-- Dependencies: 1635 1636 1636
-- Name: jclid; Type: DEFAULT; Schema: pgagent; Owner: postgres
--

ALTER TABLE pga_jobclass ALTER COLUMN jclid SET DEFAULT nextval('pga_jobclass_jclid_seq'::regclass);


--
-- TOC entry 1999 (class 2604 OID 17333)
-- Dependencies: 1646 1645 1646
-- Name: jlgid; Type: DEFAULT; Schema: pgagent; Owner: postgres
--

ALTER TABLE pga_joblog ALTER COLUMN jlgid SET DEFAULT nextval('pga_joblog_jlgid_seq'::regclass);


--
-- TOC entry 1974 (class 2604 OID 17262)
-- Dependencies: 1639 1640 1640
-- Name: jstid; Type: DEFAULT; Schema: pgagent; Owner: postgres
--

ALTER TABLE pga_jobstep ALTER COLUMN jstid SET DEFAULT nextval('pga_jobstep_jstid_seq'::regclass);


--
-- TOC entry 2003 (class 2604 OID 17350)
-- Dependencies: 1648 1647 1648
-- Name: jslid; Type: DEFAULT; Schema: pgagent; Owner: postgres
--

ALTER TABLE pga_jobsteplog ALTER COLUMN jslid SET DEFAULT nextval('pga_jobsteplog_jslid_seq'::regclass);


--
-- TOC entry 1984 (class 2604 OID 17288)
-- Dependencies: 1641 1642 1642
-- Name: jscid; Type: DEFAULT; Schema: pgagent; Owner: postgres
--

ALTER TABLE pga_schedule ALTER COLUMN jscid SET DEFAULT nextval('pga_schedule_jscid_seq'::regclass);


SET search_path = interaudit, pg_catalog;

--
-- TOC entry 2201 (class 0 OID 17429)
-- Dependencies: 1652
-- Data for Name: access_rights; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (774, true, 1, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (775, true, 1, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (776, true, 1, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (777, true, 2, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (778, true, 2, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (779, true, 2, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (780, true, 3, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (781, true, 3, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (782, true, 3, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (783, true, 4, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (784, true, 4, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (785, true, 4, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (786, true, 5, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (787, true, 5, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (788, true, 5, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (789, true, 6, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (790, true, 6, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (791, true, 6, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (792, true, 7, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (793, true, 7, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (794, true, 7, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (795, true, 181, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (796, true, 181, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (797, true, 181, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (798, true, 241, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (799, true, 241, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (800, true, 241, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (801, true, 541, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (802, false, 541, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (803, true, 541, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (804, true, 542, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (805, true, 542, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (806, true, 542, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (807, true, 543, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (808, true, 543, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (809, true, 543, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (810, true, 544, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (811, true, 544, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (812, true, 544, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (813, true, 545, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (814, true, 545, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (815, true, 545, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (816, true, 546, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (817, true, 546, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (818, true, 546, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (819, true, 547, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (820, true, 547, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (821, true, 547, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (822, true, 548, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (823, true, 548, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (824, true, 548, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (825, true, 549, 43);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (826, true, 549, 44);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (827, true, 549, 45);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (2, true, 1, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (3, true, 1, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (4, true, 1, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (5, true, 1, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (6, true, 1, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (7, false, 1, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (8, false, 1, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (9, false, 1, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (10, true, 1, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (11, true, 1, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (12, true, 1, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (13, true, 1, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (14, true, 1, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (15, true, 1, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (16, true, 1, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (17, true, 1, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (18, true, 1, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (19, true, 1, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (20, true, 1, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (21, true, 1, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (22, false, 1, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (23, true, 1, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (24, true, 1, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (25, true, 1, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (26, true, 1, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (27, true, 1, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (28, true, 1, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (29, true, 1, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (30, true, 1, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (31, true, 1, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (32, true, 1, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (33, true, 1, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (34, true, 1, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (35, true, 1, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (36, true, 1, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (37, true, 1, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (38, true, 1, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (39, true, 1, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (40, true, 1, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (41, true, 1, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (42, true, 1, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (44, true, 2, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (45, true, 2, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (46, true, 2, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (47, true, 2, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (48, true, 2, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (49, true, 2, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (50, false, 2, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (51, false, 2, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (52, false, 2, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (53, true, 2, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (54, true, 2, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (55, true, 2, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (56, true, 2, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (57, true, 2, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (58, true, 2, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (59, true, 2, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (60, true, 2, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (61, true, 2, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (62, true, 2, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (63, true, 2, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (64, true, 2, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (65, true, 2, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (66, true, 2, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (67, true, 2, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (68, true, 2, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (69, true, 2, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (70, true, 2, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (71, true, 2, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (72, true, 2, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (73, true, 2, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (74, true, 2, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (75, true, 2, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (76, true, 2, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (77, true, 2, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (78, true, 2, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (79, true, 2, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (80, true, 2, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (81, true, 2, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (82, true, 2, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (83, true, 2, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (84, true, 2, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (85, true, 2, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (87, true, 3, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (88, true, 3, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (89, true, 3, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (90, true, 3, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (91, true, 3, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (92, true, 3, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (93, true, 3, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (94, true, 3, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (95, true, 3, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (96, true, 3, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (97, true, 3, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (98, true, 3, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (99, true, 3, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (100, true, 3, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (101, true, 3, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (102, true, 3, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (103, true, 3, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (104, true, 3, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (105, true, 3, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (106, true, 3, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (107, true, 3, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (108, true, 3, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (109, true, 3, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (110, true, 3, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (111, true, 3, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (112, true, 3, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (113, true, 3, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (114, true, 3, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (115, true, 3, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (116, true, 3, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (117, true, 3, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (118, true, 3, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (119, true, 3, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (120, true, 3, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (121, true, 3, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (122, true, 3, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (123, true, 3, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (124, true, 3, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (125, true, 3, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (126, true, 3, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (127, true, 3, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (128, true, 3, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (130, true, 4, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (131, true, 4, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (132, true, 4, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (133, true, 4, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (134, true, 4, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (135, true, 4, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (136, false, 4, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (137, false, 4, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (138, false, 4, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (139, true, 4, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (140, true, 4, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (141, true, 4, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (142, true, 4, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (143, true, 4, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (144, true, 4, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (145, true, 4, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (146, true, 4, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (147, true, 4, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (148, true, 4, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (149, true, 4, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (150, true, 4, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (151, true, 4, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (152, true, 4, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (153, true, 4, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (154, true, 4, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (155, true, 4, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (156, true, 4, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (157, true, 4, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (158, true, 4, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (159, true, 4, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (160, true, 4, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (161, true, 4, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (162, true, 4, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (163, true, 4, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (164, true, 4, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (165, true, 4, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (166, true, 4, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (167, true, 4, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (168, true, 4, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (169, true, 4, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (170, true, 4, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (171, true, 4, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (173, true, 5, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (174, true, 5, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (175, true, 5, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (176, true, 5, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (177, true, 5, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (178, true, 5, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (179, false, 5, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (180, false, 5, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (181, false, 5, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (182, true, 5, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (183, true, 5, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (184, true, 5, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (185, true, 5, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (186, true, 5, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (187, true, 5, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (188, true, 5, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (189, true, 5, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (190, true, 5, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (191, true, 5, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (192, true, 5, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (193, true, 5, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (194, true, 5, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (195, true, 5, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (196, true, 5, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (197, true, 5, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (198, true, 5, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (199, true, 5, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (200, true, 5, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (201, true, 5, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (202, true, 5, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (203, true, 5, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (204, true, 5, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (205, true, 5, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (206, true, 5, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (207, true, 5, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (208, true, 5, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (209, true, 5, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (210, true, 5, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (211, true, 5, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (212, true, 5, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (213, true, 5, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (214, true, 5, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (216, true, 6, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (217, true, 6, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (218, true, 6, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (219, true, 6, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (220, true, 6, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (221, true, 6, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (222, false, 6, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (223, false, 6, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (224, false, 6, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (225, true, 6, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (226, true, 6, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (227, true, 6, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (228, true, 6, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (229, true, 6, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (230, true, 6, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (231, true, 6, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (232, true, 6, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (233, true, 6, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (234, true, 6, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (235, true, 6, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (236, true, 6, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (237, true, 6, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (238, true, 6, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (239, true, 6, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (240, true, 6, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (241, true, 6, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (242, true, 6, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (243, true, 6, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (244, true, 6, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (245, true, 6, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (246, true, 6, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (247, true, 6, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (248, true, 6, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (249, true, 6, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (250, true, 6, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (251, true, 6, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (252, true, 6, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (253, true, 6, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (254, true, 6, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (255, true, 6, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (256, true, 6, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (257, true, 6, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (259, true, 7, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (260, true, 7, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (261, true, 7, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (262, true, 7, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (263, true, 7, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (264, true, 7, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (265, false, 7, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (266, false, 7, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (267, false, 7, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (268, true, 7, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (269, true, 7, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (270, true, 7, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (271, true, 7, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (272, true, 7, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (273, true, 7, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (274, true, 7, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (275, true, 7, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (276, true, 7, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (277, true, 7, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (278, true, 7, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (279, true, 7, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (280, true, 7, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (281, true, 7, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (282, true, 7, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (283, true, 7, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (284, true, 7, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (285, true, 7, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (286, true, 7, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (287, true, 7, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (288, true, 7, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (289, true, 7, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (290, true, 7, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (291, true, 7, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (292, true, 7, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (293, true, 7, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (294, true, 7, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (295, true, 7, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (296, true, 7, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (297, true, 7, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (298, true, 7, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (299, true, 7, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (300, true, 7, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (302, true, 181, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (303, true, 181, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (304, true, 181, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (305, true, 181, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (306, true, 181, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (307, true, 181, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (308, false, 181, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (309, false, 181, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (310, false, 181, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (311, true, 181, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (312, true, 181, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (313, true, 181, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (314, true, 181, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (315, true, 181, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (316, true, 181, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (317, true, 181, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (318, true, 181, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (319, true, 181, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (320, true, 181, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (321, true, 181, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (322, true, 181, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (323, true, 181, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (324, true, 181, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (325, true, 181, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (326, true, 181, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (327, true, 181, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (328, true, 181, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (329, true, 181, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (330, true, 181, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (331, true, 181, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (332, true, 181, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (333, true, 181, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (334, true, 181, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (335, true, 181, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (336, true, 181, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (337, true, 181, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (338, true, 181, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (339, true, 181, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (340, true, 181, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (341, true, 181, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (342, true, 181, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (343, true, 181, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (345, true, 241, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (346, true, 241, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (347, true, 241, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (348, true, 241, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (349, true, 241, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (350, true, 241, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (351, false, 241, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (352, false, 241, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (353, false, 241, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (354, true, 241, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (355, true, 241, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (356, true, 241, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (357, true, 241, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (358, true, 241, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (359, true, 241, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (360, true, 241, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (361, true, 241, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (362, true, 241, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (363, true, 241, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (364, true, 241, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (365, true, 241, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (366, true, 241, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (367, true, 241, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (368, true, 241, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (369, true, 241, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (370, true, 241, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (371, true, 241, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (372, true, 241, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (373, true, 241, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (374, true, 241, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (375, true, 241, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (376, true, 241, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (377, true, 241, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (378, true, 241, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (379, true, 241, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (380, true, 241, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (381, true, 241, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (382, true, 241, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (383, true, 241, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (384, true, 241, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (385, true, 241, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (386, true, 241, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (388, true, 541, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (389, false, 541, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (390, true, 541, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (391, true, 541, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (392, false, 541, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (393, true, 541, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (394, true, 541, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (395, false, 541, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (396, true, 541, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (397, false, 541, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (398, false, 541, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (399, false, 541, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (400, true, 541, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (401, false, 541, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (402, true, 541, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (403, true, 541, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (404, false, 541, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (405, true, 541, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (406, false, 541, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (407, false, 541, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (408, true, 541, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (409, false, 541, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (410, false, 541, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (411, false, 541, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (412, false, 541, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (413, false, 541, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (414, false, 541, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (415, false, 541, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (416, false, 541, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (417, false, 541, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (418, true, 541, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (419, false, 541, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (420, true, 541, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (421, false, 541, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (422, false, 541, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (423, false, 541, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (424, true, 541, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (425, false, 541, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (426, false, 541, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (427, false, 541, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (428, false, 541, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (429, false, 541, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (431, true, 542, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (432, true, 542, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (433, true, 542, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (434, true, 542, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (435, true, 542, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (436, true, 542, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (437, false, 542, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (438, false, 542, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (439, false, 542, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (440, true, 542, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (441, true, 542, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (442, true, 542, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (443, true, 542, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (444, true, 542, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (445, true, 542, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (446, true, 542, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (447, true, 542, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (448, true, 542, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (449, true, 542, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (450, true, 542, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (451, true, 542, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (452, true, 542, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (453, true, 542, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (454, true, 542, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (455, true, 542, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (456, true, 542, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (457, true, 542, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (458, true, 542, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (459, true, 542, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (460, true, 542, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (461, true, 542, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (462, true, 542, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (463, true, 542, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (464, true, 542, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (465, true, 542, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (466, true, 542, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (467, true, 542, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (468, true, 542, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (469, true, 542, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (470, true, 542, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (471, true, 542, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (472, true, 542, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (474, true, 543, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (475, true, 543, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (476, true, 543, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (477, true, 543, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (478, true, 543, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (479, true, 543, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (480, false, 543, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (481, false, 543, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (482, false, 543, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (483, true, 543, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (484, true, 543, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (485, true, 543, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (486, true, 543, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (487, true, 543, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (488, true, 543, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (489, true, 543, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (490, true, 543, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (491, true, 543, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (492, true, 543, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (493, true, 543, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (494, true, 543, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (495, true, 543, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (496, true, 543, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (497, true, 543, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (498, true, 543, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (499, true, 543, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (500, true, 543, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (501, true, 543, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (502, true, 543, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (503, true, 543, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (504, true, 543, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (505, true, 543, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (506, true, 543, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (507, true, 543, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (508, true, 543, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (509, true, 543, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (510, true, 543, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (511, true, 543, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (512, true, 543, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (513, true, 543, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (514, true, 543, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (515, true, 543, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (517, true, 544, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (518, true, 544, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (519, true, 544, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (520, true, 544, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (521, true, 544, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (522, true, 544, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (523, false, 544, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (524, false, 544, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (525, false, 544, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (526, true, 544, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (527, true, 544, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (528, true, 544, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (529, true, 544, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (530, true, 544, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (531, true, 544, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (532, true, 544, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (533, true, 544, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (534, true, 544, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (535, true, 544, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (536, true, 544, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (537, true, 544, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (538, true, 544, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (539, true, 544, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (540, true, 544, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (541, true, 544, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (542, true, 544, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (543, true, 544, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (544, true, 544, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (545, true, 544, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (546, true, 544, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (547, true, 544, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (548, true, 544, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (549, true, 544, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (550, true, 544, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (551, true, 544, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (552, true, 544, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (553, true, 544, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (554, true, 544, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (555, true, 544, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (556, true, 544, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (557, true, 544, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (558, true, 544, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (560, true, 545, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (561, true, 545, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (562, true, 545, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (563, true, 545, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (564, true, 545, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (565, true, 545, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (566, false, 545, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (567, false, 545, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (568, false, 545, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (569, true, 545, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (570, true, 545, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (571, true, 545, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (572, true, 545, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (573, true, 545, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (574, true, 545, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (575, true, 545, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (576, true, 545, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (577, true, 545, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (578, true, 545, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (579, true, 545, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (580, true, 545, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (581, true, 545, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (582, false, 545, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (583, true, 545, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (584, true, 545, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (585, true, 545, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (586, true, 545, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (587, true, 545, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (588, true, 545, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (589, true, 545, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (590, true, 545, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (591, true, 545, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (592, true, 545, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (593, true, 545, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (594, true, 545, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (595, true, 545, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (596, true, 545, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (597, true, 545, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (598, true, 545, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (599, true, 545, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (600, true, 545, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (601, true, 545, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (603, true, 546, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (604, true, 546, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (605, true, 546, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (606, true, 546, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (607, true, 546, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (608, true, 546, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (609, false, 546, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (610, false, 546, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (611, false, 546, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (612, true, 546, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (613, true, 546, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (614, true, 546, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (615, true, 546, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (616, true, 546, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (617, true, 546, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (618, true, 546, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (619, true, 546, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (620, true, 546, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (621, true, 546, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (622, true, 546, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (623, true, 546, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (624, true, 546, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (625, true, 546, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (626, true, 546, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (627, true, 546, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (628, true, 546, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (629, true, 546, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (630, true, 546, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (631, true, 546, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (632, true, 546, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (633, true, 546, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (634, true, 546, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (635, true, 546, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (636, true, 546, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (637, true, 546, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (638, true, 546, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (639, true, 546, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (640, true, 546, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (641, true, 546, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (642, true, 546, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (643, true, 546, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (644, true, 546, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (646, true, 547, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (647, true, 547, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (648, true, 547, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (649, true, 547, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (650, true, 547, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (651, true, 547, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (652, false, 547, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (653, false, 547, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (654, false, 547, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (655, true, 547, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (656, true, 547, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (657, true, 547, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (658, true, 547, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (659, true, 547, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (660, true, 547, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (661, true, 547, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (662, true, 547, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (663, true, 547, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (664, true, 547, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (665, true, 547, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (666, true, 547, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (667, true, 547, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (668, true, 547, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (669, true, 547, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (670, true, 547, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (671, true, 547, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (672, true, 547, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (673, true, 547, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (674, true, 547, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (675, true, 547, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (676, true, 547, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (677, true, 547, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (678, true, 547, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (679, true, 547, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (680, true, 547, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (681, true, 547, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (682, true, 547, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (683, true, 547, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (684, true, 547, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (685, true, 547, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (686, true, 547, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (687, true, 547, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (689, true, 548, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (690, true, 548, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (691, true, 548, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (692, true, 548, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (693, true, 548, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (694, true, 548, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (695, false, 548, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (696, false, 548, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (697, false, 548, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (698, true, 548, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (699, true, 548, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (700, true, 548, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (701, true, 548, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (702, true, 548, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (703, true, 548, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (704, true, 548, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (705, true, 548, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (706, true, 548, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (707, true, 548, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (708, true, 548, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (709, true, 548, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (710, true, 548, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (711, true, 548, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (712, true, 548, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (713, true, 548, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (714, true, 548, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (715, true, 548, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (716, true, 548, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (717, true, 548, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (718, true, 548, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (719, true, 548, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (720, true, 548, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (721, true, 548, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (722, true, 548, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (723, true, 548, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (724, true, 548, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (725, true, 548, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (726, true, 548, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (727, true, 548, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (728, true, 548, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (729, true, 548, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (730, true, 548, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (732, true, 549, 1);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (733, true, 549, 2);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (734, true, 549, 3);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (735, true, 549, 4);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (736, true, 549, 5);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (737, true, 549, 6);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (738, false, 549, 7);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (739, false, 549, 8);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (740, false, 549, 9);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (741, true, 549, 10);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (742, true, 549, 11);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (743, true, 549, 12);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (744, true, 549, 13);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (745, true, 549, 14);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (746, true, 549, 15);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (747, true, 549, 16);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (748, true, 549, 17);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (749, true, 549, 18);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (750, true, 549, 19);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (751, true, 549, 20);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (752, true, 549, 21);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (753, true, 549, 22);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (754, true, 549, 23);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (755, true, 549, 24);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (756, true, 549, 25);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (757, true, 549, 26);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (758, true, 549, 27);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (759, true, 549, 28);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (760, true, 549, 29);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (761, true, 549, 30);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (762, true, 549, 31);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (763, true, 549, 32);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (764, true, 549, 33);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (765, true, 549, 34);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (766, true, 549, 35);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (767, true, 549, 36);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (768, true, 549, 37);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (769, true, 549, 38);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (770, true, 549, 39);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (771, true, 549, 40);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (772, true, 549, 41);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (773, true, 549, 42);
INSERT INTO access_rights (id, active, fk_employee, fk_right) VALUES (1, true, 1, 1);


--
-- TOC entry 2202 (class 0 OID 17434)
-- Dependencies: 1653
-- Data for Name: activities; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (55, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 54, 62729);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (57, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 56, 62731);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (59, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 58, 62748);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (61, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 60, 62744);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (63, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 62, 62744);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (67, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 66, 62731);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (69, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 68, 62731);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (71, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 70, 62729);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (73, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 72, 62744);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (75, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 74, 62744);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (77, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 76, 62730);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (79, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 78, 62729);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (81, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 80, 62747);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (83, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 82, 62731);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (87, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 86, 62730);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (89, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 88, 62731);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (93, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 92, 62747);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (97, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 96, 62731);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (99, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 98, 62730);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (101, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 100, 62729);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (103, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 102, 62730);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (105, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 104, 62748);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (107, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 106, 62748);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (109, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 0, 1, '2010-12-17', NULL, NULL, 108, 62730);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (95, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 1, 1, '2010-12-17', NULL, NULL, 94, 62729);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (91, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 1, 1, '2010-12-17', NULL, NULL, 90, 62729);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (85, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 1, 1, '2010-12-17', NULL, NULL, 84, 62747);
INSERT INTO activities (id, status, todo, comments, version, ordre, startdate, updatedate, enddate, mission_id, task_id) VALUES (65, 'FIELD WORK TO FINALISE', 'TO_REVIEW', NULL, 1, 1, '2010-12-17', NULL, NULL, 64, 62748);


--
-- TOC entry 2203 (class 0 OID 17442)
-- Dependencies: 1654
-- Data for Name: banks; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO banks (id, name, code, account, contactperson, contactpersonemail, contactpersonphone, contactpersonfax, active) VALUES (242, 'Societe generale', 'SG', 'LU 456987-789444', 'Alan Johnso', 'alan.johns@interaudit.lu', '4578963210', '', true);
INSERT INTO banks (id, name, code, account, contactperson, contactpersonemail, contactpersonphone, contactpersonfax, active) VALUES (302, 'Banque et Caisse d''Epargne de l''Etat', 'bceelull', '19556012', 'Hocheid ', 'hocheid@bcee.lu', '491212', '04 56 98 78 63', true);
INSERT INTO banks (id, name, code, account, contactperson, contactpersonemail, contactpersonphone, contactpersonfax, active) VALUES (303, 'BGL BNP Parisbas', 'BGLLLULL', 'LU945583698', 'Scorpionne', 'scorpionne@bgl.lu', '494448', '36 96 78 95 42', true);


--
-- TOC entry 2204 (class 0 OID 17456)
-- Dependencies: 1655
-- Data for Name: budgets; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (36, 4, 13440.000057220501, 2654, 0, NULL, 'ONGOING', true, 24, 1, 2, 5);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (44, 2, 11054.4000470638, 0, 0, NULL, 'ONGOING', true, 24, 35, 1, 6);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (51, 2, 5107.2000217437699, 0, 0, NULL, 'ONGOING', true, 24, 16318, 1, 4);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (46, 2, 16240.000069141401, 0, 0, NULL, 'ONGOING', true, 24, 28, 1, 5);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (45, 2, 1680.0000071525601, 0, 0, NULL, 'ONGOING', true, 24, 16301, 1, 6);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (42, 2, 17920.000076293902, 0, 0, NULL, 'ONGOING', true, 24, 44, 1, 5);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (40, 2, 6720.0000286102304, 0, 0, NULL, 'ONGOING', true, 24, 30, 1, 4);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (31, 2, 9801.1200417280197, 0, 0, NULL, 'ONGOING', true, 24, 40, 1, 5);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (27, 2, 2240.00000953674, 0, 0, NULL, 'ONGOING', true, 24, 37, 1, 6);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (25, 2, 13440.000057220501, 0, 0, NULL, 'ONGOING', true, 24, 27, 1, 5);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (48, 2, 28000.0001192093, 0, 0, NULL, 'ONGOING', true, 24, 26, 2, 6);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (32, 2, 6003.2000255584699, 0, 0, NULL, 'ONGOING', true, 24, 32, 2, 4);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (28, 2, 6720.0000286102304, 0, 0, NULL, 'ONGOING', true, 24, 34, 2, 4);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (52, 2, 7056.0000300407401, 0, 0, NULL, 'ONGOING', true, 24, 38, 3, 4);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (47, 2, 11058.880047082899, 0, 0, NULL, 'ONGOING', true, 24, 43, 3, 5);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (41, 2, 17472.0000743866, 0, 0, NULL, 'ONGOING', true, 24, 29, 3, 4);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (35, 2, 5149.7600219249698, 0, 0, NULL, 'ONGOING', true, 24, 24, 3, 4);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (33, 2, 6272.0000267028799, 0, 0, NULL, 'ONGOING', true, 24, 36, 3, 6);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (30, 2, 5261.7600224018097, 0, 0, NULL, 'ONGOING', true, 24, 23, 3, 5);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (49, 2, 11061.1200470924, 0, 0, NULL, 'ONGOING', true, 24, 42, 7, 5);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (39, 2, 5040.0000214576703, 0, 0, NULL, 'ONGOING', true, 24, 22, 7, 4);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (38, 2, 8960.0000381469708, 0, 0, NULL, 'ONGOING', true, 24, 33, 7, 6);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (37, 2, 8842.40003764629, 0, 0, NULL, 'ONGOING', true, 24, 39, 7, 6);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (34, 2, 16240.000069141401, 0, 0, NULL, 'ONGOING', true, 24, 2, 7, 4);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (29, 2, 7825.4400333166104, 0, 0, NULL, 'ONGOING', true, 24, 41, 7, 6);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (26, 2, 56000.000238418601, 0, 0, NULL, 'ONGOING', true, 24, 232, 7, 4);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (43, 4, 14500, 2610, 0, 'undefined', 'ONGOING', true, 24, 21, 2, 5);
INSERT INTO budgets (id, version, exp_amount, real_amount, rep_amount, comments, status, fiable, fk_exercise, fk_contract, fk_associe, fk_manager) VALUES (50, 3, 14067.2000598907, 2532, 0, NULL, 'ONGOING', true, 24, 25, 2, 5);


--
-- TOC entry 2205 (class 0 OID 17464)
-- Dependencies: 1656
-- Data for Name: comments; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO comments (id, text, created, sent, sender_address, receiver_address, fk_employee, fk_timesheet) VALUES (233, 'Timesheet incomplète', '2010-12-17 15:25:50.519', NULL, 'murielle.badoux@interaudit.lu', 'murielle.badoux@interaudit.lu', 3, 1);
INSERT INTO comments (id, text, created, sent, sender_address, receiver_address, fk_employee, fk_timesheet) VALUES (382, '', '2010-12-25 18:57:30.36', NULL, 'murielle.badoux@interaudit.lu', 'murielle.badoux@interaudit.lu', 3, 277);
INSERT INTO comments (id, text, created, sent, sender_address, receiver_address, fk_employee, fk_timesheet) VALUES (384, 'Pas les bonnes heures', '2010-12-25 18:58:03.13', NULL, 'murielle.badoux@interaudit.lu', 'murielle.badoux@interaudit.lu', 3, 277);


--
-- TOC entry 2206 (class 0 OID 17472)
-- Dependencies: 1657
-- Data for Name: contacts; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO contacts (id, sex, name, f_name, l_name, job, b_phone, b_mobile, b_fax, p_phone, p_mobile, b_email, p_email, b_web_addr, active, b_activity, updateduser, company, address, createdate, updatedate, version, fk_customer) VALUES (216, 'M', 'Manason', 'Georges', 'Manason', 'senior engineer', '+ 33 52 78 96 65 87', NULL, NULL, '+ 33 52 78 96 65 87', '+ 33 52 78 96 65 87', 'contact.email@wanadoo.fr', 'georges.manasson@caryos.lu', NULL, true, 'Caryos SA', NULL, 'Caryos SA', NULL, '0001-03-14 BC', '0001-03-14 BC', 0, 15);
INSERT INTO contacts (id, sex, name, f_name, l_name, job, b_phone, b_mobile, b_fax, p_phone, p_mobile, b_email, p_email, b_web_addr, active, b_activity, updateduser, company, address, createdate, updatedate, version, fk_customer) VALUES (222, 'M', 'jones', 'brian', 'jones', 'commercial', '+333 66 96 78 45', NULL, NULL, '+333 66 96 78 45', '+333 66 96 78 45', 'example@server.lu', 'brian.jones@anglesacapitalsa.lu', NULL, true, 'Activity', NULL, 'ANGLESEA CAPITAL SA', NULL, '0001-03-14 BC', '0001-03-14 BC', 0, 3);
INSERT INTO contacts (id, sex, name, f_name, l_name, job, b_phone, b_mobile, b_fax, p_phone, p_mobile, b_email, p_email, b_web_addr, active, b_activity, updateduser, company, address, createdate, updatedate, version, fk_customer) VALUES (302, 'M', 'jenkins', 'bobby', 'jenkins', 'manager', '+33 54 69 63', NULL, NULL, '+33 54 69 63', '+33 54 69 63', 'a.marx@sabliere.lu', 'bobby.jenkins@borg.lu', NULL, true, 'Fournisseur de sable', NULL, 'BORG LUXEMBOURG SA', NULL, '0001-03-20 BC', '0001-03-20 BC', 0, 653);
INSERT INTO contacts (id, sex, name, f_name, l_name, job, b_phone, b_mobile, b_fax, p_phone, p_mobile, b_email, p_email, b_web_addr, active, b_activity, updateduser, company, address, createdate, updatedate, version, fk_customer) VALUES (437, 'M', 'Harrison', 'james', 'Harrison', 'Manager', '+ 56 78 96 333', NULL, NULL, '+ 56 78 96 333', '+ 56 78 96 333', 'contact.email@pt.lu', 'james.harrisson@cologic.lu', NULL, true, 'Cologic
', NULL, 'Cologic', NULL, '0001-03-28 BC', '0001-03-28 BC', 0, 16);
INSERT INTO contacts (id, sex, name, f_name, l_name, job, b_phone, b_mobile, b_fax, p_phone, p_mobile, b_email, p_email, b_web_addr, active, b_activity, updateduser, company, address, createdate, updatedate, version, fk_customer) VALUES (16314, 'M', 'leandre', 'alex', 'leandre', 'manager', '10 20 36 96 87', NULL, NULL, '10 20 36 96 87', '10 20 36 96 87', 'contact@europarl.eu.lu', 'alex.leandre@scancargo.lu', NULL, true, 'Scancargo SA', NULL, 'Scancargo SA', NULL, '0001-11-28 BC', '0001-11-28 BC', 0, 7);
INSERT INTO contacts (id, sex, name, f_name, l_name, job, b_phone, b_mobile, b_fax, p_phone, p_mobile, b_email, p_email, b_web_addr, active, b_activity, updateduser, company, address, createdate, updatedate, version, fk_customer) VALUES (233, 'F', 'Jones', 'Brian', 'Jones', 'Commercial', '1245633', NULL, NULL, '1245633', '1245633', 'contact@europarl.eu.lu', 'brian.jones@fortis.lu', NULL, true, 'EUROPEAN PARLIAMENT-Imprest qtrly reviews    
', NULL, 'EUROPEAN PARLIAMENT-Imprest qtrly reviews', NULL, '0001-01-10 BC', '0001-03-14 BC', 2, 1);
INSERT INTO contacts (id, sex, name, f_name, l_name, job, b_phone, b_mobile, b_fax, p_phone, p_mobile, b_email, p_email, b_web_addr, active, b_activity, updateduser, company, address, createdate, updatedate, version, fk_customer) VALUES (261, 'F', 'Clark', 'Jones', 'Clark', 'Commercial', '12 30 45 69', NULL, NULL, '12 30 45 69', '12 30 45 69', 'example@server.lu', 'jones.clark@sgbt.lu', NULL, true, 'Activity', NULL, 'ANGLESEA CAPITAL SA', NULL, '0001-01-15 BC', '0001-04-29 BC', 1, 3);
INSERT INTO contacts (id, sex, name, f_name, l_name, job, b_phone, b_mobile, b_fax, p_phone, p_mobile, b_email, p_email, b_web_addr, active, b_activity, updateduser, company, address, createdate, updatedate, version, fk_customer) VALUES (16303, 'M', 'ratler', 'andy', 'ratler', 'commercial', '20 30 14 58', NULL, NULL, '20 30 14 58', '20 30 14 58', 'contact@email.fr', 'andy.ratler@apkieffer.lu', NULL, true, 'APKieffer
', NULL, 'APKieffer', NULL, '0001-11-27 BC', '0001-11-27 BC', 0, 14);
INSERT INTO contacts (id, sex, name, f_name, l_name, job, b_phone, b_mobile, b_fax, p_phone, p_mobile, b_email, p_email, b_web_addr, active, b_activity, updateduser, company, address, createdate, updatedate, version, fk_customer) VALUES (12947, 'F', 'dalmin', 'benoit', 'dalmin', 'senior manager', '12 36 98 74 56', NULL, NULL, '12 36 98 74 56', '12 36 98 74 56', 'a.marx@sabliere.lu', 'benoit.dalmin@dimpex.lu', NULL, true, 'Fournisseur de sable', NULL, 'DIMPEX SA', NULL, '0001-09-26 BC', '0001-09-26 BC', 0, 651);
INSERT INTO contacts (id, sex, name, f_name, l_name, job, b_phone, b_mobile, b_fax, p_phone, p_mobile, b_email, p_email, b_web_addr, active, b_activity, updateduser, company, address, createdate, updatedate, version, fk_customer) VALUES (12948, 'M', 'dimpex', 'benoit', 'dimpex', 'senior manager', '78 96 45 3. 45', NULL, NULL, '78 96 45 3. 45', '78 96 45 3. 45', 'a.marx@sabliere.lu', 'benoit.dalmin@dimpex.lu', NULL, true, 'Fournisseur de sable', NULL, 'LUXLAIT SA', NULL, '0001-09-26 BC', '0001-09-26 BC', 0, 655);
INSERT INTO contacts (id, sex, name, f_name, l_name, job, b_phone, b_mobile, b_fax, p_phone, p_mobile, b_email, p_email, b_web_addr, active, b_activity, updateduser, company, address, createdate, updatedate, version, fk_customer) VALUES (14661, 'F', 'Stosur', 'lea', 'Stosur', 'Manager', '12 35 69 87 45', NULL, NULL, '12 35 69 87 45', '12 35 69 87 45', 'contact@email.fr', 'lea.stosur@apkieffer.lu', NULL, true, 'APKieffer
', NULL, 'APKieffer', NULL, '0001-11-06 BC', '0001-11-06 BC', 0, 14);
INSERT INTO contacts (id, sex, name, f_name, l_name, job, b_phone, b_mobile, b_fax, p_phone, p_mobile, b_email, p_email, b_web_addr, active, b_activity, updateduser, company, address, createdate, updatedate, version, fk_customer) VALUES (12001, 'M', 'bean', 'larry', 'bean', 'manager', '41 56 78 96', NULL, NULL, '41 56 78 96', '45 963 12', 'contact@email.fr', 'larry.bean@lauradHolding.lu', NULL, true, 'Laurad Holding
', NULL, 'Laurad Holding', NULL, '0001-07-17 BC', '2010-12-20', 1, 17);
INSERT INTO contacts (id, sex, name, f_name, l_name, job, b_phone, b_mobile, b_fax, p_phone, p_mobile, b_email, p_email, b_web_addr, active, b_activity, updateduser, company, address, createdate, updatedate, version, fk_customer) VALUES (12341, 'F', 'edwards', 'john ', 'edwards', 'manager', '12 45 78 96', NULL, NULL, '12 45 78 96', '12 45 78 96', 'a.marx@sabliere.lu', 'john .edawards@ekolux.com', NULL, true, 'Fournisseur de sable', NULL, 'DIMPEX SA', NULL, '0001-08-14 BC', '2010-12-20', 1, 651);


--
-- TOC entry 2207 (class 0 OID 17480)
-- Dependencies: 1658
-- Data for Name: contracts; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (21, 'ANGLESEACAPITALSA_1', 'ANGLESEA CAPITAL SA', '2001-01-09 00:00:00', '2011-12-22 00:00:00', NULL, 'EN', 12300, 'EUR', 2, 'CONSO', true, 3);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (232, 'APKIEFFER_2', 'APKieffer', '2001-01-01 00:00:00', '2011-12-31 00:00:00', NULL, 'EN', 50000, 'EUR', 1, 'MISSPE', true, 14);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (22, 'APKIEFFER_1', 'APKieffer', '2001-01-01 00:00:00', '2020-12-31 00:00:00', NULL, 'FR', 4500, 'EUR', 1, 'MCOMM', true, 14);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (23, 'BORGLUXEMBOURGSA_1', 'BORG LUXEMBOURG SA', '2001-01-01 00:00:00', '2020-12-31 00:00:00', NULL, 'DE', 4698, 'EUR', 1, 'RC', true, 653);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (1, 'CARYOSSA_1', 'Caryos SA mission', '2001-01-01 00:00:00', '2020-12-31 00:00:00', NULL, 'EN', 12000, 'EUR', 1, 'RA', true, 15);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (24, 'COLOGIC_1', 'Cologic', '2001-01-01 00:00:00', '2020-01-01 00:00:00', NULL, 'FR', 4598, 'EUR', 2, 'MISSPE', true, 16);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (25, 'DIMPEXSA_1', 'DIMPEX SA', '2001-01-01 00:00:00', '2020-12-31 00:00:00', NULL, 'DE', 12560, 'EUR', 1, 'RA', true, 651);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (26, 'EASYDISTRIBUTIONSA(EXEASYCARSSA)_1', 'Easy Distribution SA (ex EASYCARS SA)', '2001-01-01 00:00:00', '2020-12-31 00:00:00', NULL, 'EN', 25000, 'EUR', 1, 'RC', true, 9);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (27, 'EUROPEANPARLIAMENT-CANTEEN_1', 'EUROPEAN PARLIAMENT-canteen', '2001-01-01 00:00:00', '2020-12-31 00:00:00', NULL, 'DE', 12000, 'EUR', 1, 'RA', false, 2);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (28, 'EUROPEANPARLIAMENT-IMPRESTQTRLYREVIEWS_1', 'EUROPEAN PARLIAMENT-Imprest qtrly reviews', '2001-01-01 00:00:00', '2020-12-31 00:00:00', NULL, 'FR', 14500, 'EUR', 1, 'MCOMM', true, 1);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (29, 'EUROPEANPARLIAMENT-SUPERMARKET_1', 'EUROPEAN PARLIAMENT-supermarket', '2001-01-01 00:00:00', '2020-12-31 00:00:00', NULL, 'DE', 15600, 'EUR', 1, 'MISSPE', true, 4);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (30, 'GAMETINVESTMENTSA_1', 'GAMET INVESTMENT SA', '2001-01-01 00:00:00', '2020-01-01 00:00:00', NULL, 'DE', 6000, 'EUR', 1, 'RC', true, 654);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (16318, 'GAMETINVESTMENTSA_2', 'gamet investment sa contract', '2001-11-11 00:00:00', '2020-11-30 00:00:00', NULL, 'EN', 4560, 'EUR', 1, 'AC', false, 654);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (2, 'GULFOILINTERNATIONALLTD_1', 'GULF OIL INTERNATIONAL LTD', '2001-01-01 00:00:00', '2020-12-31 00:00:00', NULL, 'EN', 14500, 'EUR', 1, 'AC', true, 652);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (32, 'HERMESPARTNERSTRUSTSERVICESSA_1', 'HERMES PARTNERS TRUST SERVICES SA', '2001-01-04 00:00:00', '2020-12-31 00:00:00', NULL, 'FR', 5360, 'EUR', 1, 'AC', true, 11);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (33, 'HOLDUNSA_1', 'HOLDUN SA', '2001-01-01 00:00:00', '2020-12-31 00:00:00', NULL, 'FR', 8000, 'EUR', 1, 'MCOMM', true, 6);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (34, 'HPMC1SÀRL_1', 'HPMC1 Sàrl', '2001-01-01 00:00:00', '2020-01-01 00:00:00', NULL, 'EN', 6000, 'EUR', 1, 'CONSO', true, 5);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (35, 'INTRUMACORPORATESERVICESSA_1', 'Intruma Corporate Services SA', '2001-01-01 00:00:00', '2020-01-01 00:00:00', NULL, 'EN', 9870, 'EUR', 1, 'MCOMM', true, 12);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (36, 'LAURADHOLDING_1', 'Laurad Holding', '2001-01-01 00:00:00', '2020-01-01 00:00:00', NULL, 'DE', 5600, 'EUR', 1, 'AC', true, 17);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (16301, 'LUXLAITSA_2', 'contract luxlait 2', '2001-11-02 00:00:00', '2020-11-17 00:00:00', NULL, 'EN', 1500, 'EUR', 1, 'AC', true, 655);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (37, 'LUXLAITSA_1', 'LUXLAIT SA', '2001-01-01 00:00:00', '2020-01-01 00:00:00', NULL, 'EN', 2000, 'EUR', 1, 'RA', true, 655);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (38, 'LUXYSSA_1', 'Luxys SA', '2001-01-01 00:00:00', '2020-01-01 00:00:00', NULL, 'FR', 6300, 'EUR', 1, 'AC', true, 8);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (39, 'MATSASA_1', 'MATSA', '2001-01-01 00:00:00', '2020-01-01 00:00:00', NULL, 'DE', 7895, 'EUR', 1, 'CONSO', true, 18);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (40, 'PARFIMATSA_1', 'Parfimat SA', '2001-01-01 00:00:00', '2020-01-01 00:00:00', NULL, 'EN', 8751, 'EUR', 1, 'RC', true, 19);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (41, 'SABLIEREHEINSA_1', 'SABLIERE HEIN SA', '2001-01-01 00:00:00', '2020-12-31 00:00:00', NULL, 'DE', 6987, 'EUR', 1, 'RA', true, 650);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (42, 'SCANCARGOSA_1', 'Scancargo SA', '2001-01-01 00:00:00', '2020-01-01 00:00:00', NULL, 'FR', 9876, 'EUR', 1, 'RC', true, 7);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (43, 'SUMMERWINDSA_1', 'Summerwind SA', '2001-01-01 00:00:00', '2020-01-01 00:00:00', NULL, 'EN', 9874, 'EUR', 1, 'RA', true, 13);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (44, 'WEISGERBEREXPERTISE_1', 'Weisgerber Expertise', '2001-01-01 00:00:00', '2020-01-01 00:00:00', NULL, 'FR', 16000, 'EUR', 1, 'CONSO', true, 10);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (276, 'TESTCLIENTAVECONTRACT_1', 'testclientAveContract', '2010-12-20 08:59:30.743', '2020-12-20 08:59:30.743', NULL, 'EN', 152636.56, 'EUR', 0, 'CI', false, 275);
INSERT INTO contracts (id, reference, description, fromdate, todate, duration, language, val, cur, version, missiontype, agreed, fk_customer) VALUES (278, 'PARFIMATSA_2', 'parfimat sa', '2010-12-01 00:00:00', '2019-12-11 00:00:00', NULL, 'EN', 10000, 'EUR', 0, 'AC', true, 19);


--
-- TOC entry 2208 (class 0 OID 17488)
-- Dependencies: 1659
-- Data for Name: customers; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (273, 'testclient@hotmail.fr', true, 'LU', 'hhghghgh', 'hghghj', 'testclient', NULL, NULL, NULL, 'testclient', '010254666', '010254666', NULL, NULL, NULL, 'testclient@hotmail.fr', '7777777', 0, 7, 7, 4, 8, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (15999, 'htpp//luxproduc.com', true, 'LU', '78 rue de la faiencerie
L-1511 luxembourg', 'fideos 
121 avenue de la faiencerie
a lattentionde pierre delorme', 'vente par correspondance et internet de bien de consommation', NULL, NULL, NULL, 'luxproduc S;a', '352 353535', '352 32 32 32 ', NULL, NULL, NULL, 'luxproduc@wanadoo.fr', '321654', 0, 7, 5, 4, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (16000, 'htpp//Dupont.com', true, 'LU', '12 ru du lavoir
L-5221 luxe,bourg', 'fideos
121 aveunue de la faiencerie
a lattenteion de paul dumur', 'transformation de lait creme ', NULL, NULL, NULL, 'Dupont', '352478569', '352145896', NULL, NULL, NULL, 'Dupont@wanadoo.fr', '321658', 0, 2, 7, 4, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (9, 'contact@email.lu', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Easy Distribution SA (ex EASYCARS SA)
', NULL, NULL, NULL, 'Easy Distribution SA (ex EASYCARS SA)', '+352 44 10 12 2000', '+352 44 10 12 2000', NULL, NULL, NULL, 'contact@email.lu', '9283', 1, 2, 6, 3, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (10, 'contact@email.lu', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Weisgerber Expertise
', NULL, NULL, NULL, 'Weisgerber Expertise', '+352 44 10 12 2000', '+352 44 10 12 2000', NULL, NULL, NULL, 'contact@email.lu', '9284', 1, 1, 5, 4, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (11, 'contact@email.lu', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'HERMES PARTNERS TRUST SERVICES SA
', NULL, NULL, NULL, 'HERMES PARTNERS TRUST SERVICES SA', NULL, '+352 44 10 12 20000', NULL, NULL, NULL, 'contact@email.lu', '9286', 1, 2, 4, 3, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (12, 'contact@email.lu', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Intruma Corporate Services SA
', NULL, NULL, NULL, 'Intruma Corporate Services SA', '+352 44 10 12 20000', '+352 44 10 12 20000', NULL, NULL, NULL, 'contact@email.lu', '9287', 1, 1, 6, 3, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (13, 'contact@email.lu', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Summerwind SA
', NULL, NULL, NULL, 'Summerwind SA', NULL, '+352 44 10 12 2000', NULL, NULL, NULL, 'contact@email.lu', '9289', 2, 3, 5, 3, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (14, 'contact@email.fr', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'APKieffer
', NULL, NULL, NULL, 'APKieffer', NULL, '+352 44 10122000', NULL, NULL, NULL, 'contact@email.fr', '9290', 2, 7, 4, 3, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (17, NULL, true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Laurad Holding
', NULL, NULL, NULL, 'Laurad Holding', NULL, '+333 87 96 368542', NULL, NULL, NULL, 'contact@email.fr', '9293', 2, 3, 6, 3, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (1, 'europarl.eu.lu', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'EUROPEAN PARLIAMENT-Imprest qtrly reviews    
', NULL, NULL, NULL, 'EUROPEAN PARLIAMENT-Imprest qtrly reviews', '+352 44 10 12 2000', '+352 44 10 12 2000', NULL, NULL, NULL, 'contact@europarl.eu.lu', '6363', 1, 1, 5, 3, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (5, 'contact@europarl.eu.lu', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'HPMC1 Sàrl
', NULL, NULL, NULL, 'HPMC1 Sàrl', NULL, '+352 44 10 12 2000', NULL, NULL, NULL, 'contact@europarl.eu.lu', '9000', 1, 2, 4, 6, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (650, NULL, true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Fournisseur de sable', '0001-05-18 BC', '0001-05-18 BC', NULL, 'SABLIERE HEIN SA', NULL, '20.45.96.369', NULL, NULL, NULL, 'a.marx@sabliere.lu', '10388', 2, 7, 6, 1, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (651, NULL, true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Fournisseur de sable', '0001-05-18 BC', '0001-05-18 BC', NULL, 'DIMPEX SA', NULL, '20.45.96.369', NULL, NULL, NULL, 'a.marx@sabliere.lu', '82633', 2, 2, 5, 2, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (652, NULL, true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Fournisseur de sable', '0001-05-18 BC', '0001-05-18 BC', NULL, 'GULF OIL INTERNATIONAL LTD', NULL, '20.45.96.369', NULL, NULL, NULL, 'a.marx@sabliere.lu', '11261', 3, 7, 4, 3, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (654, NULL, true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Fournisseur de sable', '0001-05-18 BC', '0001-05-18 BC', NULL, 'GAMET INVESTMENT SA', NULL, '20.45.96.369', NULL, NULL, NULL, 'a.marx@sabliere.lu', '12084', 3, 1, 4, 5, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (655, NULL, true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Fournisseur de sable', '0001-05-18 BC', '0001-05-18 BC', NULL, 'LUXLAIT SA', NULL, '20.45.96.369', NULL, NULL, NULL, 'a.marx@sabliere.lu', '12085', 3, 1, 6, 6, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (2, '0352 44 10 12 2000', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'EUROPEAN PARLIAMENT-canteen    
', NULL, NULL, NULL, 'EUROPEAN PARLIAMENT-canteen', '0352 44 10 12 2000', '0352 44 10 12 2000', NULL, NULL, NULL, 'contact@europarl.eu.lu', '6464', 1, 1, 5, 5, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (15, NULL, true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Caryos SA', NULL, NULL, NULL, 'Caryos SA', NULL, '+352 44 10 12 2000', NULL, NULL, NULL, 'contact.email@wanadoo.fr', '9291', 1, 2, 5, 3, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (16, NULL, true, 'FR', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Cologic
', NULL, NULL, NULL, 'Cologic', NULL, '+ 333 45 96 33210254', NULL, NULL, NULL, 'contact.email@pt.lu', '9292', 1, 3, 4, 4, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (18, NULL, true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'MATSA SA
', NULL, NULL, NULL, 'MATSA SA', NULL, '+333 98 75 963210', NULL, NULL, NULL, 'contact@email.fr', '9294', 1, 7, 6, 2, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (15998, 'htpp///orilux.com', true, 'LU', '12 rue de letang
L-1526 luxembourg', 'orilux s.a.
157 rue des forges
L-1523 Bertrange
A lattention de Monsieur Georges', 'vente de projet immobiler, achat, location de bureaux', NULL, NULL, NULL, 'orilux', '00352 38 65 27', '00352 38 65 45 99', NULL, NULL, NULL, 'orilux@wanadoo.fr', '15357', 1, 3, 3, 7, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (653, '', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Fournisseur de sable luxembourgeois', '0001-05-18 BC', '0001-05-18 BC', NULL, 'BORG LUXEMBOURG SA', '', '20.45.96.369', NULL, NULL, NULL, 'a.marx@sabliere.lu', '16000', 4, 3, 5, 4, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (274, 'retestestcompany@hotmail.lu', true, 'LU', 'retestestcompany', 'retestestcompany', 'retestestcompany', NULL, NULL, NULL, 'retestestcompany', '01234569874', '01234569874', NULL, NULL, NULL, 'retestestcompany@hotmail.lu', '99999', 0, 3, 6, 1, 9, 5892.6000000000004);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (275, 'testclientAveContract@inter.lu', true, 'LU', 'testclientAveContract', 'testclientAveContract', 'testclientAveContract', NULL, NULL, NULL, 'testclientAveContract', '0123569687', '0123569687', NULL, NULL, NULL, 'testclientAveContract@inter.lu', '14151718', 1, 7, 1, 4, 4, 152636.56);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (19, NULL, true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Parfimat SA
', NULL, NULL, NULL, 'Parfimat SA', NULL, '+352 44 52 69 78', NULL, NULL, NULL, 'contactemail@sa.kh', '9296', 2, 1, 5, 5, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (3, '', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'ANGLESEA CAPITAL SA', NULL, NULL, NULL, 'ANGLESEA CAPITAL SA', '+352 44 10 12 2000', '+352 44 10 12 2000', NULL, NULL, NULL, 'example@server.lu', '6565', 5, 2, 5, 2, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (4, 'contact@europarl.eu.lu', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'EUROPEAN PARLIAMENT-supermarket    
', NULL, NULL, NULL, 'EUROPEAN PARLIAMENT-supermarket', NULL, '+352 44 10 12 2000', NULL, NULL, NULL, 'contact@europarl.eu.lu', '6666', 1, 3, 4, 2, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (6, 'contact@europarl.eu.lu', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'HOLDUN SA
', NULL, NULL, NULL, 'HOLDUN SA', '+352 44 10 12 2000', '+352 44 10 12 2000', NULL, NULL, NULL, 'contact@europarl.eu.lu', '9280', 0, 7, 6, 4, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (7, 'contact@europarl.eu.lu', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Scancargo SA', NULL, NULL, NULL, 'Scancargo SA', '+352 44 10 12 2000', '+352 44 10 12 2000', NULL, NULL, NULL, 'contact@europarl.eu.lu', '9281', 1, 7, 5, 3, 1, 1000);
INSERT INTO customers (id, web_address, active, country, mainaddress, mainbillingaddress, activity, createdate, updatedate, updateduser, compy_name, fax, phone, mobile, p_phone, p_mobile, email, code, version, fk_associe, fk_manager, fk_origin, fk_contracttype, contract_amount) VALUES (8, 'Luxys SA', true, 'LU', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', 'Luxys SA
', NULL, NULL, NULL, 'Luxys SA', NULL, '+352 44 10 12 2000', NULL, NULL, NULL, 'contact@europarl.eu.lu', '9282', 1, 3, 4, 5, 1, 1000);


--
-- TOC entry 2209 (class 0 OID 17500)
-- Dependencies: 1660
-- Data for Name: declarations; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--



--
-- TOC entry 2198 (class 0 OID 17418)
-- Dependencies: 1649
-- Data for Name: docs_invoices; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--



--
-- TOC entry 2199 (class 0 OID 17421)
-- Dependencies: 1650
-- Data for Name: docs_missions; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--



--
-- TOC entry 2210 (class 0 OID 17508)
-- Dependencies: 1661
-- Data for Name: documents; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--



--
-- TOC entry 2211 (class 0 OID 17516)
-- Dependencies: 1662
-- Data for Name: email_data; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO email_data (id, sender_address, receiver_address, mailsubject, mailcontents, created, sentdate, status, type, fk_sender, fk_receiver) VALUES (234, 'murielle.badoux@interaudit.lu', 'murielle.badoux@interaudit.lu', 'Timesheet rejected', 'Timesheet incomplète', '2010-12-17 15:25:52.457', NULL, 'DONE_NOK', 'MISSION_COMMUNICATION', 3, 3);
INSERT INTO email_data (id, sender_address, receiver_address, mailsubject, mailcontents, created, sentdate, status, type, fk_sender, fk_receiver) VALUES (238, 'murielle.badoux@interaudit.lu', 'amila.beganovica@interaduit.lu', 'Invoice [ F-10/001 ] approved', 'F-10/001', '2010-12-17 15:49:37.495', NULL, 'DONE_NOK', 'FACTURATION_COMMUNICATION', 3, 11709);
INSERT INTO email_data (id, sender_address, receiver_address, mailsubject, mailcontents, created, sentdate, status, type, fk_sender, fk_receiver) VALUES (239, 'murielle.badoux@interaudit.lu', 'veronique.blocail@interaudit.lu', 'Invoice [ F-10/001 ] approved', 'F-10/001', '2010-12-17 15:49:37.498', NULL, 'DONE_NOK', 'FACTURATION_COMMUNICATION', 3, 541);
INSERT INTO email_data (id, sender_address, receiver_address, mailsubject, mailcontents, created, sentdate, status, type, fk_sender, fk_receiver) VALUES (248, 'murielle.badoux@interaudit.lu', 'amila.beganovica@interaduit.lu', 'Invoice [ F-10/002 ] approved', 'F-10/002', '2010-12-18 06:29:55.258', NULL, 'DONE_NOK', 'FACTURATION_COMMUNICATION', 3, 11709);
INSERT INTO email_data (id, sender_address, receiver_address, mailsubject, mailcontents, created, sentdate, status, type, fk_sender, fk_receiver) VALUES (249, 'murielle.badoux@interaudit.lu', 'veronique.blocail@interaudit.lu', 'Invoice [ F-10/002 ] approved', 'F-10/002', '2010-12-18 06:29:55.259', NULL, 'DONE_NOK', 'FACTURATION_COMMUNICATION', 3, 541);
INSERT INTO email_data (id, sender_address, receiver_address, mailsubject, mailcontents, created, sentdate, status, type, fk_sender, fk_receiver) VALUES (270, 'murielle.badoux@interaudit.lu', 'amila.beganovica@interaduit.lu', 'Invoice [ F-10/003 ] approved', 'F-10/003', '2010-12-19 13:30:29.338', NULL, 'DONE_NOK', 'FACTURATION_COMMUNICATION', 3, 11709);
INSERT INTO email_data (id, sender_address, receiver_address, mailsubject, mailcontents, created, sentdate, status, type, fk_sender, fk_receiver) VALUES (271, 'murielle.badoux@interaudit.lu', 'veronique.blocail@interaudit.lu', 'Invoice [ F-10/003 ] approved', 'F-10/003', '2010-12-19 13:30:29.342', NULL, 'DONE_NOK', 'FACTURATION_COMMUNICATION', 3, 541);
INSERT INTO email_data (id, sender_address, receiver_address, mailsubject, mailcontents, created, sentdate, status, type, fk_sender, fk_receiver) VALUES (383, 'murielle.badoux@interaudit.lu', 'murielle.badoux@interaudit.lu', 'Timesheet rejected', '', '2010-12-25 18:57:30.584', NULL, 'DONE_NOK', 'MISSION_COMMUNICATION', 3, 3);
INSERT INTO email_data (id, sender_address, receiver_address, mailsubject, mailcontents, created, sentdate, status, type, fk_sender, fk_receiver) VALUES (385, 'murielle.badoux@interaudit.lu', 'murielle.badoux@interaudit.lu', 'Timesheet rejected', 'Pas les bonnes heures', '2010-12-25 18:58:03.373', NULL, 'DONE_NOK', 'MISSION_COMMUNICATION', 3, 3);


--
-- TOC entry 2212 (class 0 OID 17524)
-- Dependencies: 1663
-- Data for Name: employees; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (15997, 0, 'julien', 'dupont', NULL, 'juli;dupont@interaudit.lu', 'JD', false, 'dupont', 'dupont', NULL, '0001-11-21 BC', '0001-11-22 09:06:01 BC', NULL, 30, 20, 27, 3);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (181, 1, 'Valentin', 'Nedeltchev', NULL, 'valentin.nedeltchev@interaudit.lu', 'VN', true, 'Nedeltchev', 'Nedeltchev', NULL, NULL, '0001-11-07 08:07:05 BC', NULL, 130, 120, 50, 3);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (241, 0, 'Jonathan', 'Picard', NULL, 'jonathan.picard@interaudit.lu', 'JP', true, 'picard', 'picard', NULL, NULL, '0001-11-08 14:26:26 BC', NULL, 0, 0, 0, 3);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (1, 0, 'Vincent', 'Dog', NULL, 'vincent.dog@interaudit.lu', 'VD', true, 'vincentdog', 'vincentdog', NULL, NULL, '0001-11-01 13:36:19 BC', NULL, 150, 150, 100, 4);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (2, 4, 'Edouard', 'Kostka', NULL, 'edouard.kostka@interausit.lu', 'EK', true, 'kostka', 'kostka', NULL, NULL, '0001-11-01 13:38:30 BC', NULL, 150, 120, 100, 4);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (4, 1, 'Stephane', 'Gaillard', NULL, 'stephane.gaillard@interaudit.lu', 'SG', true, 'gaillard', 'gaillard', NULL, NULL, '0001-11-01 13:48:02 BC', NULL, 125, 120, 80, 7);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (6, 0, 'Olivier', 'Jansen', NULL, 'olivier.jansen@interaudit.lu', 'OJ', true, 'jansen', 'jansen', NULL, NULL, '0001-11-01 13:52:48 BC', NULL, 120, 140, 56, 7);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (5, 14, 'Laurent', 'Mariani', NULL, 'laurent.mariani@interausit.lu', 'LM', true, 'mariani', 'mariani', NULL, NULL, '0001-11-01 13:50:01 BC', NULL, 170, 150, 100, 7);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (7, 0, 'Olivier', 'Biren', NULL, 'olivier.biren@interaudit.lu', 'OB', true, 'biren', 'biren', NULL, NULL, '0001-11-01 14:00:31 BC', NULL, 170, 150, 100, 5);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (541, 5, 'Veronique', 'Blocail', NULL, 'veronique.blocail@interaudit.lu', 'VB', true, 'blocabe12', 'blocabe', NULL, NULL, '0001-12-13 17:02:23 BC', NULL, 150, 120, 100, 6);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (544, 1, 'Phillippe', 'Lemoine', NULL, 'Phillippe.Lemoine@interaudit.lu', 'PL', true, 'Lemoine', 'Lemoine', NULL, NULL, '0001-12-13 17:32:42 BC', NULL, 0, 0, 0, 3);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (542, 5, 'Dimitri', 'Hubin', NULL, 'dimitri.hubin@interaudit.lu', 'DH', true, 'Hubin', 'Hubin', NULL, NULL, '0001-12-13 17:29:35 BC', NULL, 60, 50, 25, 3);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (543, 4, 'Calogero', 'Midioni', NULL, 'Calogero.Midioni@interaudit.lu', 'CM', true, 'Calogero', 'Calogero', NULL, NULL, '0001-12-13 17:31:21 BC', NULL, 140, 130, 90, 3);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (545, 2, 'Claire', 'Pignot', NULL, 'claire.pignot@interaduit.lu', 'CP', true, 'Pignot', 'Pignot', NULL, NULL, '0001-12-13 17:34:08 BC', NULL, 170, 150, 100, 3);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (546, 1, 'laszlo', 'szabadhegyi', NULL, 'laszlo.szabadhegyi@interaudit.lu', 'LS', true, 'Szraberg', 'Szraberg', NULL, NULL, '0001-12-13 17:35:46 BC', NULL, 0, 0, 0, 3);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (547, 0, 'Sebastien', 'Radelet', NULL, 'Sebastien.Radelet@interaudit.lu', 'SR', true, 'Radelet', 'Radelet', NULL, NULL, '0001-12-13 17:37:06 BC', NULL, 0, 0, 0, 3);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (548, 2, 'Elsa', 'Gaspard', NULL, 'Elsa.Gaspard@interaudit.lu', 'EG', true, 'Gaspard', 'Gaspard', NULL, NULL, '0001-12-13 17:38:32 BC', NULL, 60, 50, 25, 3);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (549, 2, 'Medhi', 'Zidane', NULL, 'Medhi.Zidane@interaudit.lu', 'MZ', false, 'Zidane', 'Zidane', NULL, NULL, '0001-12-13 17:40:00 BC', NULL, 0, 0, 0, 3);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (11709, 1, 'Amila', 'Beganovic', NULL, 'amila.beganovica@interaduit.lu', 'AB', true, 'Amila', 'Amila', NULL, '0001-07-15 BC', '0001-07-12 10:29:23 BC', NULL, 65, 60, 15, 6);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (11710, 2, 'Valerie', 'Crete', NULL, 'valerie.crete@interaudit.lu', 'VC', true, 'crete', 'crete', NULL, '0001-07-13 BC', '0001-07-12 10:31:18 BC', NULL, 70, 50, 15, 3);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (11711, 3, 'Yves', 'Deschenaux', NULL, 'yves.deschenaus@interaudit.lu', 'YD', false, 'deschenaux', 'deschenaux', NULL, '0001-07-13 BC', '0001-07-12 10:32:53 BC', NULL, 80, 50, 15, 3);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (11712, 0, 'Natalie', 'Semedo', NULL, 'natalie.semedo@interaudit.lu', 'NS', true, 'semedo', 'semedo', NULL, '0001-07-14 BC', '0001-07-12 10:34:35 BC', NULL, 80, 70, 15, 3);
INSERT INTO employees (id, version, firstname, lastname, jobtitle, email, code, active, password, login, updateuser, date_of_hiring, createdate, modifieddate, prixvente, prixrevient, couthoraire, fk_position) VALUES (3, 15, 'Murielle', 'Badoux', NULL, 'murielle.badoux@interaudit.lu', 'MB', true, 'badoux', 'badoux', NULL, '2001-07-06', '0001-11-01 13:41:39 BC', NULL, 120, 100, 90, 5);


--
-- TOC entry 2214 (class 0 OID 17542)
-- Dependencies: 1665
-- Data for Name: event_planning; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (110, 2010, 49, false, 545, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (111, 2010, 48, false, 6, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (112, 2010, 50, false, 543, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (113, 2010, 50, false, 2, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (115, 2010, 50, false, 1, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (119, 2010, 50, false, 7, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (121, 2010, 50, false, 5, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (123, 2010, 50, false, 6, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (125, 2010, 50, false, 4, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (128, 2010, 50, false, 545, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (130, 2010, 50, false, 542, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (132, 2010, 50, false, 548, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (134, 2010, 50, false, 241, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (136, 2010, 50, false, 15997, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (138, 2010, 50, false, 546, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (140, 2010, 50, false, 549, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (142, 2010, 50, false, 11712, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (144, 2010, 50, false, 544, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (146, 2010, 50, false, 547, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (148, 2010, 50, false, 181, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (150, 2010, 50, false, 11710, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (152, 2010, 50, false, 11711, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (154, 2010, 50, false, 11709, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (156, 2010, 50, false, 541, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (160, 2010, 48, false, 3, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (164, 2010, 48, false, 2, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (166, 2010, 48, false, 1, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (169, 2010, 48, false, 7, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (171, 2010, 48, false, 5, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (174, 2010, 48, false, 4, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (176, 2010, 48, false, 543, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (178, 2010, 48, false, 545, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (180, 2010, 48, false, 542, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (182, 2010, 48, false, 548, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (184, 2010, 48, false, 241, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (186, 2010, 48, false, 15997, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (188, 2010, 48, false, 546, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (190, 2010, 48, false, 549, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (192, 2010, 48, false, 11712, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (194, 2010, 48, false, 544, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (196, 2010, 48, false, 547, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (198, 2010, 48, false, 181, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (200, 2010, 48, false, 11710, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (202, 2010, 48, false, 11711, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (204, 2010, 48, false, 11709, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (206, 2010, 48, false, 541, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (208, 2010, 49, false, 1, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (210, 2010, 49, false, 3, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (212, 2010, 49, false, 15997, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (214, 2010, 49, false, 546, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (216, 2010, 49, false, 549, 22);
INSERT INTO event_planning (id, year, weeknumber, validated, fk_employee, fk_planning) VALUES (117, 2010, 50, true, 3, 22);


--
-- TOC entry 2213 (class 0 OID 17534)
-- Dependencies: 1664
-- Data for Name: events; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO events (id, year, month, day, starthour, endhour, type, title, dateofevent, valid, fk_employee, fk_activity, task_id) VALUES (161, 2010, 11, 349, 4, 32, 'EUROPEAN PARLIAMENT-canteen', 'EUROPEAN PARLIAMENT-canteen', '2010-12-15 14:41:29.607', false, 3, 95, NULL);
INSERT INTO events (id, year, month, day, starthour, endhour, type, title, dateofevent, valid, fk_employee, fk_activity, task_id) VALUES (162, 2010, 11, 351, 0, 17, 'Caryos SA', 'fnlkfkjldfgd*lsdfùkldfùdfl*
s*lsl*g*gfsd*', '2010-12-17 14:42:09.116', false, 3, 91, NULL);
INSERT INTO events (id, year, month, day, starthour, endhour, type, title, dateofevent, valid, fk_employee, fk_activity, task_id) VALUES (163, 2010, 11, 351, 40, 46, 'Cologic', 'Cologic ', '2010-12-17 15:22:20.733', false, 3, 85, NULL);
INSERT INTO events (id, year, month, day, starthour, endhour, type, title, dateofevent, valid, fk_employee, fk_activity, task_id) VALUES (263, 2010, 11, 356, 14, 18, 'ANGLESEA CAPITAL SA', 'Tel: 44 10 12 36 98
Fax : 40 10 12 20 01', '2010-12-22 10:49:23.976', false, 3, 65, NULL);


--
-- TOC entry 2215 (class 0 OID 17547)
-- Dependencies: 1666
-- Data for Name: exercises; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO exercises (id, version, year, status, startdate, enddate, isappproved, tot_exp_amount, inflationpercentage, tot_rep_amount, tot_real_amount, tot_inactif_amount) VALUES (24, 7, 2010, 'ONGOING', '2010-01-01 00:00:00', '2010-12-31 00:00:00', true, 333172.48999999999, 1, 0, 7726, 0);


--
-- TOC entry 2217 (class 0 OID 17564)
-- Dependencies: 1668
-- Data for Name: invoice_deduction_fees; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO invoice_deduction_fees (id, createdate, codejustification, justification, value, version, fk_facture) VALUES (246, '2010-12-18', 'lib.fa.men.trop.percu', 'Trop perçu', 15, 1, 243);
INSERT INTO invoice_deduction_fees (id, createdate, codejustification, justification, value, version, fk_facture) VALUES (268, '2010-12-19', 'lib.fa.men.trop.percu', 'Trop perçu', 120, 1, 265);


--
-- TOC entry 2218 (class 0 OID 17572)
-- Dependencies: 1669
-- Data for Name: invoice_fees; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO invoice_fees (id, createdate, codejustification, justification, value, version, fk_facture) VALUES (245, '2010-12-18', 'lib.fa.men.blanchiment', 'Frais en relation avec les obligations 
professionnelles en matière de prévention 
du blanchiment d''argent et du financement 
du terrosrisme conformément à la loi du 
12.11.2004', 150, 3, 243);
INSERT INTO invoice_fees (id, createdate, codejustification, justification, value, version, fk_facture) VALUES (267, '2010-12-19', 'lib.fa.men.blanchiment', 'Frais en relation avec les obligations %n professionnelles en matière de prévention %n du blanchiment d''argent et du financement %n du terrosrisme conformément à la loi du %n 12.11.2004', 50, 0, 265);


--
-- TOC entry 2219 (class 0 OID 17580)
-- Dependencies: 1670
-- Data for Name: invoice_reminds; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO invoice_reminds (id, reminddate, sent, libelle, libfin, lang, ordre, year, refasssec, version, fk_sender, fk_customer) VALUES (251, '2010-12-18', true, 'According to our records, the following amount remains unpaid', 'We look forward to settlement of the amount due at your best convenience to our bank account N° IBAN LU19 0019 1255 8610 4000 with Banque et Caisse d''Epargne de l''Etat ,
Luxembourg.', 'EN', 1, 2010, 'EK/mb-10-9291', 2, 3, 15);
INSERT INTO invoice_reminds (id, reminddate, sent, libelle, libfin, lang, ordre, year, refasssec, version, fk_sender, fk_customer) VALUES (412, '2010-12-26', true, 'According to our records, the following amount remains unpaid', 'We look forward to settlement of the amount due at your best convenience to our bank account N° IBAN LU19 0019 1255 8610 4000 with Banque et Caisse d''Epargne de l''Etat ,
Luxembourg.', 'EN', 2, 2010, 'EK/mb-10-9291', 2, 3, 15);


--
-- TOC entry 2200 (class 0 OID 17426)
-- Dependencies: 1651
-- Data for Name: invoice_reminds_relation; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO invoice_reminds_relation (remindid, invoiceid) VALUES (251, 243);
INSERT INTO invoice_reminds_relation (remindid, invoiceid) VALUES (412, 243);


--
-- TOC entry 2236 (class 0 OID 18000)
-- Dependencies: 1688
-- Data for Name: invoice_translations; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.ra.men.bas', 'fr', 'Si le relevé devait se croiser avec votre paiement, nous vous prions de le considérer comme nul et non avenu.');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.ra.men.bas', 'en', 'We look forward to settlement of the amount due at your best convenience to our bank account N° IBAN LU19 0019 1255 8610 4000 with Banque et Caisse d''Epargne de l''Etat ,
Luxembourg.');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.ra.men.bas', 'de', 'Wir bitten Sie die Zahlung so bald wie möglich auf unser Konto N° IBAN LU19 0019 1255 8610 4000 bei Banque et Caisse d''Epargne de l''Etat , Luxembourg zu tätigen.

Wir danken Ihnen im voraus verbleiben,

Mit freundlichen Grüssen');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.ra.men.haut', 'fr', 'Nous nous permettons de vous signaler que le solde ci-après est venu à échéance. Nous vous prions de bien vouloir nous régler le montant échu sur notre compte 
auprès de la Banque et Caisse d''Epargne de l''Etat N° IBAN LU19 0019 1255 8610 4000 dans les meilleurs délais.');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.ra.men.haut', 'en', 'According to our records, the following amount remains unpaid');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.ra.men.haut', 'de', 'Wir möchen Sie darauf hinweisen, dass die folgenden Rechnungen noch immer offenstehen : ');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.deduire', 'fr', 'A déduire');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.deduire', 'en', 'Less');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.deduire', 'de', 'Weniger');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.du', 'fr', 'du');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.rappel.paiement', 'de', 'bei Zahlung unbedingt angeben');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.titre.honoraire', 'fr', 'NOTE D''''HONORAIRES');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.honoraires', 'de', 'Fûr die Prûfung Ihres Jahresabschlusses %n per <date_prestation> berechnen wir Ihnen eine %n erste Anzahlung');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.supplement', 'de', 'Supplément suite aux modifications de vos comptes annuels');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.blanchiment', 'de', 'Kosten in Verbindung der beruflichen %n Verplichtung hinsichtlich der Prâvention %n der Geldwâsche und Finanzierung von %n Terroristen gemâss dem Gesetz vom %n 12.11.2004');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.cssf', 'de', 'Gebûhr CSSF fûr die Beaufsichtigung der %n Prûfungsbranche im Rahmen der %n Grossherzoglichen Verordnung vom 18.12.2009');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.delai.paiement', 'de', 'Wir bitten un Ûberweisung innerhalb von %1$s Tagen auf unser Konto Nr %2$s bei der Banque et Caisse d''Epargne de l''Etat.');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.acompte', 'de', 'anzahlung');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.tva', 'fr', 'TVA');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.ref', 'de', NULL);
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.delai.paiement', 'fr', 'Cette facture est payable endéans %1$s jours sur notre compte %2$s auprès de la Banque et Caisse d''Epargne de l''Etat.');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.total', 'de', 'TOTAL');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.rp.titre', 'de', 'ZAHLUNGSERINNERUNG');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.rp.rappel', 'de', 'Rückruf');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.rp.note.honoraires', 'de', 'Rechnung');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.du', 'en', 'of');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.du', 'de', 'von');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.rappel.paiement', 'fr', 'A rappeler lors du paiement');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.rappel.paiement', 'en', 'Please give as reference with payment');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.acompte', 'fr', 'acompte');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.acompte', 'en', 'interim');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.delai.paiement', 'en', 'Please arrange payment within %1$s days to our account N° %2$s with Banque et Caisse d''Epargne de l''Etat');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.blanchiment', 'fr', 'Frais en relation avec les obligations %n professionnelles en matière de prévention %n du blanchiment d''argent et du financement %n du terrosrisme conformément à la loi du %n 12.11.2004');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.blanchiment', 'en', 'Expenses relating to our profesional %n obligations with regards to the %n prevention of money laundering and the %n financing of terrosrism');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.cssf', 'fr', 'Frais en relation avec les taxes à percevoir par la %n CSSF pour la supervision de la profession %n d''audit, conformément au Règlement Grand-Ducal du 18.12.2009.');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.cssf', 'en', 'Expenses relating to taxes by the CSSF for the %n supervision of the auditing profession, under the %n Grand Ducal Regulation of 18.12.2009');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.honoraires', 'fr', 'Nos prestations relatives à la révision de %n vos comptes annuels au <date_prestation> selon %n les normes internationales de révision %n (ISA).');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.honoraires', 'en', 'Billing for professional service rendered %n in connection with the audit of your financial %n statements at  <date_prestation>');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.ref', 'fr', 'Ref.');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.ref', 'en', 'Ref.');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.rp.note.honoraires', 'fr', 'Note d''honoraires');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.rp.note.honoraires', 'en', 'Note of fees');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.rp.rappel', 'fr', 'Rappel');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.rp.rappel', 'en', 'Reminder');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.rp.titre', 'fr', 'RELEVE DE COMPTE');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.rp.titre', 'en', 'STATEMENT OF ACCOUNT');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.supplement', 'fr', 'Supplément suite aux modifications de vos comptes annuels');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.supplement', 'en', 'Supplément suite aux modifications de vos comptes annuels');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.titre.honoraire', 'de', 'HONORARABRECHUNG');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.titre.honoraire', 'en', 'NOTE OF FEES');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.total', 'fr', 'TOTAL');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.total', 'en', 'TOTAL');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.tva', 'en', 'VAT');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.tva', 'de', 'VAT');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.fact.finale', 'fr', 'Facture finale');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.fact.finale', 'de', 'Endabrechnung');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.fact.finale', 'en', 'Finale bill');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.note.credit', 'fr', 'Note de credit');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.note.credit', 'en', 'Note de credit');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.note.credit', 'de', 'Note de credit');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.trop.percu', 'fr', 'Trop perçu');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.trop.percu', 'en', 'Trop perçu');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.trop.percu', 'de', 'Trop perçu');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.remise.exceptionnelle', 'de', 'Remise exceptionnelle');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.remise.exceptionnelle', 'fr', 'Remise exceptionnelle');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.remise.exceptionnelle', 'en', 'Remise exceptionnelle');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.advance', 'de', 'Notre demande d''acompte');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.advance', 'fr', 'Notre demande d''acompte');
INSERT INTO invoice_translations (key, lang, value) VALUES ('lib.fa.men.advance', 'en', 'Notre demande d''acompte');


--
-- TOC entry 2216 (class 0 OID 17554)
-- Dependencies: 1667
-- Data for Name: invoices; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO invoices (id, version, type, language, tva, delaipaiement, datefacture, sentdate, dateofpayment, currency, year, mois, libelle, honoraires, amount, amount_net, reference, parent_reference, refasssec, status, pays, libhonoraires, libdelai, billaddress, custcode, custname, approved, sent, exercisemandat, bank_id, project_id, fk_sender, fk_partner, fk_creator) VALUES (410, 0, 'CN', 'FR', 15, 0, '2010-12-26', NULL, NULL, 'EUR', '2010', '11', 'Note de credit', 100, 115, 100, 'NC-10/004', 'F-10/002', NULL, 'pending', 'LU', 'Nos prestations relatives à la révision de %n vos comptes annuels au <date_prestation> selon %n les normes internationales de révision %n (ISA).', 'Cette facture est payable endéans 0 jours sur notre compte  auprès de la Banque et Caisse d''Epargne de l''Etat.', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', '9291', 'Caryos SA', false, false, '2013', NULL, 90, NULL, 2, 3);
INSERT INTO invoices (id, version, type, language, tva, delaipaiement, datefacture, sentdate, dateofpayment, currency, year, mois, libelle, honoraires, amount, amount_net, reference, parent_reference, refasssec, status, pays, libhonoraires, libdelai, billaddress, custcode, custname, approved, sent, exercisemandat, bank_id, project_id, fk_sender, fk_partner, fk_creator) VALUES (243, 10, 'AD', 'FR', 15, 15, '2010-12-18', '2010-12-18', NULL, 'EUR', '2010', '11', 'acompte', 2419, 2937.0999999999999, 2554, 'F-10/002', NULL, 'EK/mb-13-9291', 'sent', 'LU', 'Nos prestations relatives à la révision de %n vos comptes annuels au <date_prestation> selon %n les normes internationales de révision %n (ISA).', 'Cette facture est payable endéans 15 jours sur notre compte 19556012 auprès de la Banque et Caisse d''Epargne de l''Etat.', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', '9291', 'Caryos SA', true, true, '2013', 302, 90, 3, 2, 3);
INSERT INTO invoices (id, version, type, language, tva, delaipaiement, datefacture, sentdate, dateofpayment, currency, year, mois, libelle, honoraires, amount, amount_net, reference, parent_reference, refasssec, status, pays, libhonoraires, libdelai, billaddress, custcode, custname, approved, sent, exercisemandat, bank_id, project_id, fk_sender, fk_partner, fk_creator) VALUES (235, 4, 'AD', 'EN', 15, 15, '2010-12-17', '2010-12-17', '2010-12-17', 'EUR', '2010', '11', 'interim', 2610, 3001.5, 2610, 'F-10/001', NULL, 'EK/mb-10-6565', 'paid', 'LU', 'Billing for professional service rendered %n in connection with the audit of your financial %n statements at  <date_prestation>', 'Please arrange payment within 15 days to our account N° 19556012 with Banque et Caisse d''Epargne de l''Etat', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', '6565', 'ANGLESEA CAPITAL SA', true, true, '2010', 302, 64, 3, 2, 3);
INSERT INTO invoices (id, version, type, language, tva, delaipaiement, datefacture, sentdate, dateofpayment, currency, year, mois, libelle, honoraires, amount, amount_net, reference, parent_reference, refasssec, status, pays, libhonoraires, libdelai, billaddress, custcode, custname, approved, sent, exercisemandat, bank_id, project_id, fk_sender, fk_partner, fk_creator) VALUES (265, 11, 'AD', 'FR', 20, 15, '2010-12-19', '2010-12-19', NULL, 'EUR', '2010', '11', 'acompte', 2532, 2954.4000000000001, 2462, 'F-10/003', NULL, 'EK/mb-10-82633', 'sent', 'LU', 'Nos prestations relatives à la révision de %n vos comptes annuels au <date_prestation> selon %n les normes internationales de révision %n (ISA).', 'Cette facture est payable endéans 15 jours sur notre compte 19556012 auprès de la Banque et Caisse d''Epargne de l''Etat.', 'Rue J.P Bausch
L-4023 ESCH-SUR-ALZETTE', '82633', 'DIMPEX SA', true, true, '2010', 302, 100, 3, 2, 3);


--
-- TOC entry 2220 (class 0 OID 17588)
-- Dependencies: 1671
-- Data for Name: item_event_planning; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (114, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:42.349', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 113);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (116, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:42.485', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 115);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (120, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:42.831', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 119);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (122, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:42.932', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 121);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (124, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:43.098', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 123);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (126, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:43.253', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 125);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (127, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:43.445', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 112);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (129, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:43.557', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 128);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (131, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:43.714', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 130);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (133, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:43.871', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 132);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (135, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:44.027', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 134);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (137, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:44.198', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 136);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (139, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:44.388', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 138);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (141, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:44.571', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 140);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (143, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:44.747', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 142);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (145, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:44.935', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 144);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (147, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:45.111', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 146);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (149, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:45.288', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 148);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (151, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:45.464', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 150);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (153, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:45.637', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 152);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (155, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:45.806', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 154);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (157, true, 64, 'ANGLESEA CAPITAL SA', '2010-12-17 13:40:45.981', 'NA', 5, '2010-12-13 13:40:41.745', '2010-12-17 13:40:41.745', NULL, NULL, 0, 'd.M.yyyy', 156);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (158, true, 60, 'APKieffer', '2010-12-17 13:41:27.755', 'NA', 5, '2010-12-13 00:00:00', '2010-12-17 00:00:00', NULL, NULL, 0, 'd.M.yyyy', 117);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (209, true, 108, 'BORG LUXEMBOURG SA', '2010-12-17 14:52:44.508', 'NA', 5, '2010-12-06 14:52:43.942', '2010-12-10 14:52:43.942', NULL, NULL, 0, 'd.M.yyyy', 208);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (211, true, 108, 'BORG LUXEMBOURG SA', '2010-12-17 14:52:44.584', 'NA', 5, '2010-12-06 14:52:43.942', '2010-12-10 14:52:43.942', NULL, NULL, 0, 'd.M.yyyy', 210);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (213, true, 100, 'DIMPEX SA', '2010-12-17 14:53:26.308', 'NA', 5, '2010-12-06 14:53:25.745', '2010-12-10 14:53:25.745', NULL, NULL, 0, 'd.M.yyyy', 212);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (215, true, 100, 'DIMPEX SA', '2010-12-17 14:53:26.423', 'NA', 5, '2010-12-06 14:53:25.745', '2010-12-10 14:53:25.745', NULL, NULL, 0, 'd.M.yyyy', 214);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (217, true, 100, 'DIMPEX SA', '2010-12-17 14:53:26.572', 'NA', 5, '2010-12-06 14:53:25.745', '2010-12-10 14:53:25.745', NULL, NULL, 0, 'd.M.yyyy', 216);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (159, true, 94, 'EUROPEAN PARLIAMENT-canteen', '2010-12-17 13:41:34.518', 'NA', 5, '2010-12-13 00:00:00', '2010-12-17 00:00:00', '2010-12-15 15:26:09.067', '2010-12-15 15:26:09.067', 7, 'd.M.yyyy', 117);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (386, true, 80, 'APKieffer', '2010-12-25 21:14:40.754', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 164);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (387, true, 80, 'APKieffer', '2010-12-25 21:14:40.76', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 166);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (388, true, 80, 'APKieffer', '2010-12-25 21:14:40.766', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 160);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (389, true, 80, 'APKieffer', '2010-12-25 21:14:40.769', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 169);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (390, true, 80, 'APKieffer', '2010-12-25 21:14:40.776', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 171);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (391, true, 80, 'APKieffer', '2010-12-25 21:14:40.782', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 111);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (392, true, 80, 'APKieffer', '2010-12-25 21:14:40.785', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 174);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (393, true, 80, 'APKieffer', '2010-12-25 21:14:40.793', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 176);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (394, true, 80, 'APKieffer', '2010-12-25 21:14:40.801', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 178);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (395, true, 80, 'APKieffer', '2010-12-25 21:14:40.808', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 180);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (396, true, 80, 'APKieffer', '2010-12-25 21:14:40.816', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 182);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (397, true, 80, 'APKieffer', '2010-12-25 21:14:40.824', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 184);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (398, true, 80, 'APKieffer', '2010-12-25 21:14:40.84', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 186);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (399, true, 80, 'APKieffer', '2010-12-25 21:14:40.848', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 188);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (400, true, 80, 'APKieffer', '2010-12-25 21:14:40.856', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 190);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (401, true, 80, 'APKieffer', '2010-12-25 21:14:40.862', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 192);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (402, true, 80, 'APKieffer', '2010-12-25 21:14:40.87', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 194);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (403, true, 80, 'APKieffer', '2010-12-25 21:14:40.879', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 196);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (404, true, 80, 'APKieffer', '2010-12-25 21:14:40.888', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 198);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (405, true, 80, 'APKieffer', '2010-12-25 21:14:40.895', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 200);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (406, true, 80, 'APKieffer', '2010-12-25 21:14:40.902', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 202);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (407, true, 80, 'APKieffer', '2010-12-25 21:14:40.909', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 204);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (408, true, 80, 'APKieffer', '2010-12-25 21:14:40.918', 'NA', 5, '2010-11-29 21:14:40.664', '2010-12-03 21:14:40.664', NULL, NULL, 0, 'd.M.yyyy', 206);
INSERT INTO item_event_planning (id, mission, identity, title, dateofevent, durationtype, duration, expectedstartdate, expectedenddate, realstartdate, realenddate, totalhoursspent, sformat, fk_eventplanning) VALUES (409, true, 76, 'Parfimat SA', '2010-12-25 21:15:02.269', 'NA', 5, '2010-11-29 00:00:00', '2010-12-03 00:00:00', NULL, NULL, 0, 'd.M.yyyy', 160);


--
-- TOC entry 2221 (class 0 OID 17596)
-- Dependencies: 1672
-- Data for Name: message; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO message (id, subject, contents, createdate, sentdate, emailslist, read, parent_id, from_id, to_id, mission_id) VALUES (254, 'Cologic avancement', '<P>Status de des dossiers du client.<br>Mettre documents sur le réseau<br></P>', '2010-12-18 07:05:45.123', NULL, 'Elsa.Gaspard@interaudit.lu;dimitri.hubin@interaudit.lu;', true, NULL, 3, NULL, 84);
INSERT INTO message (id, subject, contents, createdate, sentdate, emailslist, read, parent_id, from_id, to_id, mission_id) VALUES (261, 'Re : Cologic avancement', 'réponse test<br><br>---------------------------------------------<br>From:Badoux Murielle<br>Sent:2010-12-18 07:05:45.123<br>To:Elsa.Gaspard@interaudit.lu;dimitri.hubin@interaudit.lu;<br>Subject:Cologic avancement<br><br>
<P>Status de des dossiers du client.<br>Mettre documents sur le réseau<br></P>', '2010-12-18 08:01:11.673', NULL, 'murielle.badoux@interaudit.lu', true, NULL, 3, NULL, 84);
INSERT INTO message (id, subject, contents, createdate, sentdate, emailslist, read, parent_id, from_id, to_id, mission_id) VALUES (262, 'Re : Re : Cologic avancement', 'retest des réponses<br><br>---------------------------------------------<br>From:Badoux Murielle<br>Sent:2010-12-18 08:01:11.673<br>To:murielle.badoux@interaudit.lu<br>Subject:Re : Cologic avancement<br><br>réponse test<br><br>---------------------------------------------<br>From:Badoux Murielle<br>Sent:2010-12-18 07:05:45.123<br>To:Elsa.Gaspard@interaudit.lu;dimitri.hubin@interaudit.lu;<br>Subject:Cologic avancement<br><br>
<P>Status de des dossiers du client.<br>Mettre documents sur le réseau<br></P>', '2010-12-18 08:02:15.997', NULL, 'murielle.badoux@interaudit.lu', true, NULL, 3, NULL, 84);
INSERT INTO message (id, subject, contents, createdate, sentdate, emailslist, read, parent_id, from_id, to_id, mission_id) VALUES (264, 'subject', '<br><ul><li><strong>blabla</strong></li><li style="font-family: yui-tmp;"><strong style="background-color: #ffffff;">blab lkkq</strong></li></ul>', '2010-12-19 07:11:54.03', NULL, 'juli;dupont@interaudit.lu;stephane.gaillard@interaudit.lu;Phillippe.Lemoine@interaudit.lu;', true, NULL, 3, NULL, 100);


--
-- TOC entry 2224 (class 0 OID 17620)
-- Dependencies: 1675
-- Data for Name: mission_costs; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--



--
-- TOC entry 2222 (class 0 OID 17604)
-- Dependencies: 1673
-- Data for Name: missions; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (54, '2010', 'SABLIERE HEIN SA', 0, '10388_2010', 'SABLIERE HEIN SA', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'RA', 0, 'DE', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 29, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (56, '2010', 'HERMES PARTNERS TRUST SERVICES SA', 0, '9286_2010', 'HERMES PARTNERS TRUST SERVICES SA', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'AC', 0, 'FR', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 32, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (58, '2010', 'HPMC1 SÀRL', 0, '9000_2010', 'HPMC1 SÀRL', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'CONSO', 0, 'EN', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 28, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (62, '2010', 'EUROPEAN PARLIAMENT-IMPREST QTRLY REVIEWS', 0, '6363_2010', 'EUROPEAN PARLIAMENT-IMPREST QTRLY REVIEWS', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'MCOMM', 0, 'FR', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 46, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (66, '2010', 'LUXLAIT SA', 0, '12085_2010', 'LUXLAIT SA', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'AC', 0, 'EN', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 45, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (68, '2010', 'GAMET INVESTMENT SA', 0, '12084_2010', 'GAMET INVESTMENT SA', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'AC', 0, 'EN', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 51, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (70, '2010', 'LUXLAIT SA', 0, '12085_2010', 'LUXLAIT SA', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'RA', 0, 'EN', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 27, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (72, '2010', 'INTRUMA CORPORATE SERVICES SA', 0, '9287_2010', 'INTRUMA CORPORATE SERVICES SA', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'MCOMM', 0, 'EN', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 44, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (74, '2010', 'HOLDUN SA', 0, '9280_2010', 'HOLDUN SA', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'MCOMM', 0, 'FR', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 38, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (78, '2010', 'SUMMERWIND SA', 0, '9289_2010', 'SUMMERWIND SA', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'RA', 0, 'EN', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 47, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (82, '2010', 'GULF OIL INTERNATIONAL LTD', 0, '11261_2010', 'GULF OIL INTERNATIONAL LTD', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'AC', 0, 'EN', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 34, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (86, '2010', 'GAMET INVESTMENT SA', 0, '12084_2010', 'GAMET INVESTMENT SA', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'RC', 0, 'DE', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 40, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (88, '2010', 'LAURAD HOLDING', 0, '9293_2010', 'LAURAD HOLDING', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'AC', 0, 'DE', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 33, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (92, '2010', 'EUROPEAN PARLIAMENT-SUPERMARKET', 0, '6666_2010', 'EUROPEAN PARLIAMENT-SUPERMARKET', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'MISSPE', 0, 'DE', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 41, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (96, '2010', 'LUXYS SA', 0, '9282_2010', 'LUXYS SA', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'AC', 0, 'FR', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 52, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (98, '2010', 'SCANCARGO SA', 0, '9281_2010', 'SCANCARGO SA', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'RC', 0, 'FR', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 49, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (102, '2010', 'EASY DISTRIBUTION SA (EX EASYCARS SA)', 0, '9283_2010', 'EASY DISTRIBUTION SA (EX EASYCARS SA)', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'RC', 0, 'EN', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 48, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (104, '2010', 'MATSA SA', 0, '9294_2010', 'MATSA SA', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'CONSO', 0, 'DE', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 37, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (106, '2010', 'WEISGERBER EXPERTISE', 0, '9284_2010', 'WEISGERBER EXPERTISE', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'PENDING', 'CONSO', 0, 'FR', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 42, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (60, '2010', 'APKIEFFER', 50, '9290_2010', 'APKIEFFER', '2010-12-17', '2010-12-17', NULL, NULL, NULL, NULL, 'ONGOING', 'MCOMM', 1, 'FR', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 39, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (94, '2010', 'EUROPEAN PARLIAMENT-CANTEEN', 50, '6464_2010', 'EUROPEAN PARLIAMENT-CANTEEN', '2010-12-17', '2010-12-17', NULL, NULL, NULL, NULL, 'ONGOING', 'RA', 2, 'DE', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 25, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (90, '2010', 'CARYOS SA', 0, '9291_2010', 'CARYOS SA', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'ONGOING', 'RA', 1, 'EN', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 36, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (108, '2010', 'BORG LUXEMBOURG SA', 50, '16000_2010', 'BORG LUXEMBOURG SA', '2010-12-17', '2010-12-17', NULL, NULL, NULL, NULL, 'ONGOING', 'RC', 1, 'DE', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 30, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (84, '2010', 'COLOGIC', 0, '9292_2010', 'COLOGIC', '2010-12-17', NULL, NULL, NULL, NULL, NULL, 'ONGOING', 'MISSPE', 4, 'FR', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 35, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (64, '2010', 'ANGLESEA CAPITAL SA', 0, '6565_2010', 'ANGLESEA CAPITAL SA', '2010-12-17', NULL, '2010-12-15', NULL, NULL, NULL, 'ONGOING', 'CONSO', 3, 'EN', 'STOPPED', 'REVIEWED', 'Test', 'DRAFT_ISSUED_TO_CLIENT', 43, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (100, '2010', 'DIMPEX SA', 50, '82633_2010', 'DIMPEX SA', '2010-12-17', '2010-12-17', NULL, NULL, NULL, NULL, 'ONGOING', 'RA', 2, 'DE', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 50, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (80, '2010', 'APKIEFFER', 51, '9290_2010', 'APKIEFFER', '2010-12-17', '2010-12-25', NULL, NULL, NULL, NULL, 'ONGOING', 'MISSPE', 1, 'EN', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 26, NULL);
INSERT INTO missions (id, exercise, title, startweek, refer, comments, createdate, startdate, duedate, datecloture, updatedate, updateduser, status, typ, version, language, job_status, todo, jobcomment, tofinish, annualbudget_id, fk_parent) VALUES (76, '2010', 'PARFIMAT SA', 50, '9296_2010', 'PARFIMAT SA', '2010-12-17', '2010-12-17', NULL, NULL, NULL, NULL, 'ONGOING', 'RC', 2, 'EN', 'FIELD WORK TO FINALISE', NULL, NULL, NULL, 31, NULL);


--
-- TOC entry 2223 (class 0 OID 17612)
-- Dependencies: 1674
-- Data for Name: missiontype_task; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO missiontype_task (id, missiontypecode, libelle, taskcode, defaultvalue) VALUES (1, 'RA', 'Revision annuelle', 'RA', 1000);
INSERT INTO missiontype_task (id, missiontypecode, libelle, taskcode, defaultvalue) VALUES (2, 'RC', 'Revision contractuelle', 'RC', 1000);
INSERT INTO missiontype_task (id, missiontypecode, libelle, taskcode, defaultvalue) VALUES (3, 'AC', 'Audit consolidation', 'AC', 1000);
INSERT INTO missiontype_task (id, missiontypecode, libelle, taskcode, defaultvalue) VALUES (4, 'CI', 'Controle interne', 'CI', 1000);
INSERT INTO missiontype_task (id, missiontypecode, libelle, taskcode, defaultvalue) VALUES (5, 'MCOMM', 'Mandat  commissaire', 'MCOMM', 1000);
INSERT INTO missiontype_task (id, missiontypecode, libelle, taskcode, defaultvalue) VALUES (6, 'MADM', 'Mandat administrateur', 'MADM', 1000);
INSERT INTO missiontype_task (id, missiontypecode, libelle, taskcode, defaultvalue) VALUES (7, 'MISSPE', 'Mission speciale', 'MISSPE', 1000);
INSERT INTO missiontype_task (id, missiontypecode, libelle, taskcode, defaultvalue) VALUES (8, 'CONSO', 'Consolidation', 'CONSO', 1000);
INSERT INTO missiontype_task (id, missiontypecode, libelle, taskcode, defaultvalue) VALUES (9, 'DOM', 'Domiciliation', 'DOM', 1000);


--
-- TOC entry 2225 (class 0 OID 17628)
-- Dependencies: 1676
-- Data for Name: origins; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO origins (id, name, code) VALUES (1, 'InterFiduciaire', 'IF');
INSERT INTO origins (id, name, code) VALUES (2, 'InterAudit', 'IA');
INSERT INTO origins (id, name, code) VALUES (3, 'Atoz', 'AT');
INSERT INTO origins (id, name, code) VALUES (4, 'Fideos', 'FD');
INSERT INTO origins (id, name, code) VALUES (5, 'Bob Bernard', 'BB');
INSERT INTO origins (id, name, code) VALUES (6, 'Signes', 'SG');
INSERT INTO origins (id, name, code) VALUES (7, 'Inconnue', 'IC');


--
-- TOC entry 2226 (class 0 OID 17638)
-- Dependencies: 1677
-- Data for Name: payments; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO payments (id, code, reference, year, customername, totaldu, totalnc, totalpaid, totalremaining, amount, currency, paymentdate, version, fk_facture) VALUES (241, 'BGLLLULL', 'P_F-10/001_1', '2010', 'ANGLESEA CAPITAL SA', 3001.5, 0, 0, 3001.5, 3001.5, 'EUR', '2010-12-08', 0, 235);


--
-- TOC entry 2227 (class 0 OID 17648)
-- Dependencies: 1678
-- Data for Name: planning_annuel; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO planning_annuel (id, lastupdate, year) VALUES (22, '2010-12-17', 2010);
INSERT INTO planning_annuel (id, lastupdate, year) VALUES (53, '2010-12-17', 2010);


--
-- TOC entry 2228 (class 0 OID 17653)
-- Dependencies: 1679
-- Data for Name: profiles; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO profiles (id, version, name, fromdate, todate, active, ordre) VALUES (1, 0, 'SENIOR_MANAGER', '0001-05-18 10:20:49 BC', '0001-05-18 10:20:49 BC', true, 3);
INSERT INTO profiles (id, version, name, fromdate, todate, active, ordre) VALUES (2, 0, 'DIRECTOR', '0001-05-18 10:20:50 BC', '0001-05-18 10:20:50 BC', true, 2);
INSERT INTO profiles (id, version, name, fromdate, todate, active, ordre) VALUES (3, 0, 'ASSISTANTS', '0001-05-18 10:20:50 BC', '0001-05-18 10:20:50 BC', true, 8);
INSERT INTO profiles (id, version, name, fromdate, todate, active, ordre) VALUES (4, 0, 'PARTNER', '0001-05-18 10:20:50 BC', '0001-05-18 10:20:50 BC', true, 1);
INSERT INTO profiles (id, version, name, fromdate, todate, active, ordre) VALUES (5, 0, 'MGMT_PARTNER', '0001-05-18 10:20:50 BC', '0001-05-18 10:20:50 BC', true, 2);
INSERT INTO profiles (id, version, name, fromdate, todate, active, ordre) VALUES (6, 0, 'SECRETAIRE', '0001-05-18 10:20:50 BC', '0001-05-18 10:20:50 BC', true, 9);
INSERT INTO profiles (id, version, name, fromdate, todate, active, ordre) VALUES (7, 0, 'MANAGER', '0001-05-18 10:20:50 BC', '0001-05-18 10:20:50 BC', true, 4);
INSERT INTO profiles (id, version, name, fromdate, todate, active, ordre) VALUES (8, 0, 'ASSISTANT_MANAGER', '0001-05-18 10:20:50 BC', '0001-05-18 10:20:50 BC', true, 7);
INSERT INTO profiles (id, version, name, fromdate, todate, active, ordre) VALUES (9, 0, 'SENIOR_MANAGER', '0001-05-18 10:20:50 BC', '0001-05-18 10:20:50 BC', true, 5);
INSERT INTO profiles (id, version, name, fromdate, todate, active, ordre) VALUES (10, 0, 'SENIORS', '0001-05-18 10:20:50 BC', '0001-05-18 10:20:50 BC', true, 6);


--
-- TOC entry 2229 (class 0 OID 17658)
-- Dependencies: 1680
-- Data for Name: rights; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO rights (id, name, description, code, type) VALUES (43, 'Consulter factures', 'Cette option active 
les  menus:
<ul>
<li>
<span style=''color: red;''>Factures</span>
</li>
<li>
<span style=''color: red;''>Paymentsr</span>
</li>
<li>
<span style=''color: red;''>Alertes</span>
</li>

</ul>




Sans cette option l''utilisateur ne peut pas consulter les factures,les payments, les alertes', 'CONSULT_INVOICE', 'INVOICE');
INSERT INTO rights (id, name, description, code, type) VALUES (44, 'Consulter document', 'Consulter document', 'CONSULT_DOCUMENT', 'DOCUMENT');
INSERT INTO rights (id, name, description, code, type) VALUES (45, 'Enregister timesheet', 'Cette option active 
les  menus:
<ul>
<li>
<span style=''color: red;''>Ma Timesheet</span>
</li>
<li>
<span style=''color: red;''>Mes Timesheets</span>
</li>
</ul>

Sans cette option l''utilisateur ne peut pas encoder ses timesheets', 'REGISTER_TIMESHEET', 'TIMESHEET');
INSERT INTO rights (id, name, description, code, type) VALUES (1, 'Enregister employee', 'Enregister un employee', 'REGISTER_USER', 'NEW');
INSERT INTO rights (id, name, description, code, type) VALUES (2, 'Modifier employee', 'Permet de modifier un employee', 'MODIFY_USER', 'EMPLOYEE');
INSERT INTO rights (id, name, description, code, type) VALUES (3, 'Consulter employee ', 'Permet de consulter la liste  des  employées', 'CONSULT_USER', 'SEARCH');
INSERT INTO rights (id, name, description, code, type) VALUES (4, 'Enregistrer client', 'Enregistrer un client ', 'REGISTER_CUST', 'NEW');
INSERT INTO rights (id, name, description, code, type) VALUES (5, 'Modifier client', 'Permet de modifier un client', 'MODIFY_CUST', 'CLIENT');
INSERT INTO rights (id, name, description, code, type) VALUES (6, 'Consulter client', 'Permet de consulter la liste  des  clients', 'CONSULT_CUST', 'SEARCH');
INSERT INTO rights (id, name, description, code, type) VALUES (7, 'Enregistrer banque', 'Enregistrer une banque', 'REGISTER_BANK', 'NEW');
INSERT INTO rights (id, name, description, code, type) VALUES (8, 'Modifier  banque', 'Modifier une banque', 'MODIFY_BANK', 'BANK');
INSERT INTO rights (id, name, description, code, type) VALUES (10, 'Enregistrer payment', 'Cette option active 
les  menus:
<ul>
<li>
<span style=''color: red;''>Tableau de bord</span>
</li>
<li>
<span style=''color: red;''>A traiter</span>
</li>
</ul>

Ainsi que les commandes:
<ul>
<li>
<span style=''color: red;''>Validate</span>
</li>
<li>
<span style=''color: red;''>Reject</span>
</li>
</ul>


Sans cette option l''utilisateur ne peut pas gérer les timesheets', 'REGISTER_PAYMENT', 'MISSION');
INSERT INTO rights (id, name, description, code, type) VALUES (11, 'Modifier payments', 'Cette option active 
les  menus:
<ul>
<li>
<span style=''color: red;''>Tableau de bord</span>
</li>
<li>
<span style=''color: red;''>A traiter</span>
</li>
</ul>

Ainsi que les commandes:
<ul>
<li>
<span style=''color: red;''>Validate</span>
</li>
<li>
<span style=''color: red;''>Reject</span>
</li>
</ul>


Sans cette option l''utilisateur ne peut pas gérer les timesheets', 'MODIFY_PAYMENT', 'MISSION');
INSERT INTO rights (id, name, description, code, type) VALUES (13, 'Enregistrer contact', 'Enregistrer contact', 'REGISTER_CONTACT', 'NEW');
INSERT INTO rights (id, name, description, code, type) VALUES (14, 'Modifier contact', 'Modifier contact', 'MODIFY_CONTACT', 'CONTACT');
INSERT INTO rights (id, name, description, code, type) VALUES (15, 'Consulter contact', 'Consulter contact', 'CONSULT_CONTACT', 'SEARCH');
INSERT INTO rights (id, name, description, code, type) VALUES (16, 'Enregistrer declaration', 'Enregistrer declarartion', 'REGISTER_DECLARATION', 'NEW');
INSERT INTO rights (id, name, description, code, type) VALUES (17, 'Modifier declaration', 'Modifier déclaration', 'MODIFY_DECLARATION', 'DECLARATION');
INSERT INTO rights (id, name, description, code, type) VALUES (18, 'Consulter declaration', 'Consulter declaration', 'CONSULT_DECLARATION', 'SEARCH');
INSERT INTO rights (id, name, description, code, type) VALUES (19, 'Enregistrer contrat', 'Enregistrer contrat', 'REGISTER_CONTRACT', 'NEW');
INSERT INTO rights (id, name, description, code, type) VALUES (20, 'Modifier contrat', 'Modifier contrat', 'MODIFY_CONTRACT', 'CONTRAT');
INSERT INTO rights (id, name, description, code, type) VALUES (21, 'Consulter contrat', 'Consulter contrat', 'CONSULT_CONTRACT', 'SEARCH');
INSERT INTO rights (id, name, description, code, type) VALUES (22, 'Consulter budget', 'Cette option commande le menu 
<ul>
<li>
<span style=''color: red;''>Budget général</span>
</li>
</ul>
Sans cette option l''utilisateur ne peut pas accéder au Budget', 'CONSULT_BUDGET', 'BUDGET');
INSERT INTO rights (id, name, description, code, type) VALUES (23, 'Modify budget', 'Cette option commande les fonctionnalités: 
<ul>
<li>
<span style=''color: red;''>Add Budget</span>
</li>
<li><span style=''color: red;''>Delete Budget</span></li>
<li><span style=''color: red;''>Edit Budget</span></li>

<li><span style=''color: red;''>Apply Inflation</span></li>
</ul>

dans l''écran Budget général
.<br/>
Sans cette option l''utilisateur ne peut pas modifier le Budget', 'MODIFY_BUDGET', 'BUDGET');
INSERT INTO rights (id, name, description, code, type) VALUES (24, 'Build budget', 'Cette option commande les fonctionnalités: 
<ul>
<li><span style=''color: red;''>Build Next exercice</span></li>
<li><span style=''color: red;''>Approve exercice</span></li>
<li><span style=''color: red;''>Close exercice</span></li>
</ul>
dans l''écran Budget général
.<br/>
Sans cette option l''utilisateur ne peut pas créer le Budget', 'BUILD_BUDGET', 'BUDGET');
INSERT INTO rights (id, name, description, code, type) VALUES (25, 'Export budget to excel file', 'Cette option commande le menu 
<ul>
<li>
<span style=''color: red;''>Export to excel</span>
</li>
</ul>
dans l''écran Budget général
Sans cette option l''utilisateur ne peut pas générer le Budget dans Excel', 'EXPORT_BUDGET_TO_EXCEL', 'BUDGET');
INSERT INTO rights (id, name, description, code, type) VALUES (26, 'Consult  resultats', 'Cette option commande le menu 
<ul>
<li>
<span style=''color: red;''>Objectifs & Resultats</span>
</li>
</ul>
.<br/>
Sans cette option l''utilisateur ne peut pas accéder à l''écran Objectifs & Resultats', 'CONSULT_RESULTATS', 'BUDGET');
INSERT INTO rights (id, name, description, code, type) VALUES (27, 'Consult rentabilite clients', 'Cette option commande le menu 
<ul>
<li>
<span style=''color: red;''>Rentabilité par client</span>
</li>
</ul>

Sans cette option l''utilisateur ne peut pas accéder à l''écran Rentabilité par client', 'CONSULT_RENTABILITE_CLIENTS', 'BUDGET');
INSERT INTO rights (id, name, description, code, type) VALUES (28, 'Consult financial data', 'Cette option commande le menu 
<ul>
<li>
<span style=''color: red;''>Financial Data</span>
</li>
</ul>

Sans cette option l''utilisateur ne peut pas accéder aux informations financiéres par employée', 'CONSULT_FINANCIAL_DATA', 'BUDGET');
INSERT INTO rights (id, name, description, code, type) VALUES (29, 'Gérer les droits ', 'Cette option permet à l'' utilisateur de gérer les droits d''accès aux différents menus de l''application', 'MODIFY_ACCESS_RIGHTS', 'DROITS');
INSERT INTO rights (id, name, description, code, type) VALUES (30, 'Consulter planning missions', 'Consulter planning missions', 'CONSULT_PLANNING_MISSIONS', 'MISSION');
INSERT INTO rights (id, name, description, code, type) VALUES (31, 'Consulter planning general', 'Cette option active 
le menu
<ul>
<li>
<span style=''color: red;''>Planning</span>
</li>
</ul>

Sans cette option l''utilisateur ne peut pas accéder au planning', 'CONSULT_GENERAL_PLANNING', 'PLANNING');
INSERT INTO rights (id, name, description, code, type) VALUES (32, 'Gérer planning general', 'Cette option commande les fonctionnalités: 
<ul>
<li>
<span style=''color: red;''>Add</span>
</li>
<li><span style=''color: red;''>Clear</span></li>
</ul>
dans l''écran Planning et Details planning
.<br/>
Sans cette option l''utilisateur ne peut pas modifier le planning', 'MODIFY_GENERAL_PLANNING', 'PLANNING');
INSERT INTO rights (id, name, description, code, type) VALUES (33, 'Consulter agenda', 'Cette option commande le menu: 
<ul>
<li>
<span style=''color: red;''>Mon Agenda</span>
</li>
</ul>

Sans cette option l''utilisateur ne peut pas accéder a l''Agenda', 'CONSULT_CALENDAR', 'PLANNING');
INSERT INTO rights (id, name, description, code, type) VALUES (34, 'Consulter l''agenda des collaborateurs', 'Si cette option est activée, alors elle donne la possibilité de consulter l''agenda des autres en mode read-only
dans l''écran Agenda', 'MODIFY_CALENDAR', 'PLANNING');
INSERT INTO rights (id, name, description, code, type) VALUES (35, 'Consulter planweek', 'Cette option commande le menu: 
<ul>
<li>
<span style=''color: red;''>PlanWeek</span>
</li>
</ul>

Sans cette option l''utilisateur ne peut pas accéder au PlanWeek', 'CONSULT_MISSIONS', 'PLANNING');
INSERT INTO rights (id, name, description, code, type) VALUES (36, 'Administrer  mission', 'Cette option permet d''administrer les missions.
Sans cette option, l''utilisateur ne peut pas modifier l''état d''une mission.
La ligne budgétaire ne doit pas être fermée pour que l''option soit activée.', 'MODIFY_MISSIONS', 'PLANNING');
INSERT INTO rights (id, name, description, code, type) VALUES (38, 'Modifier facture', 'Cette option active 
les  menus:
<ul>
<li>
<span style=''color: red;''>Tableau de bord</span>
</li>
<li>
<span style=''color: red;''>A traiter</span>
</li>
</ul>

Ainsi que les commandes:
<ul>
<li>
<span style=''color: red;''>Validate</span>
</li>
<li>
<span style=''color: red;''>Reject</span>
</li>
</ul>


Sans cette option l''utilisateur ne peut pas gérer les timesheets', 'MODIFY_INVOICE', 'MISSION');
INSERT INTO rights (id, name, description, code, type) VALUES (39, 'Enregistrer depense', 'Cette option commande le menu 
<ul>
<li>
<span style=''color: red;''>Budget général</span>
</li>
</ul>', 'REGISTER_EXPENSE', 'MISSION');
INSERT INTO rights (id, name, description, code, type) VALUES (40, 'Modifier depense', 'Cette option commande le menu 
<ul>
<li>
<span style=''color: red;''>Budget général</span>
</li>
</ul>', 'MODIFY_EXPENSE', 'MISSION');
INSERT INTO rights (id, name, description, code, type) VALUES (41, 'Gérér  timesheet', 'Cette option active 
les  menus:
<ul>
<li>
<span style=''color: red;''>Tableau de bord</span>
</li>
<li>
<span style=''color: red;''>A traiter</span>
</li>
</ul>

Ainsi que les commandes:
<ul>
<li>
<span style=''color: red;''>Validate</span>
</li>
<li>
<span style=''color: red;''>Reject</span>
</li>
</ul>


Sans cette option l''utilisateur ne peut pas gérer les timesheets', 'VALIDATE_TIMESHEET', 'TIMESHEET');
INSERT INTO rights (id, name, description, code, type) VALUES (42, 'Reject timesheet', 'Reject timeshet', 'REJECT_TIMESHEET', 'MISSION');
INSERT INTO rights (id, name, description, code, type) VALUES (9, 'Consulter banque', 'Consulter banque', 'CONSULT_BANK', 'SEARCH');
INSERT INTO rights (id, name, description, code, type) VALUES (12, 'Consulter payment', 'Cette option active 
les  menus:
<ul>
<li>
<span style=''color: red;''>Tableau de bord</span>
</li>
<li>
<span style=''color: red;''>A traiter</span>
</li>
</ul>

Ainsi que les commandes:
<ul>
<li>
<span style=''color: red;''>Validate</span>
</li>
<li>
<span style=''color: red;''>Reject</span>
</li>
</ul>


Sans cette option l''utilisateur ne peut pas gérer les timesheets', 'CONSULT_PAYMENT', 'MISSION');
INSERT INTO rights (id, name, description, code, type) VALUES (37, 'gérer facture', 'Cette option active 
les  menus:
<ul>
<li>
<span style=''color: red;''>Encoder une facture</span>
</li>
<li>
<span style=''color: red;''>Encoder un rappel</span>
</li>

<li>
<span style=''color: red;''>Encoder une note de creditl</span>
</li>

</ul>

Sans cette option l''utilisateur ne peut pas générer de factures, de note de crédit ou de rappel', 'CREATE_INVOICE', 'INVOICE');


--
-- TOC entry 2230 (class 0 OID 17666)
-- Dependencies: 1681
-- Data for Name: tasks; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62848, 'Formation', 'Formation', '9859', false, 'FORMATION', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62849, 'Temps partiel', 'Temps partiel', '9981', true, 'TEMPSPART', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62850, 'Revue', 'Revue', '9982', true, 'REVUE', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62846, 'Conge parental', 'Conge parental', '9931', false, 'CONGEPARE', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62847, 'Conge sans solde', 'Conge sans solde', '9932', true, 'CONGESS', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62800, 'Conge de maternite', 'Conge de maternite', '9920', false, 'CONGMAT', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62807, 'Preparation des confirmations', 'banques , avocats, cadastre', '4002', true, 'PDC', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62808, 'Preparation lancement du dossier (DA,LS)', 'Preparation lancement du dossier (DA,LS)', '4003', true, 'PLDD', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62809, 'Dossier administratif', 'Dossier administratif', '4004', true, 'DA', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62810, 'Compte annuels', 'controle: report,sommes pointage B/G', '4005', true, 'CA', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62811, 'Prise de connaissance du dossier - repartition du travail', 'Prise de connaissance du dossier - repartition du travail', '4006', true, 'PCDRT', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62812, 'Suivi du dossier chez le client/Supervision', 'Suivi du dossier chez le client/Supervision', '4007', true, 'SDCS', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62813, 'Frais d''établissement', 'Frais d''établissement', '4008', true, 'FRE', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62814, 'Immobilisations incorporelles', 'Immobilisations incorporelles', '4009', true, 'IMINC', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62815, 'Immobilisations corporelles', 'Immobilisations corporelles', '4010', true, 'IMCP', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62816, 'Immobilisations financieres', 'Immobilisations financieres', '4011', true, 'IMFI', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62817, 'Stock-Inventaire physique', 'Stock-Inventaire physique', '4012', true, 'SIPH', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62818, 'Stock-Audit de la section', 'Stock-Audit de la section', '4013', true, 'SAS', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62819, 'Client-Preparation et suivi de la circularisation', 'Client-Preparation et suivi de la circularisation', '4014', true, 'CPSC', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62820, 'Client-Audit de la section', 'Client-Audit de la section', '4015', true, 'CAS', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62821, 'Creances sur des entreprises liees', 'Creances sur des entreprises liees', '4016', true, 'CEL', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62822, 'Autres creances', 'Autres creances', '4017', true, 'ATC', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62823, 'Valeurs mobilieres', 'Valeurs mobilieres', '4018', true, 'VMB', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62824, 'Avoirs en banques et encaisses', 'Avoirs en banques et encaisses', '4019', true, 'ABEC', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62825, 'Compte de regularisation actif', 'Compte de regularisation actif', '4020', true, 'CPRA', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62826, 'Capitaux propres', 'Capitaux propres', '4021', true, 'CPP', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62827, 'Provisions pour risques et charges', 'Provisions pour risques et charges', '4022', true, 'PRRC', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62828, 'Dettes envers ets de credit', 'Dettes envers ets de credit', '4023', true, 'DECR', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62829, 'Fournisseurs-preparation et suivi de la circularisation', 'Fournisseurs-preparation et suivi de la circularisation', '4024', true, 'FNPSC', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62830, 'Fournisseurs-audit de la section', 'Fournisseurs-audit de la section', '4025', true, 'FNAS', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62831, 'Dettes sur des entreprises liees', 'Dettes sur des entreprises liees', '4026', true, 'DEL', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62832, 'Autres dettes', 'Autres dettes', '4027', true, 'ADTS', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62833, 'Compte de regularisation passif', 'Compte de regularisation passif', '4028', true, 'CPRP', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62834, 'Compte de pertes et profits', 'Compte de pertes et profits', '4029', true, 'CPPP', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62835, 'Compte de pertes et profits-Resultats brut', 'Compte de pertes et profits-Resultats brut', '4030', true, 'CPPRB', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62836, 'Compte de pertes et profits-Frais de personnel', 'Compte de pertes et profits-Frais de personnel', '4031', true, 'CPPFP', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62837, 'Compte de pertes et profits-Autres charges déxploitation', 'Compte de pertes et profits-Autres charges déxploitation', '4032', true, 'CPP-ACD', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62838, 'Compte de pertes et profits-Produits provenant de participations', 'Compte de pertes et profits-Produits provenant de participations', '4033', true, 'CPP-PPP', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62839, 'Compte de pertes et profits-Resultat financier', 'Compte de pertes et profits-Resultat financier', '4034', true, 'CPPRF', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62840, 'Compte de pertes et profits-Resultat exceptionel', 'Compte de pertes et profits-Resultat exceptionel', '4035', true, 'CPPRE', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62841, 'Compte de pertes et profits-Impots', 'Compte de pertes et profits-Impots', '4036', true, 'CPPRI', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62842, 'Engagements hors bilan', 'Engagements hors bilan', '4037', true, 'EGHB', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62843, 'Review', 'Review', '4038', true, 'RVW', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62844, 'Copie de controle', 'Copie de controle', '4039', true, 'CDCT', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62845, 'Travail specifique ', 'Travail specifique', '4040', true, 'TRSP', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62744, 'Mandat - commissaire', 'Mandat - commissaire', '5046', true, 'MCOMM', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62745, 'Mandat - actionnaire', 'Mandat - actionnaire', '5047', true, 'MACT', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62746, 'Secrétariat juridique', 'S‚cr‚tariat juridique', '5048', true, 'SECJUR', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62747, 'Mission spéciale', 'Mission sp‚ciale', '5050', true, 'MISSPE', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62748, 'Consolidation', 'Consolidation', '5051', true, 'CONSO', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62749, 'Expertise', 'Expertise', '5052', true, 'EXP', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62750, 'Déplacement', 'D‚placement', '5080', true, 'DEP', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62751, 'Domiciliation', 'Domiciliation', '5090', true, 'DOM', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62752, 'Dactylographie', 'Dactylographie', '6000', true, 'DACT', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62753, 'Classement', 'Classement', '6010', true, 'CALS', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62754, 'Photocopies', 'Photocopies', '6020', true, 'PHOTO', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62755, 'D‚placement', 'D‚placement', '6030', true, 'DEPLAC', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62756, 'Divers', 'Divers', '6090', true, 'DIV', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62757, 'Manual typing', 'Manual typing', '6100', true, 'MANTYP', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62758, 'Telefax', 'Telefax', '6110', true, 'FAX', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62759, 'Expenses', 'Expenses', '6120', true, 'EXPE', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62760, 'Frais … refacturer', 'Frais … refacturer', '6130', true, 'FFACT', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62761, 'Prestations par tiers', 'Prestations par tiers', '9110', false, 'PRETIER', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62762, 'Frais de d‚placement', 'Frais de d‚placement', '9120', false, 'FDEP', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62763, 'D‚bours', 'D‚bours', '9130', false, 'DEB', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62764, 'OECL', 'OECL', '9710', false, 'OECL', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62765, 'IRE', 'IRE', '9720', false, 'IRE', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62766, 'ALFI', 'ALFI', '9730', false, 'ALFI', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62767, 'KPMG', 'KPMG', '9750', false, 'KPMG', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62768, 'KPMG Network', 'KPMG Network', '9751', false, 'KPMG-N', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62769, 'KPMG Updating livres', 'KPMG Updating livres', '9752', false, 'KPMG-UL', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62770, 'KPMG-Fiscalit‚', 'KPMG-Fiscalit‚', '9755', false, 'KPMG-F', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62771, 'Organisations professionnelles locales', 'Organisations professionnelles locales', '9760', false, 'OPL', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62772, 'Correspondants divers', 'Correspondants divers', '9790', false, 'CDIVER', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62773, 'Secretariat', 'Secretariat', '9800', false, 'SEC', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62774, 'Facturation clients', 'Facturation clients', '9810', false, 'FCLIENT', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62775, 'D‚comptes fournisseurs', 'D‚comptes fournisseurs', '9812', false, 'DECFOUR', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62776, 'Comptabilite', 'Comptabilite', '9815', false, 'COMPTA', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62777, 'Datev', 'Datev', '9818', false, 'DATEV', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62778, 'Gestion du personnel', 'Gestion du personnel', '9820', false, 'GESPERS', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62779, 'Recrutement', 'Recrutement', '9825', false, 'RECRUT', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62780, 'Organisation administrative', 'Organisation administrative', '9830', false, 'ORGADM', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62781, 'Newsletters et autres publications', 'Newsletters et autres publications', '9831', false, 'NEWS', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62782, 'Informatique-conception de formulaires', 'Informatique-conception de formulaires', '9832', false, 'INF-CONS', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62783, 'Administration courante', 'Administration courante', '9835', false, 'ADMCOU', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62784, 'Prestations clients non factur‚es', 'Prestations clients non factur‚es', '9840', false, 'PRESCLI', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62785, 'Litt‚rature', 'Litt‚rature', '9850', false, 'LITT', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62786, 'Etudes fiscales', 'Etudes fiscales', '9851', false, 'ETUDFIS', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62787, 'Conception formulaires', 'Conception formulaires', '9852', false, 'CONFOR', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62788, 'S‚minaires KPMG', 'S‚minaires KPMG', '9853', false, 'SEMKPMG', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62789, 'Formation externe (IRE, Universit‚ Luxembourg)', 'Formation externe (IRE, Universit‚ Luxembourg)', '9854', false, 'FORMEXT', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62790, 'Formation interne (bureau IA)', 'Formation interne (bureau IA)', '9855', false, 'FORMINT', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62791, 'Formation nouveaux collŠgues', 'Formation nouveaux collŠgues', '9856', false, 'FORMCOLL', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62792, 'Prestation s‚minaire', 'Prestation s‚minaire', '9857', false, 'PRESTSEM', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62793, 'Gestion des formations', 'Gestion des formations', '9858', false, 'GESTFORM', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62794, 'Public Relations', 'Public Relations', '9860', false, 'PUBREL', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62795, 'Publication banques allemandes', 'Publication banques allemandes', '9870', false, 'PUBBQ', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62796, 'Projet Leasing', 'Projet Leasing', '9875', false, 'PROJLEAS', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62798, 'Divers', 'Divers', '9890', false, 'DIVERS', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62799, 'Maladie', 'Maladie', '9910', false, 'MALAD', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62801, 'Conge legal', 'Conge legal', '9930', false, 'CONGLEG', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62802, 'Jours feries', 'Jours feries', '9940', false, 'FERIE', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62803, 'Absences r‚mun‚r‚es', 'Absences r‚mun‚r‚es', '9970', false, 'ABSREM', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62804, 'Absence non r‚mun‚r‚e', 'Absence non r‚mun‚r‚e', '9980', false, 'ABSNREM', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62806, 'Planning-budget', 'Planning-budget', '4001', true, 'PLB', false, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62700, 'Report a nouveau', 'Report a nouveau', '1000', true, 'RAA', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62701, 'D‚claration', 'D‚claration', '2000', true, 'D', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62702, 'Conseil fiscal', 'Conseil fiscal', '2010', true, 'CF', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62703, 'Conseil droit fiscal et travail', 'Conseil droit fiscal et travail', '2020', true, 'CDFET', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62704, 'Autres conseils', 'Autres conseils', '2030', true, 'AC1', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62705, 'Contr“le bulletins', 'Contr“le bulletins', '2050', true, 'CB', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62706, 'Recours fiscal', 'Recours fiscal', '2060', true, 'RF', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62707, 'Conseil fiscal imp“ts directs', 'Conseil fiscal imp“ts directs', '2100', true, 'CFID', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62708, 'Conseil fiscal TVA', 'Conseil fiscal TVA', '2101', true, 'CFTVA', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62709, 'Conseil fiscal autres imp“ts indirects', 'Conseil fiscal autres imp“ts indirects', '2102', true, 'CFAII', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62710, 'Conseil fiscal autres', 'Conseil fiscal autres', '2103', true, 'CFAIII', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62711, 'Assistance r‚vision', 'Assistance r‚vision', '2110', true, 'AR', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62714, 'Comptabilite', 'Comptabilite', '3000', true, 'CMPT', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62715, 'D‚compte salaires', 'D‚compte salaires', '3200', true, 'DS', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62716, 'Conseil', 'Conseil', '3210', true, 'C', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62718, 'Mise en route comptabilit‚', 'Mise en route comptabilit‚', '3310', true, 'MERC', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62719, 'Assistance comptabilit‚', 'Assistance comptabilit‚', '3320', true, 'AC2', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62720, 'Redressement comptabilit‚', 'Redressement comptabilit‚', '3330', true, 'RD', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62721, 'Etablissement bilan', 'Etablissement bilan', '3500', true, 'EB', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62722, 'Annexe et rapport de gestion', 'Annexe et rapport de gestion', '3502', true, 'AERDG', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62723, 'Discussion finale', 'Discussion finale', '3505', true, 'DF', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62724, 'Bilan int‚rimaire', 'Bilan int‚rimaire', '3510', true, 'BI', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62725, 'D‚claration IR-IRC-IC-IF', 'D‚claration IR-IRC-IC-IF', '3520', true, 'DIIII', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62726, 'D‚claration TVA', 'D‚claration TVA', '3530', true, 'DTVA', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62727, 'Taxe d''abonnement', 'Taxe d''abonnement', '3540', true, 'TA', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62728, 'Statec', 'Statec', '3590', true, 'STATEC', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62729, 'R‚vision annuelle', 'R‚vision annuelle', '4000', true, 'RA', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62730, 'R‚vision contractuelle', 'R‚vision contractuelle', '4050', true, 'RC', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62731, 'Audit consolidation', 'Audit consolidation', '4051', true, 'AC', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62732, 'Contr“le interne', 'Contr“le interne', '4100', true, 'CI', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62733, 'Revue dossiers d''autres r‚viseurs', 'Revue dossiers d''autres r‚viseurs', '4150', true, 'RDDAR', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62734, 'Conseil juridique', 'Conseil juridique', '5010', true, 'CJ', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62735, 'Conseil en gestion d''entreprise', 'Conseil en gestion d''entreprise', '5012', true, 'CEGDE', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62736, 'Conseil financier', 'Conseil financier', '5014', true, 'CFI', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62737, 'Conseil informatique', 'Conseil informatique', '5015', true, 'CINF', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62738, 'Recrutement', 'Recrutement', '5020', true, 'REC', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62739, 'S‚minaire', 'S‚minaire', '5030', true, 'SEM', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62740, 'Autorisation de commerce', 'Autorisation de commerce', '5040', true, 'AUTCOM', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62741, 'Constitution de soci‚t‚', 'Constitution de soci‚t‚', '5042', true, 'CONSOC', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62742, 'Liquidation de soci‚t‚', 'Liquidation de soci‚t‚', '5043', true, 'LIQSOC', true, 0);
INSERT INTO tasks (id, name, description, codeprestation, chargeable, code, optional, version) VALUES (62743, 'Mandat - administrateur', 'Mandat - administrateur', '5045', true, 'MADM', true, 0);


--
-- TOC entry 2232 (class 0 OID 17682)
-- Dependencies: 1683
-- Data for Name: timesheet_cells; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO timesheet_cells (id, version, daynumber, value, fk_row) VALUES (228, 0, 5, 4.25, 227);
INSERT INTO timesheet_cells (id, version, daynumber, value, fk_row) VALUES (230, 0, 3, 7, 229);
INSERT INTO timesheet_cells (id, version, daynumber, value, fk_row) VALUES (232, 0, 5, 1.5, 231);
INSERT INTO timesheet_cells (id, version, daynumber, value, fk_row) VALUES (368, 0, 3, 8, 367);
INSERT INTO timesheet_cells (id, version, daynumber, value, fk_row) VALUES (370, 0, 1, 8, 369);
INSERT INTO timesheet_cells (id, version, daynumber, value, fk_row) VALUES (375, 0, 5, 4, 374);
INSERT INTO timesheet_cells (id, version, daynumber, value, fk_row) VALUES (376, 0, 2, 8, 374);
INSERT INTO timesheet_cells (id, version, daynumber, value, fk_row) VALUES (380, 0, 5, 4, 379);
INSERT INTO timesheet_cells (id, version, daynumber, value, fk_row) VALUES (381, 0, 4, 8, 379);


--
-- TOC entry 2233 (class 0 OID 17687)
-- Dependencies: 1684
-- Data for Name: timesheet_rows; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO timesheet_rows (id, version, label, codeprestation, custnumber, asscode, mancode, year, taskidentifier, fk_activity, fk_timesheet) VALUES (227, 6, 'Caryos SA', '4000', '9291', 'EK', 'LM', '2010', NULL, 91, 1);
INSERT INTO timesheet_rows (id, version, label, codeprestation, custnumber, asscode, mancode, year, taskidentifier, fk_activity, fk_timesheet) VALUES (229, 6, 'EUROPEAN PARLIAMENT-canteen', '4000', '6464', 'VD', 'LM', '2010', NULL, 95, 1);
INSERT INTO timesheet_rows (id, version, label, codeprestation, custnumber, asscode, mancode, year, taskidentifier, fk_activity, fk_timesheet) VALUES (231, 6, 'Cologic', '5050', '9292', 'MB', 'SG', '2010', NULL, 85, 1);
INSERT INTO timesheet_rows (id, version, label, codeprestation, custnumber, asscode, mancode, year, taskidentifier, fk_activity, fk_timesheet) VALUES (379, 8, 'Laurad Holding', '4051', '9293', 'MB', 'OJ', '2010', NULL, 89, 277);
INSERT INTO timesheet_rows (id, version, label, codeprestation, custnumber, asscode, mancode, year, taskidentifier, fk_activity, fk_timesheet) VALUES (374, 8, 'Revue', '9982', '99900', NULL, NULL, '2010', '62850', NULL, 277);
INSERT INTO timesheet_rows (id, version, label, codeprestation, custnumber, asscode, mancode, year, taskidentifier, fk_activity, fk_timesheet) VALUES (369, 8, 'DIMPEX SA', '3502', '82633', 'EK', 'LM', '2010', NULL, 101, 277);
INSERT INTO timesheet_rows (id, version, label, codeprestation, custnumber, asscode, mancode, year, taskidentifier, fk_activity, fk_timesheet) VALUES (367, 8, 'ANGLESEA CAPITAL SA', '5051', '6565', 'EK', 'LM', '2010', NULL, 65, 277);


--
-- TOC entry 2231 (class 0 OID 17674)
-- Dependencies: 1682
-- Data for Name: timesheets; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (2, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 65, 60, 15, 'DRAFT', 50, 11709, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (3, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 170, 150, 100, 'DRAFT', 50, 7, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (4, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 150, 120, 100, 'DRAFT', 50, 541, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (5, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 70, 50, 15, 'DRAFT', 50, 11710, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (6, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 150, 150, 100, 'DRAFT', 50, 1, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (7, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 125, 120, 80, 'DRAFT', 50, 4, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (8, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 60, 50, 25, 'DRAFT', 50, 548, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (9, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 60, 50, 25, 'DRAFT', 50, 542, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (10, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 120, 140, 56, 'DRAFT', 50, 6, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (11, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 150, 120, 100, 'DRAFT', 50, 2, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (12, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 0, 0, 0, 'DRAFT', 50, 544, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (13, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 170, 150, 100, 'DRAFT', 50, 5, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (14, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 140, 130, 90, 'DRAFT', 50, 543, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (15, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 130, 120, 50, 'DRAFT', 50, 181, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (16, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 0, 0, 0, 'DRAFT', 50, 241, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (17, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 170, 150, 100, 'DRAFT', 50, 545, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (18, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 0, 0, 0, 'DRAFT', 50, 547, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (19, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 80, 70, 15, 'DRAFT', 50, 11712, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (20, 0, '2010', '2010-12-17', NULL, NULL, NULL, NULL, NULL, '2010-12-13', '2010-12-18', 0, 0, 0, 'DRAFT', 50, 546, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (1, 10, '2010', '2010-12-17', '2010-12-17', NULL, '2010-12-17', '2010-12-17', '2010-12-17', '2010-12-13', '2010-12-18', 120, 100, 90, 'VALIDATED', 50, 3, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (292, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 65, 60, 15, 'DRAFT', 51, 11709, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (293, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 170, 150, 100, 'DRAFT', 51, 7, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (294, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 150, 120, 100, 'DRAFT', 51, 541, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (295, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 70, 50, 15, 'DRAFT', 51, 11710, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (296, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 150, 150, 100, 'DRAFT', 51, 1, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (297, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 125, 120, 80, 'DRAFT', 51, 4, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (298, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 60, 50, 25, 'DRAFT', 51, 548, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (299, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 60, 50, 25, 'DRAFT', 51, 542, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (300, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 120, 140, 56, 'DRAFT', 51, 6, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (301, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 150, 120, 100, 'DRAFT', 51, 2, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (302, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 0, 0, 0, 'DRAFT', 51, 544, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (303, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 170, 150, 100, 'DRAFT', 51, 5, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (304, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 140, 130, 90, 'DRAFT', 51, 543, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (305, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 130, 120, 50, 'DRAFT', 51, 181, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (306, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 0, 0, 0, 'DRAFT', 51, 241, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (307, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 170, 150, 100, 'DRAFT', 51, 545, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (308, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 0, 0, 0, 'DRAFT', 51, 547, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (309, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 80, 70, 15, 'DRAFT', 51, 11712, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (310, 0, '2010', '2010-12-25', NULL, NULL, NULL, NULL, NULL, '2010-12-20', '2010-12-25', 0, 0, 0, 'DRAFT', 51, 546, NULL);
INSERT INTO timesheets (id, version, exercise, createdate, updatedate, accepteddate, rejecteddate, submitdate, validationdate, startdateofweek, enddateofweek, prixvente, prixrevient, couthoraire, status, weeknumber, userid, fk_employee) VALUES (277, 54, '2010', '2010-12-20', '2010-12-25', NULL, '2010-12-25', '2010-12-25', '2010-12-25', '2010-12-20', '2010-12-25', 120, 100, 90, 'VALIDATED', 51, 3, NULL);


--
-- TOC entry 2234 (class 0 OID 17695)
-- Dependencies: 1685
-- Data for Name: trainings; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--



--
-- TOC entry 2235 (class 0 OID 17703)
-- Dependencies: 1686
-- Data for Name: user_actions; Type: TABLE DATA; Schema: interaudit; Owner: postgres
--

INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (236, 'CREATION FACTURE', 'com.interaudit.domain.model.Invoice', 235, '2010-12-17 15:49:25.18', 3);
INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (237, 'APPROBATION FACTURE', 'com.interaudit.domain.model.Invoice', 235, '2010-12-17 15:49:37.4', 3);
INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (240, 'ENVOI FACTURE', 'com.interaudit.domain.model.Invoice', 235, '2010-12-17 15:49:42.232', 3);
INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (242, 'ENREGISTREMENT PAYMENT P_F-10/001_1', 'com.interaudit.domain.model.Payment', 241, '2010-12-17 16:22:51.503', 3);
INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (244, 'CREATION FACTURE', 'com.interaudit.domain.model.Invoice', 243, '2010-12-18 06:20:07.73', 3);
INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (247, 'APPROBATION FACTURE', 'com.interaudit.domain.model.Invoice', 243, '2010-12-18 06:29:55.251', 3);
INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (250, 'ENVOI FACTURE', 'com.interaudit.domain.model.Invoice', 243, '2010-12-18 06:29:58.949', 3);
INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (252, 'CREATION RAPPEL', 'com.interaudit.domain.model.RemindInvoice', 251, '2010-12-18 06:30:26.065', 3);
INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (253, 'ENVOI RAPPEL', 'com.interaudit.domain.model.RemindInvoice', 251, '2010-12-18 06:30:36.435', 3);
INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (266, 'CREATION FACTURE', 'com.interaudit.domain.model.Invoice', 265, '2010-12-19 10:15:55.685', 3);
INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (269, 'APPROBATION FACTURE', 'com.interaudit.domain.model.Invoice', 265, '2010-12-19 13:30:29.27', 3);
INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (272, 'ENVOI FACTURE', 'com.interaudit.domain.model.Invoice', 265, '2010-12-19 13:32:44.14', 3);
INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (411, 'CREATION FACTURE', 'com.interaudit.domain.model.Invoice', 410, '2010-12-26 10:39:37.105', 3);
INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (413, 'CREATION RAPPEL', 'com.interaudit.domain.model.RemindInvoice', 412, '2010-12-26 17:04:13.266', 3);
INSERT INTO user_actions (id, action, entityclassname, entityid, "time", fk_employee) VALUES (414, 'ENVOI RAPPEL', 'com.interaudit.domain.model.RemindInvoice', 412, '2010-12-26 17:31:05.867', 3);


SET search_path = pgagent, pg_catalog;

--
-- TOC entry 2195 (class 0 OID 17315)
-- Dependencies: 1644
-- Data for Name: pga_exception; Type: TABLE DATA; Schema: pgagent; Owner: postgres
--



--
-- TOC entry 2192 (class 0 OID 17233)
-- Dependencies: 1638
-- Data for Name: pga_job; Type: TABLE DATA; Schema: pgagent; Owner: postgres
--



--
-- TOC entry 2190 (class 0 OID 17210)
-- Dependencies: 1634
-- Data for Name: pga_jobagent; Type: TABLE DATA; Schema: pgagent; Owner: postgres
--

INSERT INTO pga_jobagent (jagpid, jaglogintime, jagstation) VALUES (4412, '2010-09-18 10:07:04.039+02', 'MasterPc.home');


--
-- TOC entry 2191 (class 0 OID 17221)
-- Dependencies: 1636
-- Data for Name: pga_jobclass; Type: TABLE DATA; Schema: pgagent; Owner: postgres
--

INSERT INTO pga_jobclass (jclid, jclname) VALUES (1, 'Routine Maintenance');
INSERT INTO pga_jobclass (jclid, jclname) VALUES (2, 'Data Import');
INSERT INTO pga_jobclass (jclid, jclname) VALUES (3, 'Data Export');
INSERT INTO pga_jobclass (jclid, jclname) VALUES (4, 'Data Summarisation');
INSERT INTO pga_jobclass (jclid, jclname) VALUES (5, 'Miscellaneous');


--
-- TOC entry 2196 (class 0 OID 17330)
-- Dependencies: 1646
-- Data for Name: pga_joblog; Type: TABLE DATA; Schema: pgagent; Owner: postgres
--



--
-- TOC entry 2193 (class 0 OID 17259)
-- Dependencies: 1640
-- Data for Name: pga_jobstep; Type: TABLE DATA; Schema: pgagent; Owner: postgres
--



--
-- TOC entry 2197 (class 0 OID 17347)
-- Dependencies: 1648
-- Data for Name: pga_jobsteplog; Type: TABLE DATA; Schema: pgagent; Owner: postgres
--



--
-- TOC entry 2194 (class 0 OID 17285)
-- Dependencies: 1642
-- Data for Name: pga_schedule; Type: TABLE DATA; Schema: pgagent; Owner: postgres
--



SET search_path = interaudit, pg_catalog;

--
-- TOC entry 2033 (class 2606 OID 17433)
-- Dependencies: 1652 1652
-- Name: access_rights_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY access_rights
    ADD CONSTRAINT access_rights_pkey PRIMARY KEY (id);


--
-- TOC entry 2035 (class 2606 OID 17441)
-- Dependencies: 1653 1653
-- Name: activities_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY activities
    ADD CONSTRAINT activities_pkey PRIMARY KEY (id);


--
-- TOC entry 2037 (class 2606 OID 17455)
-- Dependencies: 1654 1654
-- Name: banks_account_key; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY banks
    ADD CONSTRAINT banks_account_key UNIQUE (account);


--
-- TOC entry 2039 (class 2606 OID 17453)
-- Dependencies: 1654 1654
-- Name: banks_code_key; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY banks
    ADD CONSTRAINT banks_code_key UNIQUE (code);


--
-- TOC entry 2041 (class 2606 OID 17451)
-- Dependencies: 1654 1654
-- Name: banks_name_key; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY banks
    ADD CONSTRAINT banks_name_key UNIQUE (name);


--
-- TOC entry 2043 (class 2606 OID 17449)
-- Dependencies: 1654 1654
-- Name: banks_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY banks
    ADD CONSTRAINT banks_pkey PRIMARY KEY (id);


--
-- TOC entry 2045 (class 2606 OID 17463)
-- Dependencies: 1655 1655
-- Name: budgets_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY budgets
    ADD CONSTRAINT budgets_pkey PRIMARY KEY (id);


--
-- TOC entry 2047 (class 2606 OID 17471)
-- Dependencies: 1656 1656
-- Name: comments_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (id);


--
-- TOC entry 2049 (class 2606 OID 17479)
-- Dependencies: 1657 1657
-- Name: contacts_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY contacts
    ADD CONSTRAINT contacts_pkey PRIMARY KEY (id);


--
-- TOC entry 2051 (class 2606 OID 17487)
-- Dependencies: 1658 1658
-- Name: contracts_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY contracts
    ADD CONSTRAINT contracts_pkey PRIMARY KEY (id);


--
-- TOC entry 2053 (class 2606 OID 17499)
-- Dependencies: 1659 1659
-- Name: customers_code_key; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT customers_code_key UNIQUE (code);


--
-- TOC entry 2055 (class 2606 OID 17497)
-- Dependencies: 1659 1659
-- Name: customers_compy_name_key; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT customers_compy_name_key UNIQUE (compy_name);


--
-- TOC entry 2057 (class 2606 OID 17495)
-- Dependencies: 1659 1659
-- Name: customers_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);


--
-- TOC entry 2059 (class 2606 OID 17507)
-- Dependencies: 1660 1660
-- Name: declarations_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY declarations
    ADD CONSTRAINT declarations_pkey PRIMARY KEY (id);


--
-- TOC entry 2031 (class 2606 OID 17425)
-- Dependencies: 1650 1650 1650
-- Name: docs_missions_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY docs_missions
    ADD CONSTRAINT docs_missions_pkey PRIMARY KEY (id_mission, id_document);


--
-- TOC entry 2061 (class 2606 OID 17515)
-- Dependencies: 1661 1661
-- Name: documents_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documents
    ADD CONSTRAINT documents_pkey PRIMARY KEY (id);


--
-- TOC entry 2063 (class 2606 OID 17523)
-- Dependencies: 1662 1662
-- Name: email_data_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY email_data
    ADD CONSTRAINT email_data_pkey PRIMARY KEY (id);


--
-- TOC entry 2065 (class 2606 OID 17533)
-- Dependencies: 1663 1663
-- Name: employees_login_key; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY employees
    ADD CONSTRAINT employees_login_key UNIQUE (login);


--
-- TOC entry 2067 (class 2606 OID 17531)
-- Dependencies: 1663 1663
-- Name: employees_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (id);


--
-- TOC entry 2071 (class 2606 OID 17546)
-- Dependencies: 1665 1665
-- Name: event_planning_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY event_planning
    ADD CONSTRAINT event_planning_pkey PRIMARY KEY (id);


--
-- TOC entry 2069 (class 2606 OID 17541)
-- Dependencies: 1664 1664
-- Name: events_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY events
    ADD CONSTRAINT events_pkey PRIMARY KEY (id);


--
-- TOC entry 2073 (class 2606 OID 17551)
-- Dependencies: 1666 1666
-- Name: exercises_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY exercises
    ADD CONSTRAINT exercises_pkey PRIMARY KEY (id);


--
-- TOC entry 2075 (class 2606 OID 17553)
-- Dependencies: 1666 1666
-- Name: exercises_year_key; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY exercises
    ADD CONSTRAINT exercises_year_key UNIQUE (year);


--
-- TOC entry 2081 (class 2606 OID 17571)
-- Dependencies: 1668 1668
-- Name: invoice_deduction_fees_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY invoice_deduction_fees
    ADD CONSTRAINT invoice_deduction_fees_pkey PRIMARY KEY (id);


--
-- TOC entry 2083 (class 2606 OID 17579)
-- Dependencies: 1669 1669
-- Name: invoice_fees_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY invoice_fees
    ADD CONSTRAINT invoice_fees_pkey PRIMARY KEY (id);


--
-- TOC entry 2085 (class 2606 OID 17587)
-- Dependencies: 1670 1670
-- Name: invoice_reminds_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY invoice_reminds
    ADD CONSTRAINT invoice_reminds_pkey PRIMARY KEY (id);


--
-- TOC entry 2077 (class 2606 OID 17561)
-- Dependencies: 1667 1667
-- Name: invoices_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT invoices_pkey PRIMARY KEY (id);


--
-- TOC entry 2079 (class 2606 OID 17563)
-- Dependencies: 1667 1667
-- Name: invoices_reference_key; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT invoices_reference_key UNIQUE (reference);


--
-- TOC entry 2087 (class 2606 OID 17595)
-- Dependencies: 1671 1671
-- Name: item_event_planning_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY item_event_planning
    ADD CONSTRAINT item_event_planning_pkey PRIMARY KEY (id);


--
-- TOC entry 2089 (class 2606 OID 17603)
-- Dependencies: 1672 1672
-- Name: message_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY message
    ADD CONSTRAINT message_pkey PRIMARY KEY (id);


--
-- TOC entry 2095 (class 2606 OID 17627)
-- Dependencies: 1675 1675
-- Name: mission_costs_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mission_costs
    ADD CONSTRAINT mission_costs_pkey PRIMARY KEY (id);


--
-- TOC entry 2091 (class 2606 OID 17611)
-- Dependencies: 1673 1673
-- Name: missions_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY missions
    ADD CONSTRAINT missions_pkey PRIMARY KEY (id);


--
-- TOC entry 2093 (class 2606 OID 17619)
-- Dependencies: 1674 1674
-- Name: missiontype_task_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY missiontype_task
    ADD CONSTRAINT missiontype_task_pkey PRIMARY KEY (id);


--
-- TOC entry 2097 (class 2606 OID 17637)
-- Dependencies: 1676 1676
-- Name: origins_code_key; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY origins
    ADD CONSTRAINT origins_code_key UNIQUE (code);


--
-- TOC entry 2099 (class 2606 OID 17635)
-- Dependencies: 1676 1676
-- Name: origins_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY origins
    ADD CONSTRAINT origins_pkey PRIMARY KEY (id);


--
-- TOC entry 2101 (class 2606 OID 17645)
-- Dependencies: 1677 1677
-- Name: payments_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payments_pkey PRIMARY KEY (id);


--
-- TOC entry 2103 (class 2606 OID 17647)
-- Dependencies: 1677 1677
-- Name: payments_reference_key; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payments_reference_key UNIQUE (reference);


--
-- TOC entry 2105 (class 2606 OID 17652)
-- Dependencies: 1678 1678
-- Name: planning_annuel_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY planning_annuel
    ADD CONSTRAINT planning_annuel_pkey PRIMARY KEY (id);


--
-- TOC entry 2107 (class 2606 OID 17657)
-- Dependencies: 1679 1679
-- Name: profiles_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY profiles
    ADD CONSTRAINT profiles_pkey PRIMARY KEY (id);


--
-- TOC entry 2109 (class 2606 OID 17665)
-- Dependencies: 1680 1680
-- Name: rights_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rights
    ADD CONSTRAINT rights_pkey PRIMARY KEY (id);


--
-- TOC entry 2111 (class 2606 OID 17673)
-- Dependencies: 1681 1681
-- Name: tasks_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);


--
-- TOC entry 2115 (class 2606 OID 17686)
-- Dependencies: 1683 1683
-- Name: timesheet_cells_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY timesheet_cells
    ADD CONSTRAINT timesheet_cells_pkey PRIMARY KEY (id);


--
-- TOC entry 2117 (class 2606 OID 17694)
-- Dependencies: 1684 1684
-- Name: timesheet_rows_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY timesheet_rows
    ADD CONSTRAINT timesheet_rows_pkey PRIMARY KEY (id);


--
-- TOC entry 2113 (class 2606 OID 17681)
-- Dependencies: 1682 1682
-- Name: timesheets_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY timesheets
    ADD CONSTRAINT timesheets_pkey PRIMARY KEY (id);


--
-- TOC entry 2119 (class 2606 OID 17702)
-- Dependencies: 1685 1685
-- Name: trainings_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY trainings
    ADD CONSTRAINT trainings_pkey PRIMARY KEY (id);


--
-- TOC entry 2121 (class 2606 OID 17710)
-- Dependencies: 1686 1686
-- Name: user_actions_pkey; Type: CONSTRAINT; Schema: interaudit; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_actions
    ADD CONSTRAINT user_actions_pkey PRIMARY KEY (id);


SET search_path = pgagent, pg_catalog;

--
-- TOC entry 2023 (class 2606 OID 17320)
-- Dependencies: 1644 1644
-- Name: pga_exception_pkey; Type: CONSTRAINT; Schema: pgagent; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pga_exception
    ADD CONSTRAINT pga_exception_pkey PRIMARY KEY (jexid);


--
-- TOC entry 2013 (class 2606 OID 17246)
-- Dependencies: 1638 1638
-- Name: pga_job_pkey; Type: CONSTRAINT; Schema: pgagent; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pga_job
    ADD CONSTRAINT pga_job_pkey PRIMARY KEY (jobid);


--
-- TOC entry 2008 (class 2606 OID 17218)
-- Dependencies: 1634 1634
-- Name: pga_jobagent_pkey; Type: CONSTRAINT; Schema: pgagent; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pga_jobagent
    ADD CONSTRAINT pga_jobagent_pkey PRIMARY KEY (jagpid);


--
-- TOC entry 2011 (class 2606 OID 17229)
-- Dependencies: 1636 1636
-- Name: pga_jobclass_pkey; Type: CONSTRAINT; Schema: pgagent; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pga_jobclass
    ADD CONSTRAINT pga_jobclass_pkey PRIMARY KEY (jclid);


--
-- TOC entry 2026 (class 2606 OID 17338)
-- Dependencies: 1646 1646
-- Name: pga_joblog_pkey; Type: CONSTRAINT; Schema: pgagent; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pga_joblog
    ADD CONSTRAINT pga_joblog_pkey PRIMARY KEY (jlgid);


--
-- TOC entry 2016 (class 2606 OID 17276)
-- Dependencies: 1640 1640
-- Name: pga_jobstep_pkey; Type: CONSTRAINT; Schema: pgagent; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pga_jobstep
    ADD CONSTRAINT pga_jobstep_pkey PRIMARY KEY (jstid);


--
-- TOC entry 2029 (class 2606 OID 17358)
-- Dependencies: 1648 1648
-- Name: pga_jobsteplog_pkey; Type: CONSTRAINT; Schema: pgagent; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pga_jobsteplog
    ADD CONSTRAINT pga_jobsteplog_pkey PRIMARY KEY (jslid);


--
-- TOC entry 2019 (class 2606 OID 17306)
-- Dependencies: 1642 1642
-- Name: pga_schedule_pkey; Type: CONSTRAINT; Schema: pgagent; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pga_schedule
    ADD CONSTRAINT pga_schedule_pkey PRIMARY KEY (jscid);


--
-- TOC entry 2020 (class 1259 OID 17327)
-- Dependencies: 1644 1644
-- Name: pga_exception_datetime; Type: INDEX; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX pga_exception_datetime ON pga_exception USING btree (jexdate, jextime);


--
-- TOC entry 2021 (class 1259 OID 17326)
-- Dependencies: 1644
-- Name: pga_exception_jexscid; Type: INDEX; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE INDEX pga_exception_jexscid ON pga_exception USING btree (jexscid);


--
-- TOC entry 2009 (class 1259 OID 17230)
-- Dependencies: 1636
-- Name: pga_jobclass_name; Type: INDEX; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX pga_jobclass_name ON pga_jobclass USING btree (jclname);


--
-- TOC entry 2024 (class 1259 OID 17344)
-- Dependencies: 1646
-- Name: pga_joblog_jobid; Type: INDEX; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE INDEX pga_joblog_jobid ON pga_joblog USING btree (jlgjobid);


--
-- TOC entry 2017 (class 1259 OID 17312)
-- Dependencies: 1642
-- Name: pga_jobschedule_jobid; Type: INDEX; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE INDEX pga_jobschedule_jobid ON pga_schedule USING btree (jscjobid);


--
-- TOC entry 2014 (class 1259 OID 17282)
-- Dependencies: 1640
-- Name: pga_jobstep_jobid; Type: INDEX; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE INDEX pga_jobstep_jobid ON pga_jobstep USING btree (jstjobid);


--
-- TOC entry 2027 (class 1259 OID 17369)
-- Dependencies: 1648
-- Name: pga_jobsteplog_jslid; Type: INDEX; Schema: pgagent; Owner: postgres; Tablespace: 
--

CREATE INDEX pga_jobsteplog_jslid ON pga_jobsteplog USING btree (jsljlgid);


--
-- TOC entry 2189 (class 2620 OID 17379)
-- Dependencies: 25 1644
-- Name: pga_exception_trigger; Type: TRIGGER; Schema: pgagent; Owner: postgres
--

CREATE TRIGGER pga_exception_trigger
    AFTER INSERT OR DELETE OR UPDATE ON pga_exception
    FOR EACH ROW
    EXECUTE PROCEDURE pga_exception_trigger();


--
-- TOC entry 2276 (class 0 OID 0)
-- Dependencies: 2189
-- Name: TRIGGER pga_exception_trigger ON pga_exception; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON TRIGGER pga_exception_trigger ON pga_exception IS 'Update the job''s next run time whenever an exception changes';


--
-- TOC entry 2187 (class 2620 OID 17375)
-- Dependencies: 1638 23
-- Name: pga_job_trigger; Type: TRIGGER; Schema: pgagent; Owner: postgres
--

CREATE TRIGGER pga_job_trigger
    BEFORE UPDATE ON pga_job
    FOR EACH ROW
    EXECUTE PROCEDURE pga_job_trigger();


--
-- TOC entry 2277 (class 0 OID 0)
-- Dependencies: 2187
-- Name: TRIGGER pga_job_trigger ON pga_job; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON TRIGGER pga_job_trigger ON pga_job IS 'Update the job''s next run time.';


--
-- TOC entry 2188 (class 2620 OID 17377)
-- Dependencies: 1642 24
-- Name: pga_schedule_trigger; Type: TRIGGER; Schema: pgagent; Owner: postgres
--

CREATE TRIGGER pga_schedule_trigger
    AFTER INSERT OR DELETE OR UPDATE ON pga_schedule
    FOR EACH ROW
    EXECUTE PROCEDURE pga_schedule_trigger();


--
-- TOC entry 2278 (class 0 OID 0)
-- Dependencies: 2188
-- Name: TRIGGER pga_schedule_trigger ON pga_schedule; Type: COMMENT; Schema: pgagent; Owner: postgres
--

COMMENT ON TRIGGER pga_schedule_trigger ON pga_schedule IS 'Update the job''s next run time whenever a schedule changes';


SET search_path = interaudit, pg_catalog;

--
-- TOC entry 2141 (class 2606 OID 17766)
-- Dependencies: 1655 2066 1663
-- Name: fk3846c5ae294b3b2e; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY budgets
    ADD CONSTRAINT fk3846c5ae294b3b2e FOREIGN KEY (fk_manager) REFERENCES employees(id);


--
-- TOC entry 2140 (class 2606 OID 17761)
-- Dependencies: 1666 1655 2072
-- Name: fk3846c5ae3e4adc37; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY budgets
    ADD CONSTRAINT fk3846c5ae3e4adc37 FOREIGN KEY (fk_exercise) REFERENCES exercises(id);


--
-- TOC entry 2143 (class 2606 OID 17776)
-- Dependencies: 1658 2050 1655
-- Name: fk3846c5ae592faab; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY budgets
    ADD CONSTRAINT fk3846c5ae592faab FOREIGN KEY (fk_contract) REFERENCES contracts(id);


--
-- TOC entry 2142 (class 2606 OID 17771)
-- Dependencies: 1663 2066 1655
-- Name: fk3846c5aecd847372; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY budgets
    ADD CONSTRAINT fk3846c5aecd847372 FOREIGN KEY (fk_associe) REFERENCES employees(id);


--
-- TOC entry 2160 (class 2606 OID 17856)
-- Dependencies: 1678 1665 2104
-- Name: fk3cec37828e341e3c; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY event_planning
    ADD CONSTRAINT fk3cec37828e341e3c FOREIGN KEY (fk_planning) REFERENCES planning_annuel(id);


--
-- TOC entry 2159 (class 2606 OID 17851)
-- Dependencies: 1663 1665 2066
-- Name: fk3cec3782d76ea5e3; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY event_planning
    ADD CONSTRAINT fk3cec3782d76ea5e3 FOREIGN KEY (fk_employee) REFERENCES employees(id);


--
-- TOC entry 2130 (class 2606 OID 17711)
-- Dependencies: 2060 1649 1661
-- Name: fk40477c6ac08e7b27; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY docs_invoices
    ADD CONSTRAINT fk40477c6ac08e7b27 FOREIGN KEY (id_document) REFERENCES documents(id);


--
-- TOC entry 2131 (class 2606 OID 17716)
-- Dependencies: 2076 1667 1649
-- Name: fk40477c6ae4504b49; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY docs_invoices
    ADD CONSTRAINT fk40477c6ae4504b49 FOREIGN KEY (id_invoice) REFERENCES invoices(id);


--
-- TOC entry 2155 (class 2606 OID 17831)
-- Dependencies: 1679 2106 1663
-- Name: fk4351ff65a24e0ad9; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY employees
    ADD CONSTRAINT fk4351ff65a24e0ad9 FOREIGN KEY (fk_position) REFERENCES profiles(id);


--
-- TOC entry 2168 (class 2606 OID 17896)
-- Dependencies: 1670 1663 2066
-- Name: fk4448e4bc6e71fea; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY invoice_reminds
    ADD CONSTRAINT fk4448e4bc6e71fea FOREIGN KEY (fk_sender) REFERENCES employees(id);


--
-- TOC entry 2169 (class 2606 OID 17901)
-- Dependencies: 1670 1659 2056
-- Name: fk4448e4bc916bd843; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY invoice_reminds
    ADD CONSTRAINT fk4448e4bc916bd843 FOREIGN KEY (fk_customer) REFERENCES customers(id);


--
-- TOC entry 2184 (class 2606 OID 17976)
-- Dependencies: 1684 1653 2034
-- Name: fk4fbc2b6683c0bea5; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY timesheet_rows
    ADD CONSTRAINT fk4fbc2b6683c0bea5 FOREIGN KEY (fk_activity) REFERENCES activities(id);


--
-- TOC entry 2183 (class 2606 OID 17971)
-- Dependencies: 2112 1684 1682
-- Name: fk4fbc2b66e279bdbd; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY timesheet_rows
    ADD CONSTRAINT fk4fbc2b66e279bdbd FOREIGN KEY (fk_timesheet) REFERENCES timesheets(id);


--
-- TOC entry 2170 (class 2606 OID 17906)
-- Dependencies: 1671 1665 2070
-- Name: fk51754a2ea46e07e7; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY item_event_planning
    ADD CONSTRAINT fk51754a2ea46e07e7 FOREIGN KEY (fk_eventplanning) REFERENCES event_planning(id);


--
-- TOC entry 2167 (class 2606 OID 17891)
-- Dependencies: 2076 1667 1669
-- Name: fk53b2279f5f680542; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY invoice_fees
    ADD CONSTRAINT fk53b2279f5f680542 FOREIGN KEY (fk_facture) REFERENCES invoices(id);


--
-- TOC entry 2137 (class 2606 OID 17746)
-- Dependencies: 2108 1652 1680
-- Name: fk541e311220f5d111; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY access_rights
    ADD CONSTRAINT fk541e311220f5d111 FOREIGN KEY (fk_right) REFERENCES rights(id);


--
-- TOC entry 2136 (class 2606 OID 17741)
-- Dependencies: 1663 2066 1652
-- Name: fk541e3112d76ea5e3; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY access_rights
    ADD CONSTRAINT fk541e3112d76ea5e3 FOREIGN KEY (fk_employee) REFERENCES employees(id);


--
-- TOC entry 2162 (class 2606 OID 17866)
-- Dependencies: 1673 2090 1667
-- Name: fk549812e61b8c81a0; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk549812e61b8c81a0 FOREIGN KEY (project_id) REFERENCES missions(id);


--
-- TOC entry 2163 (class 2606 OID 17871)
-- Dependencies: 1667 1663 2066
-- Name: fk549812e634d0abad; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk549812e634d0abad FOREIGN KEY (fk_creator) REFERENCES employees(id);


--
-- TOC entry 2164 (class 2606 OID 17876)
-- Dependencies: 1667 2066 1663
-- Name: fk549812e66e71fea; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk549812e66e71fea FOREIGN KEY (fk_sender) REFERENCES employees(id);


--
-- TOC entry 2165 (class 2606 OID 17881)
-- Dependencies: 2042 1667 1654
-- Name: fk549812e6888a4d07; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk549812e6888a4d07 FOREIGN KEY (bank_id) REFERENCES banks(id);


--
-- TOC entry 2161 (class 2606 OID 17861)
-- Dependencies: 1667 1663 2066
-- Name: fk549812e6c83f0849; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk549812e6c83f0849 FOREIGN KEY (fk_partner) REFERENCES employees(id);


--
-- TOC entry 2148 (class 2606 OID 17801)
-- Dependencies: 1659 2066 1663
-- Name: fk6268c35294b3b2e; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT fk6268c35294b3b2e FOREIGN KEY (fk_manager) REFERENCES employees(id);


--
-- TOC entry 2151 (class 2606 OID 18063)
-- Dependencies: 2092 1659 1674
-- Name: fk6268c35ab95b03e; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT fk6268c35ab95b03e FOREIGN KEY (fk_contracttype) REFERENCES missiontype_task(id);


--
-- TOC entry 2149 (class 2606 OID 17806)
-- Dependencies: 1659 2066 1663
-- Name: fk6268c35cd847372; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT fk6268c35cd847372 FOREIGN KEY (fk_associe) REFERENCES employees(id);


--
-- TOC entry 2150 (class 2606 OID 17811)
-- Dependencies: 1659 2098 1676
-- Name: fk6268c35f486ac93; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT fk6268c35f486ac93 FOREIGN KEY (fk_origin) REFERENCES origins(id);


--
-- TOC entry 2171 (class 2606 OID 17911)
-- Dependencies: 1672 1663 2066
-- Name: fk63b68be723ab9a5a; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY message
    ADD CONSTRAINT fk63b68be723ab9a5a FOREIGN KEY (to_id) REFERENCES employees(id);


--
-- TOC entry 2174 (class 2606 OID 17926)
-- Dependencies: 2088 1672 1672
-- Name: fk63b68be758b0cea; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY message
    ADD CONSTRAINT fk63b68be758b0cea FOREIGN KEY (parent_id) REFERENCES message(id);


--
-- TOC entry 2173 (class 2606 OID 17921)
-- Dependencies: 1672 1673 2090
-- Name: fk63b68be78b5ed24d; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY message
    ADD CONSTRAINT fk63b68be78b5ed24d FOREIGN KEY (mission_id) REFERENCES missions(id);


--
-- TOC entry 2172 (class 2606 OID 17916)
-- Dependencies: 1672 1663 2066
-- Name: fk63b68be7f9a84ecb; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY message
    ADD CONSTRAINT fk63b68be7f9a84ecb FOREIGN KEY (from_id) REFERENCES employees(id);


--
-- TOC entry 2153 (class 2606 OID 17821)
-- Dependencies: 1662 2066 1663
-- Name: fk6d3f1a8d6017a924; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY email_data
    ADD CONSTRAINT fk6d3f1a8d6017a924 FOREIGN KEY (fk_receiver) REFERENCES employees(id);


--
-- TOC entry 2154 (class 2606 OID 17826)
-- Dependencies: 1663 2066 1662
-- Name: fk6d3f1a8d6e71fea; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY email_data
    ADD CONSTRAINT fk6d3f1a8d6e71fea FOREIGN KEY (fk_sender) REFERENCES employees(id);


--
-- TOC entry 2158 (class 2606 OID 17846)
-- Dependencies: 1664 2110 1681
-- Name: fk7a9ad51941091c67; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY events
    ADD CONSTRAINT fk7a9ad51941091c67 FOREIGN KEY (task_id) REFERENCES tasks(id);


--
-- TOC entry 2156 (class 2606 OID 17836)
-- Dependencies: 1653 2034 1664
-- Name: fk7a9ad51983c0bea5; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY events
    ADD CONSTRAINT fk7a9ad51983c0bea5 FOREIGN KEY (fk_activity) REFERENCES activities(id);


--
-- TOC entry 2157 (class 2606 OID 17841)
-- Dependencies: 2066 1664 1663
-- Name: fk7a9ad519d76ea5e3; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY events
    ADD CONSTRAINT fk7a9ad519d76ea5e3 FOREIGN KEY (fk_employee) REFERENCES employees(id);


--
-- TOC entry 2179 (class 2606 OID 17951)
-- Dependencies: 1667 1677 2076
-- Name: fk810fff2d5f680542; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT fk810fff2d5f680542 FOREIGN KEY (fk_facture) REFERENCES invoices(id);


--
-- TOC entry 2180 (class 2606 OID 17956)
-- Dependencies: 2066 1663 1682
-- Name: fk821e8001d76ea5e3; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY timesheets
    ADD CONSTRAINT fk821e8001d76ea5e3 FOREIGN KEY (fk_employee) REFERENCES employees(id);


--
-- TOC entry 2181 (class 2606 OID 17961)
-- Dependencies: 1682 1663 2066
-- Name: fk821e8001eb403f21; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY timesheets
    ADD CONSTRAINT fk821e8001eb403f21 FOREIGN KEY (userid) REFERENCES employees(id);


--
-- TOC entry 2178 (class 2606 OID 17946)
-- Dependencies: 1663 1675 2066
-- Name: fk831f01938036ab22; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY mission_costs
    ADD CONSTRAINT fk831f01938036ab22 FOREIGN KEY (owner_id) REFERENCES employees(id);


--
-- TOC entry 2177 (class 2606 OID 17941)
-- Dependencies: 1673 1675 2090
-- Name: fk831f01938b5ed24d; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY mission_costs
    ADD CONSTRAINT fk831f01938b5ed24d FOREIGN KEY (mission_id) REFERENCES missions(id);


--
-- TOC entry 2147 (class 2606 OID 17796)
-- Dependencies: 2056 1659 1658
-- Name: fk8e852181916bd843; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY contracts
    ADD CONSTRAINT fk8e852181916bd843 FOREIGN KEY (fk_customer) REFERENCES customers(id);


--
-- TOC entry 2138 (class 2606 OID 17751)
-- Dependencies: 2110 1653 1681
-- Name: fk970527ed41091c67; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY activities
    ADD CONSTRAINT fk970527ed41091c67 FOREIGN KEY (task_id) REFERENCES tasks(id);


--
-- TOC entry 2139 (class 2606 OID 17756)
-- Dependencies: 1653 1673 2090
-- Name: fk970527ed8b5ed24d; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY activities
    ADD CONSTRAINT fk970527ed8b5ed24d FOREIGN KEY (mission_id) REFERENCES missions(id);


--
-- TOC entry 2186 (class 2606 OID 17986)
-- Dependencies: 1686 1663 2066
-- Name: fk9bf46469d76ea5e3; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY user_actions
    ADD CONSTRAINT fk9bf46469d76ea5e3 FOREIGN KEY (fk_employee) REFERENCES employees(id);


--
-- TOC entry 2166 (class 2606 OID 17886)
-- Dependencies: 1668 1667 2076
-- Name: fk9f394cd95f680542; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY invoice_deduction_fees
    ADD CONSTRAINT fk9f394cd95f680542 FOREIGN KEY (fk_facture) REFERENCES invoices(id);


--
-- TOC entry 2182 (class 2606 OID 17966)
-- Dependencies: 2116 1683 1684
-- Name: fka6f12b44472a6cf5; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY timesheet_cells
    ADD CONSTRAINT fka6f12b44472a6cf5 FOREIGN KEY (fk_row) REFERENCES timesheet_rows(id);


--
-- TOC entry 2145 (class 2606 OID 17786)
-- Dependencies: 1663 2066 1656
-- Name: fkabdcdf4d76ea5e3; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkabdcdf4d76ea5e3 FOREIGN KEY (fk_employee) REFERENCES employees(id);


--
-- TOC entry 2144 (class 2606 OID 17781)
-- Dependencies: 1656 1682 2112
-- Name: fkabdcdf4e279bdbd; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkabdcdf4e279bdbd FOREIGN KEY (fk_timesheet) REFERENCES timesheets(id);


--
-- TOC entry 2146 (class 2606 OID 17791)
-- Dependencies: 1659 2056 1657
-- Name: fkcd35053916bd843; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY contacts
    ADD CONSTRAINT fkcd35053916bd843 FOREIGN KEY (fk_customer) REFERENCES customers(id);


--
-- TOC entry 2132 (class 2606 OID 17721)
-- Dependencies: 1673 1650 2090
-- Name: fkd26709eb7a20c807; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY docs_missions
    ADD CONSTRAINT fkd26709eb7a20c807 FOREIGN KEY (id_mission) REFERENCES missions(id);


--
-- TOC entry 2133 (class 2606 OID 17726)
-- Dependencies: 1661 2060 1650
-- Name: fkd26709ebc08e7b27; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY docs_missions
    ADD CONSTRAINT fkd26709ebc08e7b27 FOREIGN KEY (id_document) REFERENCES documents(id);


--
-- TOC entry 2185 (class 2606 OID 17981)
-- Dependencies: 2066 1685 1663
-- Name: fkdc0922b960e76277; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY trainings
    ADD CONSTRAINT fkdc0922b960e76277 FOREIGN KEY (beneficiaire_id) REFERENCES employees(id);


--
-- TOC entry 2152 (class 2606 OID 17816)
-- Dependencies: 2066 1663 1661
-- Name: fkde5562982368ffae; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY documents
    ADD CONSTRAINT fkde5562982368ffae FOREIGN KEY (owner) REFERENCES employees(id);


--
-- TOC entry 2175 (class 2606 OID 17931)
-- Dependencies: 1673 1673 2090
-- Name: fke6b7a0677574df03; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY missions
    ADD CONSTRAINT fke6b7a0677574df03 FOREIGN KEY (fk_parent) REFERENCES missions(id);


--
-- TOC entry 2176 (class 2606 OID 17936)
-- Dependencies: 2044 1673 1655
-- Name: fke6b7a0679bed1b27; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY missions
    ADD CONSTRAINT fke6b7a0679bed1b27 FOREIGN KEY (annualbudget_id) REFERENCES budgets(id);


--
-- TOC entry 2134 (class 2606 OID 17731)
-- Dependencies: 2084 1670 1651
-- Name: fkee65ca1f464c3d1b; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY invoice_reminds_relation
    ADD CONSTRAINT fkee65ca1f464c3d1b FOREIGN KEY (remindid) REFERENCES invoice_reminds(id);


--
-- TOC entry 2135 (class 2606 OID 17736)
-- Dependencies: 2076 1667 1651
-- Name: fkee65ca1f5e61d848; Type: FK CONSTRAINT; Schema: interaudit; Owner: postgres
--

ALTER TABLE ONLY invoice_reminds_relation
    ADD CONSTRAINT fkee65ca1f5e61d848 FOREIGN KEY (invoiceid) REFERENCES invoices(id);


SET search_path = pgagent, pg_catalog;

--
-- TOC entry 2126 (class 2606 OID 17321)
-- Dependencies: 2018 1642 1644
-- Name: pga_exception_jexscid_fkey; Type: FK CONSTRAINT; Schema: pgagent; Owner: postgres
--

ALTER TABLE ONLY pga_exception
    ADD CONSTRAINT pga_exception_jexscid_fkey FOREIGN KEY (jexscid) REFERENCES pga_schedule(jscid) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 2123 (class 2606 OID 17252)
-- Dependencies: 2007 1634 1638
-- Name: pga_job_jobagentid_fkey; Type: FK CONSTRAINT; Schema: pgagent; Owner: postgres
--

ALTER TABLE ONLY pga_job
    ADD CONSTRAINT pga_job_jobagentid_fkey FOREIGN KEY (jobagentid) REFERENCES pga_jobagent(jagpid) ON UPDATE RESTRICT ON DELETE SET NULL;


--
-- TOC entry 2122 (class 2606 OID 17247)
-- Dependencies: 1638 2010 1636
-- Name: pga_job_jobjclid_fkey; Type: FK CONSTRAINT; Schema: pgagent; Owner: postgres
--

ALTER TABLE ONLY pga_job
    ADD CONSTRAINT pga_job_jobjclid_fkey FOREIGN KEY (jobjclid) REFERENCES pga_jobclass(jclid) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2127 (class 2606 OID 17339)
-- Dependencies: 1638 2012 1646
-- Name: pga_joblog_jlgjobid_fkey; Type: FK CONSTRAINT; Schema: pgagent; Owner: postgres
--

ALTER TABLE ONLY pga_joblog
    ADD CONSTRAINT pga_joblog_jlgjobid_fkey FOREIGN KEY (jlgjobid) REFERENCES pga_job(jobid) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 2124 (class 2606 OID 17277)
-- Dependencies: 2012 1640 1638
-- Name: pga_jobstep_jstjobid_fkey; Type: FK CONSTRAINT; Schema: pgagent; Owner: postgres
--

ALTER TABLE ONLY pga_jobstep
    ADD CONSTRAINT pga_jobstep_jstjobid_fkey FOREIGN KEY (jstjobid) REFERENCES pga_job(jobid) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 2128 (class 2606 OID 17359)
-- Dependencies: 1646 1648 2025
-- Name: pga_jobsteplog_jsljlgid_fkey; Type: FK CONSTRAINT; Schema: pgagent; Owner: postgres
--

ALTER TABLE ONLY pga_jobsteplog
    ADD CONSTRAINT pga_jobsteplog_jsljlgid_fkey FOREIGN KEY (jsljlgid) REFERENCES pga_joblog(jlgid) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 2129 (class 2606 OID 17364)
-- Dependencies: 1640 2015 1648
-- Name: pga_jobsteplog_jsljstid_fkey; Type: FK CONSTRAINT; Schema: pgagent; Owner: postgres
--

ALTER TABLE ONLY pga_jobsteplog
    ADD CONSTRAINT pga_jobsteplog_jsljstid_fkey FOREIGN KEY (jsljstid) REFERENCES pga_jobstep(jstid) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 2125 (class 2606 OID 17307)
-- Dependencies: 1638 1642 2012
-- Name: pga_schedule_jscjobid_fkey; Type: FK CONSTRAINT; Schema: pgagent; Owner: postgres
--

ALTER TABLE ONLY pga_schedule
    ADD CONSTRAINT pga_schedule_jscjobid_fkey FOREIGN KEY (jscjobid) REFERENCES pga_job(jobid) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 2241 (class 0 OID 0)
-- Dependencies: 7
-- Name: interaudit; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA interaudit FROM PUBLIC;
REVOKE ALL ON SCHEMA interaudit FROM postgres;
GRANT ALL ON SCHEMA interaudit TO postgres;
GRANT ALL ON SCHEMA interaudit TO PUBLIC;


-- Completed on 2010-12-27 11:01:28

--
-- PostgreSQL database dump complete
--

