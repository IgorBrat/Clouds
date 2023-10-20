# Stage 1

FROM maven:3.8.5-eclipse-temurin-17 as stage1

ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

WORKDIR ./

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean install -Dmaven.test.skip=true

# Stage 2

FROM eclipse-temurin:17.0.8.1_1-jre

EXPOSE 8080

WORKDIR ./

COPY --from=stage1 target/app.jar ./app.jar

CMD java -jar ./app.jar