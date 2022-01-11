DROP TABLE IF EXISTS developer;
CREATE TABLE developer (
    name varchar2(10) IDENTITY PRIMARY KEY
);
DROP TABLE IF EXISTS issue;
CREATE TABLE issue (
    issueID INT AUTO_INCREMENT IDENTITY PRIMARY KEY,
    title varchar2(20) NOT NULL,
    description varchar2(50),
    creation_date datetime NOT NULL DEFAULT current_timestamp(),
    assigned_developer varchar2(10) NOT NULL,
    foreign key (assigned_developer) references developer(name)
);
DROP TABLE IF EXISTS story;
CREATE TABLE story (
    issueID INT AUTO_INCREMENT IDENTITY PRIMARY KEY,
    estimated_point_value INT NOT NULL,/*from 1(Low) to 5(High)*/
    status varchar2(10) NOT NULL,/*New,Estimated,Completed*/
    foreign key (issueID) references issue(issueID)
);
DROP TABLE IF EXISTS bug;
CREATE TABLE bug (
    issueID INT AUTO_INCREMENT IDENTITY PRIMARY KEY,
    priority varchar2(10) NOT NULL,/*Critical,Major,Minor*/
    status varchar2(10) NOT NULL,/*New,Verified,Resolved*/
    foreign key (issueID) references issue(issueID)
);

/*
CREATE USER guest PASSWORD '123' ADMIN ;
GRANT ALTER ANY SCHEMA TO guest;
*/
