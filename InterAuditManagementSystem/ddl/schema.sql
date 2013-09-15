
    alter table ACCESS_RIGHTS 
        drop constraint FK541E3112D76EA5E3;

    alter table ACCESS_RIGHTS 
        drop constraint FK541E311220F5D111;

    alter table ACTIVITIES 
        drop constraint FK970527ED41091C67;

    alter table ACTIVITIES 
        drop constraint FK970527ED8B5ED24D;

    alter table BUDGETS 
        drop constraint FK3846C5AE3E4ADC37;

    alter table BUDGETS 
        drop constraint FK3846C5AE294B3B2E;

    alter table BUDGETS 
        drop constraint FK3846C5AECD847372;

    alter table BUDGETS 
        drop constraint FK3846C5AE592FAAB;

    alter table COMMENTS 
        drop constraint FKABDCDF4E279BDBD;

    alter table COMMENTS 
        drop constraint FKABDCDF4D76EA5E3;

    alter table CONTACTS 
        drop constraint FKCD35053916BD843;

    alter table CONTRACTS 
        drop constraint FK8E852181916BD843;

    alter table CUSTOMERS 
        drop constraint FK6268C35294B3B2E;

    alter table CUSTOMERS 
        drop constraint FK6268C35CD847372;

    alter table CUSTOMERS 
        drop constraint FK6268C35F486AC93;

    alter table DOCS_INVOICES 
        drop constraint FK40477C6AC08E7B27;

    alter table DOCS_INVOICES 
        drop constraint FK40477C6AE4504B49;

    alter table DOCS_MISSIONS 
        drop constraint FKD26709EB7A20C807;

    alter table DOCS_MISSIONS 
        drop constraint FKD26709EBC08E7B27;

    alter table DOCUMENTS 
        drop constraint FKDE5562982368FFAE;

    alter table EMAIL_DATA 
        drop constraint FK6D3F1A8D6017A924;

    alter table EMAIL_DATA 
        drop constraint FK6D3F1A8D6E71FEA;

    alter table EMPLOYEES 
        drop constraint FK4351FF65A24E0AD9;

    alter table EVENTS 
        drop constraint FK7A9AD51983C0BEA5;

    alter table EVENTS 
        drop constraint FK7A9AD519D76EA5E3;

    alter table EVENTS 
        drop constraint FK7A9AD51941091C67;

    alter table EVENT_PLANNING 
        drop constraint FK3CEC3782D76EA5E3;

    alter table EVENT_PLANNING 
        drop constraint FK3CEC37828E341E3C;

    alter table INVOICES 
        drop constraint FK549812E6C83F0849;

    alter table INVOICES 
        drop constraint FK549812E61B8C81A0;

    alter table INVOICES 
        drop constraint FK549812E69246D9CD;

    alter table INVOICES 
        drop constraint FK549812E6888A4D07;

    alter table INVOICES 
        drop constraint FK549812E634D0ABAD;

    alter table INVOICE_FEES 
        drop constraint FK53B2279F5F680542;

    alter table INVOICE_REMINDS 
        drop constraint FK4448E4BC5F680542;

    alter table INVOICE_REMINDS 
        drop constraint FK4448E4BC6E71FEA;

    alter table ITEM_EVENT_PLANNING 
        drop constraint FK51754A2EA46E07E7;

    alter table MESSAGE 
        drop constraint FK63B68BE723AB9A5A;

    alter table MESSAGE 
        drop constraint FK63B68BE7F9A84ECB;

    alter table MESSAGE 
        drop constraint FK63B68BE78B5ED24D;

    alter table MESSAGE 
        drop constraint FK63B68BE758B0CEA;

    alter table MISSIONS 
        drop constraint FKE6B7A0677574DF03;

    alter table MISSIONS 
        drop constraint FKE6B7A0679BED1B27;

    alter table MISSION_COSTS 
        drop constraint FK831F01938B5ED24D;

    alter table MISSION_COSTS 
        drop constraint FK831F01938036AB22;

    alter table PAYMENTS 
        drop constraint FK810FFF2D5F680542;

    alter table TIMESHEETS 
        drop constraint FK821E8001D76EA5E3;

    alter table TIMESHEETS 
        drop constraint FK821E8001EB403F21;

    alter table TIMESHEET_CELLS 
        drop constraint FKA6F12B44472A6CF5;

    alter table TIMESHEET_ROWS 
        drop constraint FK4FBC2B66E279BDBD;

    alter table TIMESHEET_ROWS 
        drop constraint FK4FBC2B6683C0BEA5;

    alter table TRAININGS 
        drop constraint FKDC0922B960E76277;

    alter table USER_ACTIONS 
        drop constraint FK9BF46469D76EA5E3;

    drop table ACCESS_RIGHTS;

    drop table ACTIVITIES;

    drop table BANKS;

    drop table BUDGETS;

    drop table COMMENTS;

    drop table CONTACTS;

    drop table CONTRACTS;

    drop table CUSTOMERS;

    drop table DECLARATIONS;

    drop table DOCS_INVOICES;

    drop table DOCS_MISSIONS;

    drop table DOCUMENTS;

    drop table EMAIL_DATA;

    drop table EMPLOYEES;

    drop table EVENTS;

    drop table EVENT_PLANNING;

    drop table EXERCISES;

    drop table INVOICES;

    drop table INVOICE_FEES;

    drop table INVOICE_REMINDS;

    drop table ITEM_EVENT_PLANNING;

    drop table MESSAGE;

    drop table MISSIONS;

    drop table MISSIONTYPE_TASK;

    drop table MISSION_COSTS;

    drop table ORIGINS;

    drop table PAYMENTS;

    drop table PLANNING_ANNUEL;

    drop table PROFILES;

    drop table RIGHTS;

    drop table TASKS;

    drop table TIMESHEETS;

    drop table TIMESHEET_CELLS;

    drop table TIMESHEET_ROWS;

    drop table TRAININGS;

    drop table USER_ACTIONS;

    drop sequence hibernate_sequence;

    create table ACCESS_RIGHTS (
        id int8 not null,
        active bool not null,
        fk_right int8 not null,
        fk_employee int8 not null,
        primary key (id)
    );

    create table ACTIVITIES (
        id int8 not null,
        status varchar(255),
        toDo varchar(255),
        comments varchar(255),
        VERSION int4 not null,
        ORDRE int4 not null,
        STARTDATE date not null,
        updateDate date,
        endDate date,
        mission_id int8,
        task_id int8,
        primary key (id)
    );

    create table BANKS (
        id int8 not null,
        NAME varchar(255) not null unique,
        CODE varchar(255) not null unique,
        ACCOUNT varchar(255) not null unique,
        contactPerson varchar(255),
        contactPersonEmail varchar(255),
        contactPersonPhone varchar(255),
        contactPersonFax varchar(255),
        active bool not null,
        primary key (id)
    );

    create table BUDGETS (
        id int8 not null,
        VERSION int4 not null,
        EXP_AMOUNT float8 not null,
        REAL_AMOUNT float8,
        REP_AMOUNT float8,
        comments varchar(255),
        status varchar(255),
        fiable bool not null,
        fk_manager int8 not null,
        fk_associe int8 not null,
        fk_exercise int8 not null,
        fk_contract int8 not null,
        primary key (id)
    );

    create table COMMENTS (
        id int8 not null,
        text varchar(255),
        created timestamp,
        fk_employee int8 not null,
        fk_timesheet int8 not null,
        primary key (id)
    );

    create table CONTACTS (
        id int8 not null,
        SEX varchar(255) not null,
        NAME varchar(255) not null,
        F_NAME varchar(255) not null,
        L_NAME varchar(255) not null,
        JOB varchar(255),
        B_PHONE varchar(255),
        B_MOBILE varchar(255),
        B_FAX varchar(255),
        P_PHONE varchar(255) not null,
        P_MOBILE varchar(255),
        B_EMAIL varchar(255),
        P_EMAIL varchar(255),
        B_WEB_ADDR varchar(255),
        active bool not null,
        B_ACTIVITY varchar(255),
        updatedUser varchar(255),
        COMPANY varchar(255) not null,
        address varchar(255),
        createDate date,
        updateDate date,
        VERSION int4 not null,
        fk_customer int8 not null,
        primary key (id)
    );

    create table CONTRACTS (
        id int8 not null,
        reference varchar(255),
        description varchar(255),
        fromDate timestamp,
        toDate timestamp,
        duration int4,
        language varchar(255),
        VAL float8 not null,
        CUR varchar(255) not null,
        VERSION int4 not null,
        missionType varchar(255) not null,
        agreed bool,
        fk_customer int8,
        primary key (id)
    );

    create table CUSTOMERS (
        id int8 not null,
        WEB_ADDRESS varchar(255),
        active bool not null,
        country varchar(255),
        mainAddress varchar(255),
        mainBillingAddress varchar(255),
        ACTIVITY varchar(255),
        createDate date,
        updateDate date,
        updatedUser varchar(255),
        COMPY_NAME varchar(255) unique,
        FAX varchar(255),
        PHONE varchar(255),
        MOBILE varchar(255),
        P_PHONE varchar(255),
        P_MOBILE varchar(255),
        email varchar(255),
        CODE varchar(255) not null unique,
        VERSION int4 not null,
        fk_origin int8,
        fk_manager int8 not null,
        fk_associe int8 not null,
        primary key (id)
    );

    create table DECLARATIONS (
        id int8 not null,
        exercise varchar(255),
        customer varchar(255),
        requestDate timestamp,
        receiptDate timestamp,
        validityDate timestamp,
        declaration bool not null,
        passport bool not null,
        active bool not null,
        primary key (id)
    );

    create table DOCS_INVOICES (
        ID_INVOICE int8 not null,
        ID_DOCUMENT int8 not null
    );

    create table DOCS_MISSIONS (
        ID_MISSION int8 not null,
        ID_DOCUMENT int8 not null,
        primary key (ID_MISSION, ID_DOCUMENT)
    );

    create table DOCUMENTS (
        id int8 not null,
        FILE_NAME varchar(255),
        DESCRIPTION varchar(255),
        createDate timestamp,
        updateDate timestamp,
        TITLE varchar(255),
        VERSION int4 not null,
        OWNER int8,
        primary key (id)
    );

    create table EMAIL_DATA (
        id int8 not null,
        sender_address varchar(255),
        receiver_address varchar(255),
        mailSubject varchar(255),
        mailContents varchar(255),
        created timestamp,
        sentDate timestamp,
        status varchar(255),
        type varchar(255),
        fk_sender int8,
        fk_receiver int8,
        primary key (id)
    );

    create table EMPLOYEES (
        id int8 not null,
        VERSION int4 not null,
        firstName varchar(255),
        lastName varchar(255),
        jobTitle varchar(255),
        email varchar(255),
        code varchar(255),
        active bool,
        password varchar(255),
        login varchar(255) unique,
        updateUser varchar(255),
        date_of_hiring date,
        createDate timestamp,
        modifiedDate timestamp,
        prixVente float8 not null,
        prixRevient float8 not null,
        coutHoraire float8 not null,
        fk_position int8,
        primary key (id)
    );

    create table EVENTS (
        id int8 not null,
        year int4 not null,
        month int4 not null,
        day int4 not null,
        startHour int4 not null,
        endHour int4 not null,
        type varchar(255),
        title varchar(255),
        dateOfEvent timestamp,
        valid bool not null,
        fk_employee int8 not null,
        TASK_ID int8,
        fk_activity int8,
        primary key (id)
    );

    create table EVENT_PLANNING (
        id int8 not null,
        year int4 not null,
        weekNumber int4 not null,
        fk_planning int8 not null,
        fk_employee int8 not null,
        primary key (id)
    );

    create table EXERCISES (
        id int8 not null,
        VERSION int4 not null,
        year int4 not null unique,
        status varchar(255),
        startDate timestamp,
        endDate timestamp,
        isAppproved bool not null,
        TOT_EXP_AMOUNT float8 not null,
        inflationPercentage float4 not null,
        TOT_REP_AMOUNT float8 not null,
        TOT_REAL_AMOUNT float8,
        TOT_INACTIF_AMOUNT float8,
        primary key (id)
    );

    create table INVOICES (
        id int8 not null,
        VERSION int4 not null,
        type varchar(255),
        language varchar(255),
        tva float4 not null,
        delaiPaiement int4 not null,
        dateFacture date,
        sentDate date,
        dateOfPayment date,
        CURRENCY varchar(3) not null,
        YEAR varchar(255) not null,
        libelle varchar(255) not null,
        HONORAIRES float8 not null,
        AMOUNT float8 not null,
        AMOUNT_NET float8 not null,
        REFERENCE varchar(255) not null unique,
        status varchar(255),
        PAYS varchar(255) not null,
        BILLADDRESS varchar(255) not null,
        CUSTCODE varchar(255) not null,
        CUSTNAME varchar(255) not null,
        approved bool,
        sent bool,
        fk_partner int8 not null,
        fk_creator int8 not null,
        bank_id int8,
        project_id int8,
        contact_id int8,
        primary key (id)
    );

    create table INVOICE_FEES (
        id int8 not null,
        createDate date,
        codeJustification varchar(255),
        justification varchar(255),
        value float8 not null,
        VERSION int4 not null,
        fk_facture int8 not null,
        primary key (id)
    );

    create table INVOICE_REMINDS (
        id int8 not null,
        remindDate date,
        startValidityDate date,
        endValidityDate date,
        active bool,
        sent bool,
        ordre int4 not null,
        VERSION int4 not null,
        fk_facture int8 not null,
        fk_sender int8,
        primary key (id)
    );

    create table ITEM_EVENT_PLANNING (
        id int8 not null,
        mission bool not null,
        idEntity int8,
        title varchar(255),
        dateOfEvent timestamp,
        startDate varchar(255),
        endDate varchar(255),
        durationType varchar(255),
        duration float4 not null,
        sFormat varchar(255),
        fk_eventplanning int8 not null,
        primary key (id)
    );

    create table MESSAGE (
        ID int8 not null,
        subject varchar(255),
        contents varchar(255),
        createDate timestamp,
        sentDate timestamp,
        read bool not null,
        to_id int8,
        parent_ID int8,
        from_id int8,
        mission_id int8,
        primary key (ID)
    );

    create table MISSIONS (
        id int8 not null,
        exercise varchar(255),
        title varchar(255),
        startWeek int4 not null,
        refer varchar(255) not null,
        comments varchar(255) not null,
        createDate date not null,
        startDate date,
        dueDate date,
        dateCloture date,
        updateDate date,
        updatedUser varchar(255),
        status varchar(255),
        typ varchar(255) not null,
        VERSION int4 not null,
        language varchar(255),
        JOB_STATUS varchar(255) not null,
        toDo varchar(255),
        jobComment varchar(255),
        toFinish varchar(255),
        fk_parent int8,
        annualBudget_id int8,
        primary key (id)
    );

    create table MISSIONTYPE_TASK (
        ID int8 not null,
        missionTypeCode varchar(255),
        libelle varchar(255),
        taskCode varchar(255),
        primary key (ID)
    );

    create table MISSION_COSTS (
        id int8 not null,
        VERSION int4 not null,
        costCode varchar(255),
        DESCRIPTION varchar(255),
        amount float8 not null,
        CURRENCY varchar(3) not null,
        createDate timestamp,
        mission_id int8,
        owner_id int8,
        primary key (id)
    );

    create table ORIGINS (
        id int8 not null,
        NAME varchar(255) not null,
        CODE varchar(255) not null unique,
        primary key (id)
    );

    create table PAYMENTS (
        id int8 not null,
        CODE varchar(255) not null,
        reference varchar(255) not null unique,
        customerName varchar(255) not null,
        AMOUNT float8 not null,
        CURRENCY varchar(3) not null,
        paymentDate date not null,
        VERSION int4 not null,
        fk_facture int8 not null,
        primary key (id)
    );

    create table PLANNING_ANNUEL (
        id int8 not null,
        lastUpdate date,
        year int4 not null,
        primary key (id)
    );

    create table PROFILES (
        id int8 not null,
        VERSION int4 not null,
        name varchar(255),
        fromDate timestamp,
        toDate timestamp,
        active bool not null,
        ordre int4 not null,
        primary key (id)
    );

    create table RIGHTS (
        id int8 not null,
        name varchar(255),
        description varchar(255),
        code varchar(255),
        type varchar(255),
        primary key (id)
    );

    create table TASKS (
        id int8 not null,
        name varchar(255),
        description varchar(255),
        codePrestation varchar(255),
        chargeable bool not null,
        code varchar(255),
        optional bool not null,
        VERSION int4 not null,
        primary key (id)
    );

    create table TIMESHEETS (
        id int8 not null,
        VERSION int4 not null,
        exercise varchar(255),
        createdate date not null,
        updateDate date,
        acceptedDate date,
        rejectedDate date,
        submitDate date,
        validationDate date,
        startDateOfWeek date,
        endDateOfWeek date,
        prixVente float8 not null,
        prixRevient float8 not null,
        coutHoraire float8 not null,
        status varchar(255),
        weekNumber int4 not null,
        fk_employee int8,
        userid int8 not null,
        primary key (id)
    );

    create table TIMESHEET_CELLS (
        id int8 not null,
        VERSION int4 not null,
        dayNumber int4,
        value float4 not null,
        fk_row int8 not null,
        primary key (id)
    );

    create table TIMESHEET_ROWS (
        id int8 not null,
        VERSION int4 not null,
        label varchar(255),
        codePrestation varchar(255),
        custNumber varchar(255),
        assCode varchar(255),
        manCode varchar(255),
        fk_activity int8,
        fk_timesheet int8 not null,
        primary key (id)
    );

    create table TRAININGS (
        id int8 not null,
        TITLE varchar(255) not null,
        description varchar(255) not null,
        startDate timestamp not null,
        endDate timestamp not null,
        companyName varchar(255) not null,
        beneficiaire_id int8,
        primary key (id)
    );

    create table USER_ACTIONS (
        id int8 not null,
        action varchar(255),
        entityClassName varchar(255),
        entityId int8,
        time timestamp,
        fk_employee int8 not null,
        primary key (id)
    );

    alter table ACCESS_RIGHTS 
        add constraint FK541E3112D76EA5E3 
        foreign key (fk_employee) 
        references EMPLOYEES;

    alter table ACCESS_RIGHTS 
        add constraint FK541E311220F5D111 
        foreign key (fk_right) 
        references RIGHTS;

    alter table ACTIVITIES 
        add constraint FK970527ED41091C67 
        foreign key (task_id) 
        references TASKS;

    alter table ACTIVITIES 
        add constraint FK970527ED8B5ED24D 
        foreign key (mission_id) 
        references MISSIONS;

    alter table BUDGETS 
        add constraint FK3846C5AE3E4ADC37 
        foreign key (fk_exercise) 
        references EXERCISES;

    alter table BUDGETS 
        add constraint FK3846C5AE294B3B2E 
        foreign key (fk_manager) 
        references EMPLOYEES;

    alter table BUDGETS 
        add constraint FK3846C5AECD847372 
        foreign key (fk_associe) 
        references EMPLOYEES;

    alter table BUDGETS 
        add constraint FK3846C5AE592FAAB 
        foreign key (fk_contract) 
        references CONTRACTS;

    alter table COMMENTS 
        add constraint FKABDCDF4E279BDBD 
        foreign key (fk_timesheet) 
        references TIMESHEETS;

    alter table COMMENTS 
        add constraint FKABDCDF4D76EA5E3 
        foreign key (fk_employee) 
        references EMPLOYEES;

    alter table CONTACTS 
        add constraint FKCD35053916BD843 
        foreign key (fk_customer) 
        references CUSTOMERS;

    alter table CONTRACTS 
        add constraint FK8E852181916BD843 
        foreign key (fk_customer) 
        references CUSTOMERS;

    alter table CUSTOMERS 
        add constraint FK6268C35294B3B2E 
        foreign key (fk_manager) 
        references EMPLOYEES;

    alter table CUSTOMERS 
        add constraint FK6268C35CD847372 
        foreign key (fk_associe) 
        references EMPLOYEES;

    alter table CUSTOMERS 
        add constraint FK6268C35F486AC93 
        foreign key (fk_origin) 
        references ORIGINS;

    alter table DOCS_INVOICES 
        add constraint FK40477C6AC08E7B27 
        foreign key (ID_DOCUMENT) 
        references DOCUMENTS;

    alter table DOCS_INVOICES 
        add constraint FK40477C6AE4504B49 
        foreign key (ID_INVOICE) 
        references INVOICES;

    alter table DOCS_MISSIONS 
        add constraint FKD26709EB7A20C807 
        foreign key (ID_MISSION) 
        references MISSIONS;

    alter table DOCS_MISSIONS 
        add constraint FKD26709EBC08E7B27 
        foreign key (ID_DOCUMENT) 
        references DOCUMENTS;

    alter table DOCUMENTS 
        add constraint FKDE5562982368FFAE 
        foreign key (OWNER) 
        references EMPLOYEES;

    alter table EMAIL_DATA 
        add constraint FK6D3F1A8D6017A924 
        foreign key (fk_receiver) 
        references EMPLOYEES;

    alter table EMAIL_DATA 
        add constraint FK6D3F1A8D6E71FEA 
        foreign key (fk_sender) 
        references EMPLOYEES;

    alter table EMPLOYEES 
        add constraint FK4351FF65A24E0AD9 
        foreign key (fk_position) 
        references PROFILES;

    alter table EVENTS 
        add constraint FK7A9AD51983C0BEA5 
        foreign key (fk_activity) 
        references ACTIVITIES;

    alter table EVENTS 
        add constraint FK7A9AD519D76EA5E3 
        foreign key (fk_employee) 
        references EMPLOYEES;

    alter table EVENTS 
        add constraint FK7A9AD51941091C67 
        foreign key (TASK_ID) 
        references TASKS;

    alter table EVENT_PLANNING 
        add constraint FK3CEC3782D76EA5E3 
        foreign key (fk_employee) 
        references EMPLOYEES;

    alter table EVENT_PLANNING 
        add constraint FK3CEC37828E341E3C 
        foreign key (fk_planning) 
        references PLANNING_ANNUEL;

    alter table INVOICES 
        add constraint FK549812E6C83F0849 
        foreign key (fk_partner) 
        references EMPLOYEES;

    alter table INVOICES 
        add constraint FK549812E61B8C81A0 
        foreign key (project_id) 
        references MISSIONS;

    alter table INVOICES 
        add constraint FK549812E69246D9CD 
        foreign key (contact_id) 
        references CONTACTS;

    alter table INVOICES 
        add constraint FK549812E6888A4D07 
        foreign key (bank_id) 
        references BANKS;

    alter table INVOICES 
        add constraint FK549812E634D0ABAD 
        foreign key (fk_creator) 
        references EMPLOYEES;

    alter table INVOICE_FEES 
        add constraint FK53B2279F5F680542 
        foreign key (fk_facture) 
        references INVOICES;

    alter table INVOICE_REMINDS 
        add constraint FK4448E4BC5F680542 
        foreign key (fk_facture) 
        references INVOICES;

    alter table INVOICE_REMINDS 
        add constraint FK4448E4BC6E71FEA 
        foreign key (fk_sender) 
        references EMPLOYEES;

    alter table ITEM_EVENT_PLANNING 
        add constraint FK51754A2EA46E07E7 
        foreign key (fk_eventplanning) 
        references EVENT_PLANNING;

    alter table MESSAGE 
        add constraint FK63B68BE723AB9A5A 
        foreign key (to_id) 
        references EMPLOYEES;

    alter table MESSAGE 
        add constraint FK63B68BE7F9A84ECB 
        foreign key (from_id) 
        references EMPLOYEES;

    alter table MESSAGE 
        add constraint FK63B68BE78B5ED24D 
        foreign key (mission_id) 
        references MISSIONS;

    alter table MESSAGE 
        add constraint FK63B68BE758B0CEA 
        foreign key (parent_ID) 
        references MESSAGE;

    alter table MISSIONS 
        add constraint FKE6B7A0677574DF03 
        foreign key (fk_parent) 
        references MISSIONS;

    alter table MISSIONS 
        add constraint FKE6B7A0679BED1B27 
        foreign key (annualBudget_id) 
        references BUDGETS;

    alter table MISSION_COSTS 
        add constraint FK831F01938B5ED24D 
        foreign key (mission_id) 
        references MISSIONS;

    alter table MISSION_COSTS 
        add constraint FK831F01938036AB22 
        foreign key (owner_id) 
        references EMPLOYEES;

    alter table PAYMENTS 
        add constraint FK810FFF2D5F680542 
        foreign key (fk_facture) 
        references INVOICES;

    alter table TIMESHEETS 
        add constraint FK821E8001D76EA5E3 
        foreign key (fk_employee) 
        references EMPLOYEES;

    alter table TIMESHEETS 
        add constraint FK821E8001EB403F21 
        foreign key (userid) 
        references EMPLOYEES;

    alter table TIMESHEET_CELLS 
        add constraint FKA6F12B44472A6CF5 
        foreign key (fk_row) 
        references TIMESHEET_ROWS;

    alter table TIMESHEET_ROWS 
        add constraint FK4FBC2B66E279BDBD 
        foreign key (fk_timesheet) 
        references TIMESHEETS;

    alter table TIMESHEET_ROWS 
        add constraint FK4FBC2B6683C0BEA5 
        foreign key (fk_activity) 
        references ACTIVITIES;

    alter table TRAININGS 
        add constraint FKDC0922B960E76277 
        foreign key (beneficiaire_id) 
        references EMPLOYEES;

    alter table USER_ACTIONS 
        add constraint FK9BF46469D76EA5E3 
        foreign key (fk_employee) 
        references EMPLOYEES;

    create sequence hibernate_sequence;
