package br.com.iot.producer.simulator.api.mapper;

import br.com.iot.producer.simulator.api.controller.events.request.ClusterEventRequest;
import br.com.iot.producer.simulator.api.controller.events.request.EventRequest;
import br.com.iot.producer.simulator.api.model.event.ModifiableRandomEvent;
import br.com.iot.producer.simulator.api.model.event.RandomEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    default RandomEvent fromRequest(ClusterEventRequest request) {
        return ModifiableRandomEvent.create().setType(request.getType()).setClusterId(request.getClusterId());
    }

    default RandomEvent fromRequest(EventRequest request) {
        return ModifiableRandomEvent.create()
                .setType(request.getType())
                .setName(request.getName())
                .setId(request.getId())
                .setClusterId(request.getClusterId());
    }

}
