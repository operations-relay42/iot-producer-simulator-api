![Relay42 logo](../docs/wiki/images/relay42.png "Relay42")

## Context

Imagine there are IoT devices sending out continuous data which we want to collect (e.g. thermostat, heart rate car fuel readings, etc.). Our company wants to collect the data, store it somewhere and then be able to query it later.

Our team already worked in a feature to get the IoT device data and send them to a _Kafka Stream_. Your task is to build an application(s) in which we can read the IoT data from the
stream, store it and then, in a secure way query the readings of specific sensors or groups of sensors for a specific timeframe.

## Getting Started

## Producer API

We are providing to you an application that will send "IoT events" to a Kafka stream. With this you can simulate any number of devices for your application.
Check [here](../README.md) for more information.

### Requirements

- Written Java.
- Read the events from the stream and store it.
- Provide REST **secure** endpoint(s) to query the sensor data
    - average/median/max/min
- Scalable and extendable to accept more IoT device types
- _(Nice to have)_ a self-contained / container-based solution

Your submission should contain the following:

- Source code
    - preferably sourced at some version control (GitHub, Gitlab, BitBucket...)
- Short description of the approach and limitations of the implementation
- Instructions on how to run and how to access the service

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
