package br.com.iot.producer.simulator.api.mapper;

import br.com.iot.producer.simulator.api.controller.events.request.ClusterEventRequest;
import br.com.iot.producer.simulator.api.controller.events.request.EventRequest;
import br.com.iot.producer.simulator.api.model.event.ModifiableRandomEvent;
import br.com.iot.producer.simulator.api.model.event.RandomEvent;
import br.com.iot.producer.simulator.api.utils.RandomUtils;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    default RandomEvent fromRequest(ClusterEventRequest request, Integer processStep) {
        return ModifiableRandomEvent.create()
                .setType(request.getType())
                .setClusterId(request.getClusterId())
                .setName(String.format("%s_%s", request.getName(), processStep))
                .setId(RandomUtils.randomInt(request.getClusterSize()));
    }

    default RandomEvent fromRequest(EventRequest request) {
        return ModifiableRandomEvent.create()
                .setType(request.getType())
                .setName(request.getName())
                .setId(request.getId())
                .setClusterId(request.getClusterId());
    }

}
