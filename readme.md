## Pre-requisite
- docker
- java 8
- maven (I use 3.9.0 but you can try with any)
## Build
    mvn clean install package -Dspring.profiles.active=test
## Run
    docker compose up -d
    java -jar target/redisandkafka-0.0.1-SNAPSHOT.jar
## URL
- http://localhost:8080/hello/gokul - URL to populate redis cache and it works fine.
- http://localhost:8080/send - This will emit a sample event to kafka and this will result in an error in serialization.


