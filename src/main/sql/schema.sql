CREATE TABLE PulpTable (
  id STRING PRIMARY KEY,
  value LONG
) WITH (number_of_replicas = '0-2');

insert into PulpTable(id, value) values ('pulp', 10);
