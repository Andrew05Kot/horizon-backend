-- creating database
CREATE DATABASE horizon
    WITH
    OWNER = horizon_owner
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- need for using utc timezone in postgre
ALTER DATABASE horizon SET "TimeZone" TO 'utc';
