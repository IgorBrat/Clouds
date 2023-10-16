#Stage 1
# initialize build and set base image for first stage
FROM maven:3.8.5-eclipse-temurin-17 as stage1
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
# set working directory
WORKDIR ./
# copy just pom.xml
COPY pom.xml .
# go-offline using the pom.xml
RUN mvn dependency:go-offline
# copy your other files
COPY ./src ./src
# compile the source code and package it in a jar file
RUN mvn clean install -Dmaven.test.skip=true
#Stage 2
# set base image for second stage
FROM eclipse-temurin:17.0.8.1_1-jre
# set deployment directory
WORKDIR ./
# copy over the built artifact from the maven image
COPY --from=stage1 ./target/app.jar ./app.jar

# RUN
# docker run --name demo_container -p 8080:8080 demo_image:2 java -jar /opt/demo/demo.jar