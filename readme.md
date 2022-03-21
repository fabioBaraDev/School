# School App code challenge

* That is a Maven project developed with Java 11, Spring, MySQL, H2 Database and Docker
* This project has been developed with Domain concepts
* In order to have a good unit tests, this project uses H2 Database to run Services tests 
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
 
 
 * To check the data directly on database (with the docker running properly), you must access `127.0.0.1:3306` with user `root` and password `root`, I suggest DBeaver to access the database `school_database` 
 
* Click [here](https://dbeaver.io/download/) to download DBeaver
 
 Thanks and have fun!<br>
 :)