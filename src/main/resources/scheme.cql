CREATE KEYSPACE Timeseries
           WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};

USE Timeseries;

CREATE TABLE user_data (
    id bigint,
    groupId int,
    logDate timestamp,
    data int,
    PRIMARY KEY (id,logDate)
);

//CREATE INDEX logDateIndex ON user_data (logDate);
//CREATE INDEX groupIndex ON user_data (groupId);