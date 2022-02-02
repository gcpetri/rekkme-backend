#!/bin/bash

./mvnw clean package -DskipTests
docker-compose down
docker rmi bulletin:latest
docker-compose up