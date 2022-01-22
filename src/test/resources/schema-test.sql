DROP TABLE IF EXISTS developer;
CREATE TABLE developer (
                           devid INT NOT NULL IDENTITY PRIMARY KEY,
                           devname varchar2(15) NOT NULL
);
DROP TABLE IF EXISTS story;
CREATE TABLE story (
                       issueid varchar2(36) PRIMARY KEY,
                       title varchar2(45) NOT NULL,
                       description varchar2(100),
                       creationdate datetime NOT NULL DEFAULT current_timestamp(),
                       estimatedpoint INT NOT NULL,/*from 1(Low) to 5(High)*/
                       status varchar2(10) NOT NULL,/*New,Estimated,Completed*/
                       assignedev INT,
                       sprint INT,
                       foreign key (assignedev) references developer(devid)
);
DROP TABLE IF EXISTS bug;
CREATE TABLE bug (
                     issueid varchar2(36) PRIMARY KEY,
                     title varchar2(45) NOT NULL,
                     description varchar2(100),
                     creationdate datetime NOT NULL DEFAULT current_timestamp(),
                     priority varchar2(10) NOT NULL,/*Critical,Major,Minor*/
                     status varchar2(10) NOT NULL,/*New,Verified,Resolved*/
                     assignedev INT,
                     foreign key (assignedev) references developer(devid)
);
/*
CREATE USER guest PASSWORD '123' ADMIN ;
GRANT ALTER ANY SCHEMA TO guest;
*/