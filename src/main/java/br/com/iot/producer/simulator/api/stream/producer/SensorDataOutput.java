package br.com.iot.producer.simulator.api.stream.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SensorDataOutput {
    String OUTPUT = "iot-data";

    @Output(OUTPUT)
    MessageChannel output();

}
