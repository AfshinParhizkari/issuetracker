/*insert developer in table*/
INSERT INTO developer(devid, devname)
VALUES (1, 'andre'),
       (2, 'ehsan'),
       (3, 'hossein'),
       (4, 'afshin');
/*insert story in table*/
INSERT INTO story (issueid, title, description, estimatedpoint, status, assignedev)
VALUES ('727e2463-f682-4389-97d2-f7e852feafce', 'create h2 database', 'please add 3 tables', 5, 'Completed', 4),
       ('396eaf1b-1f11-45bd-9a0f-2107edd3112b', 'insert data in tables', 'please use from doc 1', 5, 'Estimated', 1),
       ('f8fb70a9-8c6a-46f4-a496-4d9f6ffbc258', 'create entity', 'please use pojo and anotations', 5, 'Estimated', 1),
       ('4ec90405-4ad6-404f-86da-b5e1077ece1e', 'create JPA rep.', 'use JPA Interfaces', 5, 'New', null),
       ('03b0aa47-1c81-4dd1-8e87-5ed468cfabfa', 'create Junit test', 'use Junit4', 5, 'New', null),
       ('9f39cb2d-14df-4677-bc90-87d99bfc96af', 'create calc. service1', 'Please provide a GET plan endiont', 5, 'New', null),
       ('2d800ebc-c402-43a9-b984-fd209219570f', 'create calc. service2', 'Please provide a planning algorithm', 5, 'New', null),
       ('aba0dba2-5b69-4f22-bbe9-d715402f0974', 'create calc. service3', 'check total less than dev*10', 5, 'New', null),
       ('275848de-0040-43d2-a617-d47d04205b95', 'create controller ', 'Restfull webservices', 4, 'New', null),
       ('f9b34a9b-8999-4e1d-a266-f9de5613a042', 'test rest services', 'with swager or postman', 4, 'New', null),
       ('4308d381-3104-4513-86f7-cffaddcb010f', 'create ui', 'use JSP ajax JQuery Bootstrap5', 3, 'New', null),
       ('71fa733c-ff7f-445a-a235-09732440ba26', 'test ui', 'user interface test', 3, 'New', null);
/*insert bug in table*/
INSERT INTO bug (issueid, title, description, priority, status, assignedev)
VALUES ('6b24ba48-52cd-4e1f-a2d7-beba1d7f456f', 'h2 is not working well', 'sometimes we enconter: connection lost', 'Critical', 'Resolved', 4),
       ('06f007fb-cc1d-43a5-843f-8484660f71ff', 'h2 port', 'port is assigned to some other app', 'Major', 'Verified', 4),
       ('d534ff04-43c2-429e-b91f-deecf6210c32', 'h2 query', 'data lost in some query', 'Minor', 'New', null);