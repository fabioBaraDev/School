# School App code challenge

* That is a Maven project developed with Java 11, H2 Database and Docker
* This project has been developed with Domain concepts
* To compile the project or run the unit test, you must run the MVN commands like clean and install, <br>
  however you can find the last compilation in School-0.0.1-SNAPSHOT.jar at target folder
* To run this project with Docker, you must run the following command at terminal (Linux command), be certain that port 8080 at localhost is available and Docker is running properly:

``` 
sudo docker-compose -f docker-compose.yaml up -d
```
 * This project loads some student and course data automatically during the startup, you can verify this data at the resources folder
 * To test this project or check the Rest documentation, you must access the Swagger URI:
 
 ```
http://localhost:8080/swagger-ui/index.html
 ```
 
 
 * To check the data directly on database, you must access the link below with user `sa` and password `password`
 
 ```
http://localhost:8080/h2-console/login.jsp
 ```
 
 Thanks and have fun!<br>
 :)