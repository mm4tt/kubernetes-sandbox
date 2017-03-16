#!/bin/bash

curl -sSXPOST '127.0.0.1:4200/_sql?pretty' -d@- <<- EOF
{
    "stmt":"CREATE TABLE PulpTable (id STRING PRIMARY KEY, value LONG) "
}
EOF

curl -sSXPOST '127.0.0.1:4200/_sql?pretty' -d@- <<- EOF
{
    "stmt":"INSERT INTO PulpTable(id, value) VALUES ('pulp', 1)"
}
EOF
