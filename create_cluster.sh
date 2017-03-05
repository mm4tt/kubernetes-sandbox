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
