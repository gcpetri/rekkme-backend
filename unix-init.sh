#!/bin/bash

./mvnw clean package -DskipTests
cp target/bulletin-0.0.1-SNAPSHOT.jar .
docker-compose down
docker rmi bulletin:latest
docker-compose up