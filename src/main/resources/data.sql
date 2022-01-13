/*insert developer in table*/
INSERT INTO developer(devid, devname)
VALUES (1, 'andre'),
       (2, 'ehsan'),
       (3, 'hossein'),
       (4, 'afshin');
/*insert story in table*/
INSERT INTO story (title, description, estimatedpoint, status, assignedev)
VALUES ('create h2 database', 'please add 3 tables', 5, 'Completed', 4),
       ('insert data in tables', 'please use from doc 1', 5, 'Estimated', 1),
       ('create entity', 'please use pojo and anotations', 5, 'Estimated', 1),
       ('create JPA rep.', 'use JPA Interfaces', 5, 'New', null),
       ('create Junit test', 'use Junit4', 5, 'New', null),
       ('create calc. service1', 'Please provide a GET plan endiont', 5, 'New', null),
       ('create calc. service2', 'Please provide a planning algorithm', 5, 'New', null),
       ('create calc. service2', 'check total less than dev*10', 5, 'New', null),
       ('create controller ', 'Restfull webservices', 4, 'New', null),
       ('test rest services', 'with swager or postman', 4, 'New', null),
       ('create ui', 'use JSP ajax JQuery Bootstrap5', 3, 'New', null),
       ('test ui', 'user interface test', 3, 'New', null);
/*insert bug in table*/
INSERT INTO bug (title, description, priority, status, assignedev)
VALUES ('h2 is not working well', 'sometimes we enconter: connection lost', 'Critical', 'Resolved', 4),
       ('h2 port', 'port is assigned to some other app', 'Major', 'Verified', 4),
       ('h2 query', 'data lost in some query', 'Minor', 'New', null);