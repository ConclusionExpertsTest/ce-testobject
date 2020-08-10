FROM openjdk:11
ADD /users-application/target/ce-testobject-users-application-*-exec.jar ce-testobject-users.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","ce-testobject-users.jar"]
