## Ordermanager
This README is organized into the following sections:

- [Functional Description](#functional-description)
- [How to Run this MS](#how-to-run-this-ms)
- [EC2 instance on AWS](#ec2-instance-on-aws)
- [Note for Mac M1 users](#note-for-mac-m1-users)

Each section provides information to help understand and use the microservice correctly.

### Functional Description

**Ordermanager** microservice manages CRUD operations for orders, auditing changes and it handles user registration, authentication, ending up with a total of 11 endpoints available via two interfaces. It integrates Spring Boot for the business layer and Spring Security for security. Refer to the Swagger contract for endpoint specifics. It utilizes the Google Maps API to fetch distance data, estimate times, and offer clear directions for order creation.

Here are some features from the overall configuration:

* Data validation
* Exception handling with custom exceptions through Controller Advice
* Thread safety ensured via optimistic locking and versioning
* API-first design facilitated by OpenAPI contract and OpenAPIGenerator Maven plugin for both domain and API layers
* Domain-Driven Design (DDD) project structure
* Adherence to SOLID principles and modularity
* Unit testing and integration testing implemented using JUnit and MockMVC
* Two spring profiles (standalone and docker) are available:
    * *standalone*: It runs on H2 and is for local testing (one shall specify on env variables within your IDE)
    * *docker*: It is used when the project is run with `docker-compose up -d` command script mentioned later and runs a MySQL DB

### How to run this MS

- First, clone this repository to your localhost.

- Secondly, as an invoker, you need to use your Google Maps API Key or use the provided one (which is for testing purpose). Replace both `keys` if a non-test key is preferred. These variables are located within the `application.yml` file:

    ```yaml
    thirdparties:
      google:
        key: "${GOOGLE_API_KEY:AIzaSyDFW_s8pzd3mG1OKTsZkLeKec0aYh5zVEw}"
      jwt:
        key: "${JWT_KEY:javax.crypto.spec.SecretKeySpec@5881a62}"
    ```

- Lastly, to run this code, you need to either execute it in your IDE as a Spring Boot application or run it via Docker.
  
    - **IDE** is preferred:
       - Ensure your IDE has Lombok and Spring Boot installed
       - Make sure to compile the project first by creating a Maven configuration with the commands `clean install`
       - Then execute the application by running it as a Spring Boot Application
    - **Docker** is preferred:
        - First, make sure that Docker Desktop (or similar) is running on your computer; otherwise, the execution will fail
        - Then, navigate to the root of the project in the terminal and execute the following command:
        
        ```
        docker-compose up -d
        ```
        
- Note: Postman files (environments and collection) have been provided to ease the testing and are located in the following folder `/ordermanager/src/main/resources/postman`. Make sure to change `http://<<your_ip>>:<<port>>` from AWS environment to `http://54.155.41.99:8080`.


### EC2 instance on AWS

An AWS EC2 instance is configured with Docker and Docker Compose for a multi-container setup, exposing port 8080 to communicate with the microservice running. This microservice uses a Jenkinsfile for Continuous Integration (CI) and Continuous Deployment (CD) with Docker on EC2. 


In addition to being able to run the application locally as specified in the [How to Run this MS](#how-to-run-this-ms) section, you can also interact with the service using Postman by targeting the IP address and port available: [http://54.155.41.99:8080](http://54.155.41.99:8080).
### Note for Mac M1 Users

**Important:** This project assumes Docker runs on a Windows operating system. However, due to compatibility issues between certain JDK versions and Mac M1 devices, as well as limitations of docker-compose.yml in supporting multiplatform image building at runtime, please note the following:

The Spring Boot application image referenced in the docker-compose.yml file is built on a Windows OS. This could lead to compatibility issues for Mac M1 users. To address this, it's necessary to use the Dockerfile for image creation instead of pulling an existing one. Follow these steps to modify the `docker-compose.yml` file accordingly:

```
      build:
         context: .
         dockerfile: Dockerfile
      platform: linux/amd64      #Specifies platform
```

Additionally, it is necessary to remove the `image: alexintelc/ordermanager:latest` line from the `docker-compose.yml` file. This ensures that users create an image at runtime adapted to macOS instead of pulling a Windows image.

Note: While specifying the platform and pulling the image from Docker Hub with the `image: alexintelc/ordermanager:latest` line might work, compatibility is not ensured. Therefore, it is suggested to follow the previous steps.
