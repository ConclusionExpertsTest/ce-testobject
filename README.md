# Conclusion Experts - testobject

## Stuff you need to install on your machine:

- JDK 8+ (https://www.oracle.com/nl/java/technologies/javase-downloads.html)
- Maven (https://maven.apache.org/install.html)
- Docker (https://www.docker.com/products/docker-desktop)
- docker-compose (https://docs.docker.com/compose/install/)

## To run the application:

- clone onto your machine and in CLI run:

    - First `cd` into the folder where the project is located, then:
        - ```mvn clean install```
        - ```docker-compose up -d```

- Browse to http://localhost:8082/swagger.html for API documentation

## To stop the application:

- In CLI run:
    ```docker-compose down```

Happy hacking :-) 