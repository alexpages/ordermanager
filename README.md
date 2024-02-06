## Ordermanager
### Functional Description

This API integrates the Google Maps API to fetch distance data between origin and destination coordinates.

This microservice runs on Docker and sets up two main containers:

- **ordermanager**: Java + Spring Boot application that enables 3 endpoints for order management
- **mysql**: Database related to MySQL 5

Here are some features from the overall configuration:
* Exception handler and custom exceptions
* Data validation
* Thread safety through optimistic locking and versioning
* Following SOLID principles and modularity
* Unit testing and integration testing through JUnit and MockMVC
* Two spring profiles (standalone and docker) are available:
    * *standalone*: It runs on H2 and is for local testing (one shall specify on env variables within your IDE)
    * *docker*: It is used in the start.sh script mentioned later and runs a MySQL DB

### How to run this MS

- First, as an invoker, you need to use your Google Maps API Key:

Replace `YOUR_API_KEY` with your actual Google Maps API Key in the `application.yml` as follows:

```
thirdparties:
  google:
    key: YOUR_API_KEY
```
- Secondly, make sure that Docker Desktop (or similar) is running on your computer; otherwise, the script will fail.


- Lastly, to run this code, you need to run the start.sh:

Open the terminal, navigate to the root of the project, and execute the `start.sh` file. 

```
./start.sh
```