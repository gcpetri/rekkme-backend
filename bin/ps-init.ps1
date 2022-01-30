cd ../
./mvnw clean package -DskipTests
cp target/rekkme-backend-0.0.1-SNAPSHOT.jar bin/
cd bin
docker-compose down
docker rmi rekkme-backend:latest
docker-compose up