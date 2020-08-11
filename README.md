# Producer Simulator API

Project to simulate IoT sensor sending events to a Kafka cluster.

![AWS CodeBuild Master](https://codebuild.us-east-1.amazonaws.com/badges?uuid=eyJlbmNyeXB0ZWREYXRhIjoidkU5dngzWnhJSkVZOE83RG5GOFdxVWJ4OStiOVFBYjAzSXRXVndEMHdpNjFFbnBXRWxRbjVFNVN5bnF1cUNoQkFRbTY1L3ZhLzJTSzZXc0hUMCtCS1NvPSIsIml2UGFyYW1ldGVyU3BlYyI6IkU5QUJNRDJ0VzNNRUNhOGwiLCJtYXRlcmlhbFNldFNlcmlhbCI6MX0%3D&branch=master)

![Docker Image Size (tag)](https://img.shields.io/docker/image-size/flaviosolci/iot-services/producer-latest)

## Technologies

- **[Spring Webflux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html)** - 2.3.2.RELEASE
- **[Mapstruct](https://mapstruct.org/)** - 1.4.0.Beta3
- **[Immutables](https://immutables.github.io/)** - 2.8.8
- **[Gradle](https://gradle.org/)** - 6.5.1
- Java 11

## Getting Started

### Requirements

- Docker
  - Needed in case you want run Kafka with Docker. In case you want use the Kafka scripts, you don't need it.
- Java 11
  - Need have the JAVA_HOME set in case you wanna execute via Gradle

### Building

At the project root folder, execute:

```shell
./gradlew build
```

#### Only tests

At the project root folder, execute:

```shell
./gradlew test
```

#### Jacoco

```shell
./gradlew test jacocoTestReport
```

Path: `./build/reports/jacoco/jacocoRootReport/html/index.html`

### Running

Below are the options:

1. **A docker-compose to rule them all**:

   1. Install docker: https://docs.docker.com/engine/install/ 

      - On Linux, docker compose need be installed separately: https://docs.docker.com/compose/install/

   2. Go to https://github.com/flaviosolci/iot-all-in-one and the [docker-compose.yml](https://github.com/flaviosolci/iot-all-in-one/blob/master/docker-compose.yml)

   3. Run the docker compose file

      ````bash
      docker-compose -f "docker-compose.yml" up -d
      ````

      ![Docker compose success status](docs/wiki/images/docker-compose-all-in-one.png)

      It will start:

      - 1x [Zookeper](https://hub.docker.com/r/wurstmeister/zookeeper)
      - 3x [Kafka](https://hub.docker.com/r/wurstmeister/kafka) brokers
      - [TimescaleDB](https://hub.docker.com/r/timescale/timescaledb) (used by the consumer)
      - [Kafdrop](https://hub.docker.com/r/obsidiandynamics/kafdrop)
      - [Producer API](https://hub.docker.com/layers/flaviosolci/iot-services/producer-latest-develop/images/sha256-f97c1b7ef32b832f1b0dfda637645a7e07c0c7dd3f372007633657f9f15e8afe?context=repo)
      - [Consumer API](https://hub.docker.com/layers/flaviosolci/iot-services/consumer-latest-develop/images/sha256-15a096b68ec8088f7c9e4185fdef254ba081366204856449a84878874fc5e5f4?context=repo)

   4. Application will start on `localhost:8091`

      1. Checkout the [OpenApi](docs/api/openapi.yml) to know about the endpoint and how execute them.

      :memo: This will also start the consumer API. Checkout more about it: https://github.com/flaviosolci/iot-consumer-api

      :memo: It also comes with [Kafdrop](https://github.com/obsidiandynamics/kafdrop), which is *is a web UI for viewing Kafka topics and browsing consumer groups*. You can access via browser: [http://localhost:9000](http://localhost:9000/).

2. **Hmm, that's ok...**: Cloning the repository + Kafka Docker

   1. Clone this repo to your local machine

   2. Install docker: https://docs.docker.com/engine/install/ 

      - On Linux, docker compose need be installed separately: https://docs.docker.com/compose/install/

   3. Go to https://github.com/flaviosolci/iot-all-in-one and the [docker-compose-minimal.yml](https://github.com/flaviosolci/iot-all-in-one/blob/master/docker-compose-minimal.yml)

   4. Run the docker compose file

      ````bash
      docker-compose -f "docker-compose-minimal.yml" up -d
      ````

      ![Docker compose success status](docs/wiki/images/docker-compose-minimal.png)

      - It will start:
        - 1x [Zookeper](https://hub.docker.com/r/wurstmeister/zookeeper)
        - 3x [Kafka](https://hub.docker.com/r/wurstmeister/kafka) brokers
        - [TimescaleDB](https://hub.docker.com/r/timescale/timescaledb) (used by the consumer)
        - [Kafdrop](https://hub.docker.com/r/obsidiandynamics/kafdrop)

   5. Go to the root directory of this project

      1. Run `./gradlew bootrun`

   6. Application will start on `localhost:8091`

   7. Checkout the [OpenApi](docs/api/openapi.yml) to know about the endpoint and how execute them.

      :memo: It also comes with [Kafdrop](https://github.com/obsidiandynamics/kafdrop), which is *is a web UI for viewing Kafka topics and browsing consumer groups*. You can access via browser: [http://localhost:9000](http://localhost:9000/).

3. **Nightmare**: Cloning the repository + run Kafka Manually

   1. Clone this repo to your local machine

   2. Install Kafka/Zookeeper manually:  https://kafka.apache.org/downloads

   3. Start Zookeeper

      :memo: Note the commands for Windows and Linux are the same, the only difference is the path and file extension. All the windows files will be under `bin\windows` and have the `.bat` extension, while the Linux ones are directly under `bin` and have the `.sh`extension.

      **Windows**

      ```batch
      .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
      ```

      **Linux**

      ```bash
      ./bin/zookeeper-server-start.bat ./config/zookeeper.properties
      ```

   4. Start Kafka

      **Windows**

      ````batch
      .\bin\windows\kafka-server-start.sh .\config\server.properties
      ````

      **Linux**:

      ````bash
      ./bin/kafka-server-start.sh ./config/server.properties
      ````

   5. Create a topic

      **Windows**

      ````batch
      .\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic iot-data
      ````

      **Linux**

      ````bash
      ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic iot-data
      ````

   6. Start the producer application

      1. Go to the root directory of this project
      2. Run `./gradlew bootrun`

   7. Application will start on `localhost:8091`

   8. Checkout the OpenApi to know about the endpoint and how execute them.

## Documentation

### OpenApi

The endpoints are documented using OpenApi and can be found at [docs/api/openapi.yml](docs/api/openapi.yml)

More about OpenAPI: https://www.openapis.org/.

You can also checkout the [Postman Collection](https://documenter.getpostman.com/view/7376623/T1LLETBq?version=latest#ced80847-a808-4214-8b64-9e3198375944).

### Security

The API security is made using JWT authentication. The Authorization Server is provided by [Okta](https://www.okta.com/), so we just need consume an endpoint with the credentials and it will return the token. With this token you can access the API resources.

To access this API, the user must have the `admin` scope, otherwise the access will be denied.

To get the token, you must access the endpoint: `https://dev-928245.okta.com/oauth2/ausp92bbxrQ9m9Ojq4x6/v1/token` and provide: 

- username (email)
- password 
- Authorization Basic

Ex:

```shell
curl --location --request POST 'https://dev-928245.okta.com/oauth2/ausp92bbxrQ9m9Ojq4x6/v1/token' \
--header 'Accept: application/json' \
--header 'Authorization: Basic d0MzR3FzN1RRY0E0dg==...' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=test@test.com' \
--data-urlencode 'password=as243fse2' \
--data-urlencode 'grant_type=password'
```

If you have the right permissions it will return you something like that:

```json
{
    "token_type": "Bearer",
    "expires_in": 1800,
    "access_token": "eyJraWQiOiJUNzNuY3dy......",
    "scope": "admin"
}
```

The `access_token` the one you need pass when calling the API

Ex:

```shell script
curl --location --request POST 'http://localhost:8091/producer-api/clusters' \
--header 'Authorization: Bearer eyJraWQiOiJUNzNuY3dy......' \
--header 'Content-Type: application/json' \
--data-raw '[
    {
        "total": 10,
        "type": "CPU",
        "heartBeat": 1,
        "clusterSize": 5,
        "clusterId": 445
    }
]'
```

In case you don't provide the token in the call, an error `401` will be returned. If your user does not have the correct permissions, an error `403`will be returned.

More about JWT: https://auth0.com/learn/json-web-tokens/

##### Authentication flow:

1. Receive the call
2. Check if there is a token in the header
   1. In case negative, thrown an exception (status `401`)
3. Validate the token against Okta public key
   1. If the token is invalid or expired, throw exception (status `401`)
4. Check if the token has necessary scopes to access the resource
   1. If does not have, throw an exception (status `403`)
5. Procced to complete the request

### Flows

To generate the sensor events we have two flows: via single sensor or via sensor cluster.

#### Single sensor

The `Single sensor` endpoint provides the capability to setup a single sensor (with all the information provided by you) and then send as many request you want. 

For example, let's say we have the sensor `Living Room Temp` and we want simulate the events for `TEMPERATURE` every `5 seconds`  for `10 minutes`. This is what the request would look like:

````curlrc
curl --location --request POST 'http://localhost:8091/producer-api/events' \
--header 'Authorization: Bearer eyJraWQiOiJUNzNuY3dy......' \
--header 'Content-Type: application/json' \
--data-raw '[
    {
        "total": 120,
        "type": "TEMPERATURE",
        "heartBeat": 5,
        "id": 1,
        "name": "Living Room Temp",
        "clusterId": "1"
    }
]'
````

:memo: The `id` is a unique identifier of this sensor and is mandatory.

:memo: The `clusterId`, identifies this sensor as belonging to a cluster. Later you can query all the sensor in the same cluster. This field is optional.

:memo: The `total` is how many events do you want. In our example, we wanted it running for 10 minutes, so: 120 X 5 seconds = 10 minutes

:memo: The `heartBeat` is the interval, in seconds, between each event.

For more info about the endpoint, see [openapi.yml](docs/api/openapi.yml)

####  Sensor cluster

In the sensor cluster, it will simulate a several sensors of the same type sending events. 

Taking the same example as before, lets say now e added 100 more sensors for temperature and we still want simulate the same: events for `TEMPERATURE` every `5 seconds`  for `10 minutes`. 

The request would look like:

````curlrc
curl --location --request POST 'http://localhost:8091/producer-api/clusters' \
--header 'Authorization: Bearer eyJraWQiOiJUNzNuY3dy......' \
--header 'Content-Type: application/json' \
--data-raw '[
    {
        "total": 120,
        "type": "TEMPERATURE",
        "heartBeat": 5,
        "clusterSize": 100,
        "clusterId": 1
    }
]'
````

:memo: `clusterSize` is how many sensor you want running in parallel.

:memo: Note that now we don't provide the `id`, it's because the server is going to random generate one for each sensor. In this case is easier to query the sensor events by `clusterId` in the Consumer API.

For more info about the endpoint, see [openapi.yml](docs/api/openapi.yml)

