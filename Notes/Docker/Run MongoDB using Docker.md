# Running MongoDB using Docker

### 1. Project Structure


```
springboot-mongo-docker/
│
├─ src/
│   ├─ main/
│   │   ├─ java/
│   │   │   └─ com/example/demo/
│   │   │       ├─ DemoApplication.java
│   │   │       ├─ model/
│   │   │       │    └─ User.java
│   │   │       ├─ repository/
│   │   │       │    └─ UserRepository.java
│   │   │       └─ controller/
│   │   │            └─ UserController.java
│   │   └─ resources/
│   │       ├─ application.properties
│   │       └─ static/
│   └─ test/
├─ pom.xml
├─ Dockerfile
└─ docker-compose.yml
```

### 2. Spring Boot Dependencies (pom.xml)


```java
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Data MongoDB -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>

    <!-- Optional: Lombok for cleaner code -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- Testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>

```


### 3. Creating Docker Compose

```yml
version: '3.9'

services:
    mongo:
        image: mongo:7.0
        container_name: mongodb
        ports:
            - "27017:27017"
        environment:
            MONGO_INITDB_ROOT_USERNAME: ${MONGO_USER}
            MONGO_INITDB_ROOT_PASSWORD: ${MONGO_PASS}
            MONGO_INITDB_DATABASE: demo_db
        volumes:
            - mongo-data:/data/db

volumes:
    mongo-data:

```

#### 1. `version`: 
- defines the Compose file format. (syntax version of YAML file, which is important for supporting latest versions of Docker)

#### 2. `services`: 
  - every application component (like mongoDB, Spring Boot, Redis etc) is a service
  - here we have only one service
  - We could later add our entire spring boot application as another service under `services:`

#### 3. `mongo:` 

   - This is the name of the service
   - It also becomes the DNS hostname other containers can use (e.g, `mongodb://mongo:27017`)
   - It is the logical name in Docker Compose commands like `docker-compose logs mongo`
  
#### 4. `image: mongo7.0`

   - This tells docker which image to use
   - `mongo` is the official MongoDB image from DockerHub
   - `7.0` is the version tag (always specify versions in real project)
   - When we first run `docker-compose up`, Docker pulls `mongo:7.0` from Docker Hub if not already present

#### 5. `container_name:mongodb`

   - By default Docker gives random names like `projectname_service_1`
   - `container_name` lets us override that and give it a clean name (here, `mongodb`)
   - so we can run commands like `docker logs mongodb`, `docker exec -it mongodb bash`

#### 6. `ports:`

   - "27017:27017"
   - This is port mapping (host port :  container port)
   - Left side(host) - our machine (localhost)
   - Right side (container) - inside docker
   - Now we can connect using ```mongodb://localhost:27017```

#### 7. environment:

   - This section defines environment variables for the container
   - These configure how MongoDB runs
   - MONGO_INITDB_ROOT_USERNAME: creates a root user with full admin rights
   - MONGO_INITDB_ROOT_PASSWORD: Sets password for that root user
   - MONGO_INITDB_DATABASE: Creates an initial database (demo_db) on first startup
   - These variables are part of MongoDB image's initialization script
   - when the container starts for the first time, MongoDB reads these and sets up users/database accordingly
   - After first run, these variables are ignored unless ew delete the volumne (because MongoDB won't reinitialize an existing db)

#### 8. volumes:

   - `mongo data:/data/db` mounts a docker volume named `mongo-data` into the container's `/data/db` directory
   - `/data/db` is where MongoDB stores its data files
   - Without this, data would live inside the container's file system and be deleted if the container stops or is removed
   - With this volume, data persists across container restarts or rebuilds
   - with this volume, even if we run ```docker-compose down``` and bring it up again, our db still exists

#### 9.  volumes: (at bottom)
    - `mongo-data` This declares the named volume used above


#### 10. How everything works together?

1. You run docker-compose up -d.

2. Docker Compose:
    - Creates a network for your services.
    - Starts MongoDB container from the official image.
    - Passes environment variables to it.
    - Mounts the persistent mongo-data volume.
    - Exposes port 27017 to your machine.

3. Our Spring Boot app (running locally) connects to it at:

```bash
mongodb://root:rootpassword@localhost:27017/demo_db
```

4. Data is stored in the Docker-managed volume, safe even if the container stops.

### 4. Run the container

```bash
docker-compose up -d
```
- Reads the docker compose file
- Creates a custom network
- starts each service(container) defined inside
- Handles volumes, environment variables, and port mapping.
- mongoDB is now running in Docker, data is persisted in ```mongo-data``` volume
- To stop: ```docker-compose down```

### 5. ```application.properties```

```properties
# db uri
spring.data.mongodb.uri=mongodb://${MONGO_USER}:${MONGO_PASS}@localhost:27017/demo_db
# db port
server.port=8080
```

- Store the credentials in Environment (inside RUN CONFIGURATION MENU in vs code)
- store credentials as plain text in key-value pairs separated by comma as follows:
```MONGO_USER=user, MONGO_PASS=pass```
- `demo_db` is the database name
- Spring boot will auto-create collections if they don't exist
- Here MONGO_USER and MONGO_PASS are env vars


### 6. VS Code Tips

1. Docker Extension Panel
   - open the docker extension sidebar
   - we can see the MongoDB container running
   - we can start, stop or view logs from here

2. MongoDB Tools
   - Install MongoDB for VS code extension to explore db from vs code

3. **Always start docker compose first before running the application**




