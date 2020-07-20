FROM openjdk:8
ADD /application/target/ce-testobject-application-0.0.1-SNAPSHOT.jar ce-testobject-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","ce-testobject-0.0.1-SNAPSHOT.jar"]
