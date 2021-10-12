package com.relay.iot.producer.simulator.api.stream.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EventDataOutput {
    String OUTPUT = "iot-data";

    @Output(OUTPUT)
    MessageChannel output();

}
