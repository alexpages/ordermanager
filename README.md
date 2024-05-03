## Ordermanager
### Functional Description

**Ordermanager** is an API that manages CRUD operations for orders, auditing the modifications made to them. It also facilitates user registration and authentication for accessing the endpoints. This microservice provides a total of 11 endpoints across two interfaces, each implemented by a separate controller within the same microservice. The user interface is integrated with Spring Security, while the order interface is linked to Spring Boot and handles communication with the database

This API integrates the Google Maps API to fetch distance data between origin and destination coordinates. Also it calculate time and provides human readable directions to be inserted on the orders to be created.

This microservice can be run on local environment or on docker:
- Local: Ensure your IDE has Spring Boot installed, do `mvn clean install` and run the project as a Spring Boot Application.
- Docker: Ensure docker daemon is running on your computer, open a terminal session on the docker-compose.yml file location (located in the root of the project) and execute `docker-compose up -d`. This microservice runs on Docker and sets up two main containers:
    - **ordermanager**: Java + Spring Boot application that enables 11 endpoints for order management and user management through two different interfaces. Please refer to the swagger contract to visualize the endpoints and the expected request body.   
    - **mysql**: Database related to MySQL 5

Here are some features from the overall configuration:

* Exception handling with custom exceptions through Controller Advice
* Data validation
* Thread safety ensured via optimistic locking and versioning
* API-first design facilitated by OpenAPI contract and OpenAPI Generator for both domain and API layers
* Domain-Driven Design (DDD) project structure
* Adherence to SOLID principles and modularity
* Unit testing and integration testing implemented using JUnit and MockMVC
* Two spring profiles (standalone and docker) are available:
    * *standalone*: It runs on H2 and is for local testing (one shall specify on env variables within your IDE)
    * *docker*: It is used when the project is run with `docker-compose up -d` command script mentioned later and runs a MySQL DB

### How to run this MS

- First, as an invoker, you need to use your Google Maps API Key or use the provided one (which is for testing purpose). Replace both `keys` if a non test key is preferred. These variables are located within `application.yml` file:

```
thirdparties:
  google:
    key: "${GOOGLE_API_KEY:AIzaSyDFW_s8pzd3mG1OKTsZkLeKec0aYh5zVEw}"
  jwt:
    key: "${JWT_KEY:javax.crypto.spec.SecretKeySpec@5881a62}"
```

- Secondly, make sure that Docker Desktop (or similar) is running on your computer; otherwise, the script will fail.


- Lastly, to run this code, you need to run the start.sh:

Open the terminal, navigate to the root of the project, and execute the `start.sh` file. 

```
./start.sh
```

### Note for Mac M1 users
NOTE: The project is designed with the assumption that Docker runs on a Windows operating system. Due to compatibility issues between certain versions of JDK and Mac M1 devices, and the limitation of docker-compose.yml in supporting multiplatform image building at runtime, it is necessary to include the following information:

Considering that the image pulled for the Spring Boot application in the docker-compose.yml file is generated from a Windows OS, it is necessary to utilize the Dockerfile provided in the root of the project and define the compilation method on the ordermanager container in the docker-compose.yml file. This involves adding a specific line in the docker-compose.yml file to specify the platform, enabling users to run the microservice on Mac M1 devices.

```
      build:
         context: .
         dockerfile: Dockerfile
         platform: linux/amd64      #Specifies platform
      ports:
```

Additionally, it is necessary to remove the `image: alexintelc/ordermanager:latest` line. By doing this, users will create an image at runtime adapted to the macOS instead of pulling a Windows image.
