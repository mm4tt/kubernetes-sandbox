#!/bin/bash

tag=`date +%Y%m%d%H%M%S`

gradle backendJar &&
docker build -f src/main/docker/backend -t gcr.io/docker-sandbox-144501/backend -t gcr.io/docker-sandbox-144501/backend:$tag . &&
gcloud docker -- push gcr.io/docker-sandbox-144501/backend:$tag &&
kubectl set image deployment/backend backend=gcr.io/docker-sandbox-144501/backend:$tag


gradle frontendJar &&
docker build -f src/main/docker/frontend -t gcr.io/docker-sandbox-144501/frontend -t gcr.io/docker-sandbox-144501/frontend:$tag . &&
gcloud docker -- push gcr.io/docker-sandbox-144501/frontend:$tag &&
kubectl set image deployment/frontend frontend=gcr.io/docker-sandbox-144501/frontend:$tag
