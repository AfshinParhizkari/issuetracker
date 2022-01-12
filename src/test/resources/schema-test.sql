CREATE TABLE developer (
    devname varchar2(10) NOT NULL PRIMARY KEY
);
CREATE TABLE issue (
    issueid INT IDENTITY PRIMARY KEY,
    issuetype INT NOT NULL,/*0=story, 1=bug*/
    title varchar2(45) NOT NULL,
    description varchar2(100),
    creationdate datetime NOT NULL DEFAULT current_timestamp(),
    assignedev varchar2(10),
    foreign key (assignedev) references developer(devname)
);
CREATE TABLE story (
    issueid INT IDENTITY PRIMARY KEY,
    estimatedpoint INT NOT NULL,/*from 1(Low) to 5(High)*/
    status varchar2(10) NOT NULL,/*New,Estimated,Completed*/
    foreign key (issueid) references issue(issueid)
);
CREATE TABLE bug (
    issueid INT IDENTITY PRIMARY KEY,
    priority varchar2(10) NOT NULL,/*Critical,Major,Minor*/
    status varchar2(10) NOT NULL,/*New,Verified,Resolved*/
    foreign key (issueid) references issue(issueid)
);

/*
CREATE USER guest PASSWORD '123' ADMIN ;
GRANT ALTER ANY SCHEMA TO guest;
*/
