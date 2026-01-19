alter system set db_recovery_file_dest_size = 10G;
alter system set db_recovery_file_dest = '/opt/oracle/oradata/recovery_area' scope=spfile;
shutdown immediate
startup mount
alter database archivelog;
alter database open;
-- Should now "Database log mode: Archive Mode"
archive log list

SELECT LOG_MODE FROM V$DATABASE;

-- create tabelspace
CREATE TABLESPACE logminer_tbs DATAFILE '/opt/oracle/oradata/ORCLCDB/logminer_tbs.dbf'
    SIZE 25M REUSE AUTOEXTEND ON MAXSIZE UNLIMITED;

-- create user dbzuser in cdb
CREATE USER c##dbzuser IDENTIFIED BY dbz
    DEFAULT TABLESPACE logminer_tbs
    QUOTA UNLIMITED ON logminer_tbs
    CONTAINER=ALL;

-- prove grate permisson to c##dbzuser
GRANT CREATE SESSION TO c##dbzuser CONTAINER=ALL; 
GRANT SET CONTAINER TO c##dbzuser CONTAINER=ALL; 
GRANT SELECT ON V_$DATABASE to c##dbzuser CONTAINER=ALL; 
GRANT FLASHBACK ANY TABLE TO c##dbzuser CONTAINER=ALL; 
GRANT SELECT ANY TABLE TO c##dbzuser CONTAINER=ALL; 
GRANT SELECT_CATALOG_ROLE TO c##dbzuser CONTAINER=ALL; 
GRANT EXECUTE_CATALOG_ROLE TO c##dbzuser CONTAINER=ALL; 
GRANT SELECT ANY TRANSACTION TO c##dbzuser CONTAINER=ALL; 
GRANT LOGMINING TO c##dbzuser CONTAINER=ALL; 

-- provide grant permisson to c##dbzuser for create table
GRANT CREATE TABLE TO c##dbzuser CONTAINER=ALL; 
GRANT LOCK ANY TABLE TO c##dbzuser CONTAINER=ALL; 
GRANT CREATE SEQUENCE TO c##dbzuser CONTAINER=ALL; 

-- provide grant permisson to c##dbzuser for excute
GRANT EXECUTE ON DBMS_LOGMNR TO c##dbzuser CONTAINER=ALL; 
GRANT EXECUTE ON DBMS_LOGMNR_D TO c##dbzuser CONTAINER=ALL; 

GRANT SELECT ON V_$LOG TO c##dbzuser CONTAINER=ALL; 
GRANT SELECT ON V_$LOG_HISTORY TO c##dbzuser CONTAINER=ALL; 
GRANT SELECT ON V_$LOGMNR_LOGS TO c##dbzuser CONTAINER=ALL; 
GRANT SELECT ON V_$LOGMNR_CONTENTS TO c##dbzuser CONTAINER=ALL; 
GRANT SELECT ON V_$LOGMNR_PARAMETERS TO c##dbzuser CONTAINER=ALL; 
GRANT SELECT ON V_$LOGFILE TO c##dbzuser CONTAINER=ALL; 
GRANT SELECT ON V_$ARCHIVED_LOG TO c##dbzuser CONTAINER=ALL; 
GRANT SELECT ON V_$ARCHIVE_DEST_STATUS TO c##dbzuser CONTAINER=ALL; 
GRANT SELECT ON V_$TRANSACTION TO c##dbzuser CONTAINER=ALL; 

GRANT SELECT ON V_$MYSTAT TO c##dbzuser CONTAINER=ALL; 
GRANT SELECT ON V_$STATNAME TO c##dbzuser CONTAINER=ALL;

CREATE TABLE C##DBZUSER.PRODUCTS (
        id NUMBER PRIMARY KEY,
        name VARCHAR2(100),
        description VARCHAR2(500),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

SELECT *
FROM C##DBZUSER.PRODUCTS;

INSERT INTO C##DBZUSER.PRODUCTS (id, name, description) VALUES (
        1,
        'Laptop',
        'High-performance laptop'
    );
COMMIT;

-- switch from CDB to PDB
ALTER SESSION SET CONTAINER = ORCLPDB1;

-- create USER = CORE_BANKING , PASSWORD =  qwer
CREATE USER CORE_BANKING IDENTIFIED BY qwer;
-- provide grant connect to CORE_BACNKING
GRANT CONNECT, RESOURCE TO CORE_BANKING;
-- 
ALTER USER CORE_BANKING QUOTA UNLIMITED ON USERS;
ALTER USER CORE_BANKING QUOTA UNLIMITED ON logminer_tbs;
ALTER USER CORE_BANKING DEFAULT TABLESPACE logminer_tbs;

CREATE TABLE CORE_BANKING.PRODUCTS (
        id NUMBER PRIMARY KEY,
        name VARCHAR2(100),
        description VARCHAR2(500),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
    
SELECT * FROM CORE_BANKING.PRODUCTS;

INSERT INTO CORE_BANKING.PRODUCTS (id, name, description) VALUES (
        1,
        'macBook',
        'High-performance chip'
    );
COMMIT;

SELECT TABLESPACE_NAME, STATUS,CONTENTS
FROM DBA_TABLESPACES;


