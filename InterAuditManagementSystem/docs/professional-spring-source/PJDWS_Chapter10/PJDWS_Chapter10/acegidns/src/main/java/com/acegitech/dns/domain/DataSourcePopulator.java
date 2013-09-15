/* Copyright 2004 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.acegitech.dns.domain;

import org.springframework.beans.factory.InitializingBean;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


/**
 * Populates the Contacts in-memory database with contact and ACL information.
 *
 * @author Ben Alex
 * @version $Id: DataSourcePopulator.java,v 1.1 2004/11/18 06:55:19 administrator Exp $
 */
public class DataSourcePopulator implements InitializingBean {
    //~ Instance fields ========================================================

    private DataSource dataSource;

    //~ Methods ================================================================

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void afterPropertiesSet() throws Exception {
        if (dataSource == null) {
            throw new IllegalArgumentException("dataSource required");
        }

        JdbcTemplate template = new JdbcTemplate(dataSource);

        template.execute(
            "CREATE TABLE DOMAIN(ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100)  NOT NULL PRIMARY KEY,PARENT_DOMAIN_ID INTEGER,FULL_NAME VARCHAR_IGNORECASE(63) NOT NULL,CONSTRAINT SYS_FK_88 FOREIGN KEY(PARENT_DOMAIN_ID) REFERENCES DOMAIN(ID) ON DELETE CASCADE,CONSTRAINT SYS_CT_89 UNIQUE(FULL_NAME))");
        template.execute(
            "CREATE TABLE SOA(ID INTEGER NOT NULL PRIMARY KEY,ORIGIN VARCHAR_IGNORECASE(255) NOT NULL,NS VARCHAR_IGNORECASE(255) NOT NULL,MBOX VARCHAR_IGNORECASE(255) NOT NULL,SERIAL INTEGER DEFAULT 1 NOT NULL,REFRESH INTEGER DEFAULT 28800 NOT NULL,RETRY INTEGER DEFAULT 7200 NOT NULL,EXPIRE INTEGER DEFAULT 604800 NOT NULL,MINIMUM INTEGER DEFAULT 86400 NOT NULL,TTL INTEGER DEFAULT 86400 NOT NULL,CONSTRAINT SYS_FK_93 FOREIGN KEY(ID) REFERENCES DOMAIN(ID) ON DELETE CASCADE,CONSTRAINT SYS_CT_94 UNIQUE(ORIGIN))");
        template.execute(
            "CREATE TABLE RR(ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 0)  NOT NULL PRIMARY KEY,ZONE INTEGER NOT NULL,NAME VARCHAR_IGNORECASE(64) NOT NULL,TYPE VARCHAR_IGNORECASE(5) NOT NULL,DATA VARCHAR_IGNORECASE(128) NOT NULL,AUX INTEGER DEFAULT 0 NOT NULL,TTL INTEGER DEFAULT 86400 NOT NULL,CONSTRAINT SYS_CT_98 UNIQUE(ZONE,NAME,TYPE,DATA),CONSTRAINT SYS_FK_99 FOREIGN KEY(ZONE) REFERENCES SOA(ID) ON DELETE CASCADE,CONSTRAINT SYS_CT_100 CHECK(((((((((((RR.TYPE='A') OR (RR.TYPE='AAAA')) OR (RR.TYPE='ALIAS')) OR (RR.TYPE='CNAME')) OR (RR.TYPE='HINFO')) OR (RR.TYPE='MX')) OR (RR.TYPE='NS')) OR (RR.TYPE='PTR')) OR (RR.TYPE='RP')) OR (RR.TYPE='SRV')) OR (RR.TYPE='TXT')))");
        template.execute(
            "CREATE TABLE ACL_OBJECT_IDENTITY(ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100)  NOT NULL PRIMARY KEY,OBJECT_IDENTITY VARCHAR_IGNORECASE(250) NOT NULL,PARENT_OBJECT INTEGER,ACL_CLASS VARCHAR_IGNORECASE(250) NOT NULL,CONSTRAINT UNIQUE_OBJECT_IDENTITY UNIQUE(OBJECT_IDENTITY),CONSTRAINT SYS_FK_3 FOREIGN KEY(PARENT_OBJECT) REFERENCES ACL_OBJECT_IDENTITY(ID))");
        template.execute(
            "CREATE TABLE ACL_PERMISSION(ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100)  NOT NULL PRIMARY KEY,ACL_OBJECT_IDENTITY INTEGER NOT NULL,RECIPIENT VARCHAR_IGNORECASE(100) NOT NULL,MASK INTEGER NOT NULL,CONSTRAINT UNIQUE_RECIPIENT UNIQUE(ACL_OBJECT_IDENTITY,RECIPIENT),CONSTRAINT SYS_FK_7 FOREIGN KEY(ACL_OBJECT_IDENTITY) REFERENCES ACL_OBJECT_IDENTITY(ID))");
        template.execute(
            "CREATE TABLE USERS(USERNAME VARCHAR_IGNORECASE(50) NOT NULL PRIMARY KEY,PASSWORD VARCHAR_IGNORECASE(50) NOT NULL,ENABLED BOOLEAN NOT NULL);");
        template.execute(
            "CREATE TABLE AUTHORITIES(USERNAME VARCHAR_IGNORECASE(50) NOT NULL,AUTHORITY VARCHAR_IGNORECASE(50) NOT NULL,CONSTRAINT FK_AUTHORITIES_USERS FOREIGN KEY(USERNAME) REFERENCES USERS(USERNAME));");
        template.execute(
            "CREATE UNIQUE INDEX IX_AUTH_USERNAME ON AUTHORITIES(USERNAME,AUTHORITY);");

        template.execute(
            "insert into domain (id, parent_domain_id, full_name) values (2, null, 'petes.com.zz.');");
        template.execute(
            "insert into domain (id, parent_domain_id, full_name) values (3, null, 'scotty.com.zz.');");
        template.execute(
            "insert into domain (id, parent_domain_id, full_name) values (4, null, 'jackpot.com.zz.');");
        template.execute(
            "insert into domain (id, parent_domain_id, full_name) values (6, null, 'zoo-uni.edu.zz.')");
        template.execute(
            "insert into domain (id, parent_domain_id, full_name) values (7, null, 'tafe.edu.zz.')");
        template.execute(
            "insert into domain (id, parent_domain_id, full_name) values (8, null, 'zoohigh.edu.zz.')");
        template.execute(
            "insert into domain (id, parent_domain_id, full_name) values (9, 6, 'science.zoo-uni.edu.zz.')");
        template.execute(
            "insert into domain (id, parent_domain_id, full_name) values (10, 6, 'arts.zoo-uni.edu.zz.')");
        template.execute(
            "insert into domain (id, parent_domain_id, full_name) values (11, 9, 'compsci.science.zoo-uni.edu.zz.')");

        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (2, 'com.acegitech.dns.domain.Domain:2', null, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (3, 'com.acegitech.dns.domain.Domain:3', null, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (4, 'com.acegitech.dns.domain.Domain:4', null, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (6, 'com.acegitech.dns.domain.Domain:6', null, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (7, 'com.acegitech.dns.domain.Domain:7', null, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (8, 'com.acegitech.dns.domain.Domain:8', null, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (9, 'com.acegitech.dns.domain.Domain:9', 6, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (10, 'com.acegitech.dns.domain.Domain:10', 6, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (11, 'com.acegitech.dns.domain.Domain:11', 9, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");

        template.execute(
            "insert into soa(id, origin, ns, mbox) values (2, 'petes.com.zz.', 'ns1.acegisecurity.net.au', 'mailbox.acegisecurity.net.au')");
        template.execute(
            "insert into soa(id, origin, ns, mbox) values (3, 'scotty.com.zz.', 'ns1.acegisecurity.net.au', 'mailbox.acegisecurity.net.au')");
        template.execute(
            "insert into soa(id, origin, ns, mbox) values (4, 'jackpot.com.zz.', 'ns1.acegisecurity.net.au', 'mailbox.acegisecurity.net.au')");
        template.execute(
            "insert into soa(id, origin, ns, mbox) values (6, 'zoo-uni.edu.zz.', 'ns1.acegisecurity.net.au', 'mailbox.acegisecurity.net.au')");
        template.execute(
            "insert into soa(id, origin, ns, mbox) values (7, 'tafe.edu.zz.', 'ns1.acegisecurity.net.au', 'mailbox.acegisecurity.net.au')");
        template.execute(
            "insert into soa(id, origin, ns, mbox) values (8, 'zoohigh.edu.zz.', 'ns1.acegisecurity.net.au', 'mailbox.acegisecurity.net.au')");
        template.execute(
            "insert into soa(id, origin, ns, mbox) values (9, 'science.zoo-uni.edu.zz.', 'ns1.acegisecurity.net.au', 'mailbox.acegisecurity.net.au')");
        template.execute(
            "insert into soa(id, origin, ns, mbox) values (10, 'arts.zoo-uni.edu.zz.', 'ns1.acegisecurity.net.au', 'mailbox.acegisecurity.net.au')");
        template.execute(
            "insert into soa(id, origin, ns, mbox) values (11, 'compsci.science.zoo-uni.edu.zz.', 'ns1.acegisecurity.net.au', 'mailbox.acegisecurity.net.au')");

        template.execute(
            "insert into rr (id, zone, name, type, data) values (2, 2, 'www', 'A', '192.168.0.1');");
        template.execute(
            "insert into rr (id, zone, name, type, data) values (3, 3, 'www', 'A', '192.168.0.2');");
        template.execute(
            "insert into rr (id, zone, name, type, data) values (4, 4, 'www', 'A', '192.168.0.3');");
        template.execute(
            "insert into rr (id, zone, name, type, data) values (6, 6, 'www', 'A', '10.1.0.1');");
        template.execute(
            "insert into rr (id, zone, name, type, data) values (7, 7, 'www', 'A', '192.168.0.5');");
        template.execute(
            "insert into rr (id, zone, name, type, data) values (8, 8, 'www', 'A', '192.168.0.6');");
        template.execute(
            "insert into rr (id, zone, name, type, data) values (9, 9, 'www', 'A', '10.1.12.4');");
        template.execute(
            "insert into rr (id, zone, name, type, data) values (10, 10, 'www', 'A', '10.1.43.65');");
        template.execute(
            "insert into rr (id, zone, name, type, data) values (11, 11, 'www', 'A', '10.1.12.87');");
        template.execute(
            "insert into rr (id, zone, name, type, data) values (12, 11, 'ssl', 'A', '10.1.12.88');");

        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (null, 'com.acegitech.dns.domain.ResourceRecord:2', 2, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (null, 'com.acegitech.dns.domain.ResourceRecord:3', 3, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (null, 'com.acegitech.dns.domain.ResourceRecord:4', 4, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (null, 'com.acegitech.dns.domain.ResourceRecord:6', 6, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (null, 'com.acegitech.dns.domain.ResourceRecord:7', 7, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (null, 'com.acegitech.dns.domain.ResourceRecord:8', 8, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (null, 'com.acegitech.dns.domain.ResourceRecord:9', 9, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (null, 'com.acegitech.dns.domain.ResourceRecord:10', 10, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (null, 'com.acegitech.dns.domain.ResourceRecord:11', 11, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");
        template.execute(
            "insert into acl_object_identity (id, object_identity, parent_object, acl_class) VALUES (null, 'com.acegitech.dns.domain.ResourceRecord:12', 11, 'net.sf.acegisecurity.acl.basic.SimpleAclEntry')");

        /*
           Passwords encoded using MD5, NOT in Base64 format, with null as salt
           Encoded password for marissa is "koala"
           Encoded password for dianne is "emu"
           Encoded password for scott is "wombat"
           Encoded password for peter is "opal" (but user is disabled)
         */
        template.execute(
            "INSERT INTO USERS VALUES('marissa','a564de63c2d0da68cf47586ee05984d7',TRUE);");
        template.execute(
            "INSERT INTO USERS VALUES('dianne','65d15fe9156f9c4bbffd98085992a44e',TRUE);");
        template.execute(
            "INSERT INTO USERS VALUES('scott','2b58af6dddbd072ed27ffc86725d7d3a',TRUE);");
        template.execute(
            "INSERT INTO USERS VALUES('peter','22b5c9accc6e1ba628cedc63a72d57f8',TRUE);");
        template.execute(
            "INSERT INTO AUTHORITIES VALUES('marissa','ROLE_USER');");
        template.execute(
            "INSERT INTO AUTHORITIES VALUES('marissa','ROLE_STAFF');");
        template.execute(
            "INSERT INTO AUTHORITIES VALUES('marissa','ROLE_SUPERVISOR')");
        template.execute("INSERT INTO AUTHORITIES VALUES('dianne','ROLE_USER')");
        template.execute(
            "INSERT INTO AUTHORITIES VALUES('dianne','ROLE_STAFF');");
        template.execute("INSERT INTO AUTHORITIES VALUES('scott','ROLE_USER')");
        template.execute("INSERT INTO AUTHORITIES VALUES('peter','ROLE_USER')");

        template.execute(
            "insert into acl_permission (id, acl_object_identity, recipient, mask) VALUES (null, 2, 'ROLE_STAFF', 1)"); // admin
        template.execute(
            "insert into acl_permission (id, acl_object_identity, recipient, mask) VALUES (null, 3, 'ROLE_STAFF', 0)"); // no permissions
        template.execute(
            "insert into acl_permission (id, acl_object_identity, recipient, mask) VALUES (null, 4, 'ROLE_STAFF', 1)"); // admin
        template.execute(
            "insert into acl_permission (id, acl_object_identity, recipient, mask) VALUES (null, 6, 'ROLE_STAFF', 1)"); // admin
        template.execute(
            "insert into acl_permission (id, acl_object_identity, recipient, mask) VALUES (null, 7, 'ROLE_STAFF', 1)"); // admin
        template.execute(
            "insert into acl_permission (id, acl_object_identity, recipient, mask) VALUES (null, 8, 'ROLE_STAFF', 1)"); // admin

        // Grant users access to related domains
        template.execute(
            "insert into acl_permission (id, acl_object_identity, recipient, mask) VALUES (null, 2, 'peter', 1)"); // admin
        template.execute(
            "insert into acl_permission (id, acl_object_identity, recipient, mask) VALUES (null, 3, 'scott', 1)"); // admin
        template.execute(
            "insert into acl_permission (id, acl_object_identity, recipient, mask) VALUES (null, 9, 'peter', 30)"); // read+write+create+delete
        template.execute(
            "insert into acl_permission (id, acl_object_identity, recipient, mask) VALUES (null, 7, 'scott', 6)"); // read+write
    }
}
