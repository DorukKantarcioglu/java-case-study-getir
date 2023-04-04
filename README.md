# ReadingIsGood
Case study for the Java Developer position.

### Design
The project is a closed layered web application composed of controllers, services, repositories and entities. Each layer can only interact with their layer or the layer below them. API design is meant to be robust and conforming to the REST standarts. DTO design pattern is utilized via Request and Response objects. Idempotency in GET and PUT methods are maintained. Every controller request aside from login requests are to be accompanied by Authorization header, with a Bearer token. Paging in order requests is present due to the possible load of bulk processing.

### Tech stack
* JDK 11
* H2 Database
* Spring Boot
* Spring Security
* JPA
* Hibernate
* JDBC
* JWT
* JUnit
* Mockito
* Jakarta Bean Validation
* OpenAPI Specification
* Swagger

### How to start the project
If you want to start locally or in an IDE, use the following commands:
```
mvn clean package
java -jar target\reading-is-good-1.0.0.jar
```
If you want to start on a Docker container, use the following commands:
```
docker build -t reading-is-good:1.0 .
docker run -d -p 8080:8080 -t reading-is-good:1.0
```
The application will run in port 8080. To access the H2 database, use <localhost:8080/h2-console>. To access the API documentation via Swagger, use <localhost:8080/swagger-ui.html>

### Credentials
H2 database credentials:
```
JDBC URL: jdbc:h2:mem:db
username: getir
password: getir
```

User credentials to obtain Bearer token via <localhost:8080/api/v1/authentication/login>
```
username: getir
password: getir
```

### Postman requests
Postman requests are available in the folder "postman" as a collection.
