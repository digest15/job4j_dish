FROM maven:3.6.3-openjdk-17 as maven
RUN mkdir app
WORKDIR app
COPY . .
RUN mvn package -Dmaven.test.skip=true
CMD ["mvn", "liquibase:update", "-Pdocker"]

FROM openjdk:17.0.2-jdk
RUN mkdir app
WORKDIR app
COPY --from=maven /app/target/job4j_dish.jar job4j_dish.jar
CMD ["java", "-jar", "job4j_dish.jar"]