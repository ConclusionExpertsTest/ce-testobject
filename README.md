# Conclusion Experts - testobject

--------------------------------------------------------------------

## About the application

### Stuff you need to install on your machine:

- JDK 8+ (https://www.oracle.com/nl/java/technologies/javase-downloads.html)
- Maven (https://maven.apache.org/install.html)
- Docker (https://www.docker.com/products/docker-desktop)
- docker-compose (https://docs.docker.com/compose/install/)

### To run the application:

- clone onto your machine and in CLI run:

    - First `cd` into the folder where the project is located, then:
        - ```cd application```
        - ```mvn clean install```
        - ```docker-compose up -d```

- Browse to http://localhost:8082/users/swagger.html for Users API documentation
- Browse to http://localhost:8083/working-conditions/swagger.html for Users API documentation

### To stop the application:

- In CLI run:
    ```docker-compose down```

Happy hacking :-) 

### Extras for the interested:

- docker-compose cheat sheet: https://devhints.io/docker-compose
- mvn (Maven) build lifecycle commands: https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html

--------------------------------------------------------------------

## Contribute

To contribute clone the project and create your own branch based on 'master'. Please do not commit straight into 'master'. If you have something in your feature branch you want merged to 'master', please use a merge request.
