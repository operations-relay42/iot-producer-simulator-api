![Relay42 logo](../docs/wiki/images/relay42.png "Relay42")

## Context

### IoT, the Data and Everything

The Internet of Things (IoT), refers to the devices around the world that are connected to the internet, all collecting and sharing data. [Many companies](https://www.zdnet.com/article/ten-examples-of-iot-and-big-data-working-well-together/) are already using it to improve and better understand their business.

Our company has access to several of these IoT devices that are constantly sending data (e.g. thermostat, heart rate car fuel readings, etc.). What we want is to collect the data, store it somewhere and then query it later.

Our team already started work in a feature to get the IoT device data and send them to a _Kafka Stream_. Your task is to build an application(s) in which we can read the IoT data from the
stream, store it and then, in a secure way query the readings of specific sensors or groups of sensors for a specific timeframe.

## Getting Started

### Producer API

We are providing to you an application that will send "IoT events" to a Kafka stream. With this you can simulate any number of devices for your application.
Check [here](../README.md) for more information.

### Requirements

- Written Java.
- Use [iot-producer-simulator-api](https://github.com/operations-relay42/iot-producer-simulator-api/blob/master/assignment/problem.md#requirements) to produce events (don’t need write your own producer).
- Read the events from the stream and store it.
- Provide REST **secure** endpoint(s) to query the sensor data
    - Operations: average/median/max/min
    - Filters:
        - _(Mandatory)_ Time-frame (from/to)
        - Event type
        - Cluster ID.
- Scalable and extendable to accept more IoT device types
- A self-contained / container-based solution

Your submission should contain the following:

- Source code
    - preferably sourced at some version control (GitHub, Gitlab, BitBucket...)
- Short description of your design and architecture, explaining your main decisions 
- Limitation of the current implementation and how it could be improved.

### Evaluation criteria

- Ability to transform vague requirements into a working system
- Code quality / tests
    - Unit test. Integration tests. Code coverage.
- Architecture / design scalability
    - How difficult would be to introduce new event types? And if they have a different structure?
    - Is concurrency a problem? If we had a huge number of events, would it be a problem?
- Technology choices.
    - Why that Database? What's the benefit of this framework?
- Good documentation
    - Can anyone team member that get this code easily executes and maintain?

Don't panic and good luck!
