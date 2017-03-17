#!/bin/bash

gcloud container clusters create test-cluster &&

gradle backendJar &&
docker build -f src/main/docker/backend -t gcr.io/docker-sandbox-144501/backend . &&
gcloud docker -- push gcr.io/docker-sandbox-144501/backend &&

gradle frontendJar &&
docker build -f src/main/docker/frontend -t gcr.io/docker-sandbox-144501/frontend . &&
gcloud docker -- push gcr.io/docker-sandbox-144501/frontend &&

kubectl apply -f src/main/kubernetes/crate.yaml &&
kubectl apply -f src/main/kubernetes/app.yaml

echo "Waiting 60s for the jobs to start up..."
sleep 60
echo "Done."
kubectl get services

echo "Starting kubernetes proxy"
# Enable job control.
set -m
kubectl proxy &
echo "Waiting 15s for the proxy..."
sleep 15

echo "Initializing database"
db_endpoint="localhost:8001/api/v1/proxy/namespaces/default/services/crate:web/_sql?pretty"

curl -sSXPOST "$db_endpoint" -d@- <<- EOF
{
    "stmt":"CREATE TABLE PulpTable (id STRING PRIMARY KEY, value LONG) "
}
EOF
curl -sSXPOST "$db_endpoint" -d@- <<- EOF
{
    "stmt":"INSERT INTO PulpTable(id, value) VALUES ('pulp', 1)"
}
EOF
echo "Done."

echo "Ready:"
kubectl get services
echo "Kubernetes dashboard: http://localhost:8001/ui"
echo "Crate dashboard: http://localhost:8001/api/v1/proxy/namespaces/default/services/crate:web"

# Bring back kubectl proxy from background.
fg
