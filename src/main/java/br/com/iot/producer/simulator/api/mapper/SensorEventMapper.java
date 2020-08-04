package br.com.iot.producer.simulator.api.mapper;

import br.com.iot.producer.simulator.api.controller.events.request.SensorEventRequest;
import br.com.iot.producer.simulator.api.model.event.RandomSensorEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring")
public interface SensorEventMapper {

    @Mapping(target = "timestamp", ignore = true)
    @Mapping(target = "value", ignore = true)
    @NonNull
    RandomSensorEvent toSensorEvent(@NonNull SensorEventRequest request);
}
