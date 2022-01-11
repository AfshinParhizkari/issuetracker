/*insert developer in table*/
INSERT INTO developer(devname) VALUES
('andre'),
('ehsan'),
('hossein'),
('afshin');
/*insert issue in table*/
INSERT INTO issue (issueid,issuetype,title,description,assignedev) VALUES
(1,0,'create h2 database', 'please add 3 tables','afshin'),
(2,0,'insert data in tables','please use from doc 1','hossein'),
(3,0,'create entity','please use pojo and anotations','ehsan'),
(4,0,'create JPA rep.', 'use JPA Interfaces',null),
(5,0,'create Junit test', 'use Junit4',null),
(6,0,'create calc. service1', 'Please provide a GET plan endiont',null),
(7,0,'create calc. service2', 'Please provide a planning algorithm',null),
(8,0,'create calc. service2', 'check total less than dev*10',null),
(9,0,'create controller ', 'Restfull webservices',null),
(10,0,'test rest services', 'with swager or postman',null),
(11,0,'create ui', 'use JSP ajax JQuery Bootstrap5',null),
(12,0,'test ui', 'user interface test',null),
(13,1,'h2 is not working well', 'sometimes we enconter: connection lost','afshin'),
(14,1,'h2 port', 'port is assigned to some other app','afshin'),
(15,1,'h2 query', 'data lost in some query',null );
/*insert story in table*/
INSERT INTO story (issueid,estimatedpoint,status) VALUES
(1,5, 'Completed'),
(2,5, 'Estimated'),
(3,5, 'Estimated'),
(4,5, 'New'),
(5,5, 'New'),
(6,4, 'New'),
(7,3, 'New'),
(8,2, 'New'),
(9,2, 'New'),
(10,1, 'New'),
(11,1, 'New'),
(12,1, 'New');
/*insert bug in table*/
INSERT INTO bug (issueid,priority,status) VALUES
(13,'Critical','Resolved'),
(14,'Major','Verified'),
(15,'Minor','New');