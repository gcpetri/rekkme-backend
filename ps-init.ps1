./mvnw clean package -DskipTests
docker-compose down
docker rmi rekkme-backend:latest
docker-compose up