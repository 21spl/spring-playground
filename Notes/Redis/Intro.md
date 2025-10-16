# Agenda

1. How to create and configure our spring boot application to use Redis?
2. How to add a docker compose file as a Git submodule our our application to configure and run Redis
3. How to add a Git Submodule with application's sample raw data
4. How to luanch a redis instance ,]

# 1, Creating a skeleton Spring Boot application from scratch

1. Create a project using Spring Initializr
2. Project metadata:
   - Group: com.redislabs.edu
   - Artifact: redi2read
   - Name: redi2read
   - packaging: JAR
   - Java: 11

3. Dependencies:
    - spring web
    - spring Data Redis
    - Spring Security
    - Lombok
    - Spring Boot DevTools
  
4. We can now add our project to git
  
# 2. Adding redismod docker compose Git submodule

1. We need to configure a Redis instance for our application
2. There is a docker compose file already created in a git repository, that we can add as a submodule in our application's git repository
3. This docker compose.yml file is in redismod-docker-compose repo
4. This docker compose file has configurations to user Redis redismod image
5. This redismod image includes Redis with built in support for JSON, Search, Graph, TimeSeries and even probabilistic data structures


```bash
git submodule add git@github.com:redis-developer/redismod-docker-compose.git docker
```


# 3. Adding sample data as git submodule

- The sample data for the application is also provided as a separate  Git repo, what we can add to our application repo
- The raw data consists of a collection of JSON documents, representing books and users
  
### Books

Books have following attributes
- pageCount
- thumbnail
- price
- subtitle
- description
- language
- currency
- id
- authors
- infoLink

```JSON
{
  "pageCount":304,
  "thumbnail":"http:\/\/books.google.com\/books\/content?id=prtSDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
  "price":42.99,
  "subtitle":null,
  "description":"Drowning in unnecessary complexity, unmanaged state, and tangles of spaghetti code? In the best tradition of Lisp, Clojure gets out of your way so you can focus on expressing simple solutions to hard problems.",
  "language":"en",
  "currency":"USD",
  "id":"1680505726",
  "title":"Programming Clojure",
  "infoLink":"https:\/\/play.google.com\/store\/books\/details?id=prtSDwAAQBAJ&source=gbs_api",
  "authors":[
    "Alex Miller",
    "Stuart Halloway",
    "Aaron Bedra"
  ]
}

```


### Users

The user dta was randomly created using https://randomuser.me, which generated JSON like:

```JSON

{
  "password": "9yNvIO4GLBdboI",
  "name": "Georgia Spencer",
  "id": -5035019007718357598,
  "email": "georgia.spencer@example.com"
}

```

To add this submodule, use the following command

```bash
git submodule add git@github.com:redis-developer/redi2read-data.git src/main/resources/data
```

This submodule will load under the folder
```src/main/resource/data``` within our application folder to facilitate the loading of the data from the classpath



