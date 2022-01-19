# Consumer Simulator App

Project to simulate IoT sensor procesing events from a Kafka cluster.

## Technologies

- **[Spring Webflux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html)**
- **[Maven](https://maven.apache.org/)** 
- Java 11
- Kafka / Spring cloud stream
- Mongodb / Reactive connector
- Spring security
- lombok


## Getting Started

### Requirements

- Docker
  - Needed in case you want to run it with Docker.
- Java 11
  - Need have the JAVA_HOME set in case you want to execute via Maven

### Building

At the project root folder, execute:

```shell
maven clean package -DskipTests=true
```


#### Run with tests

```shell
maven clean install
```


## Design and Architecture

* Kafka consumer consumes stream from producer and store it in database. In order to implemet that I've been using :

- spring cloud stream because it provides a good abstraction so it makes it easy later to replcae kafka with another platform such as rabbitmq.
- database and IO operations are blocking however, when it comes to streaming non-blocking is better choice so r2dbc mongo connector has been used which provided by spring mongo reactive library

TODOs :

- For making an endpoint secure , http basic mechanism has been used which can be improved by jwt token and auth server implementation. please visit this repo of [mine](https://github.com/jedlab/cloud-platform).
- inmemory hardcoded users should move to a RDBMS (Postgresql)
- handling backpresure : if kafka producers emit items faster than consumer can consume 
- create docker volumes to keep data after docker restart
- more integration/unit tests coverage

### Running

Make sure, producer is up and running 


Run the docker compose file

      ````bash
      docker-compose -f "docker-compose.yml" up --build -d
      ````





