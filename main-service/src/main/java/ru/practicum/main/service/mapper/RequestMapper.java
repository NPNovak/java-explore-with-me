package ru.practicum.main.service.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.main.service.dto.request.ParticipationRequestResponse;
import ru.practicum.main.service.model.Request;

@Component
public class RequestMapper {

    public ParticipationRequestResponse toParticipationRequestResponse(Request request) {
        ParticipationRequestResponse participationRequestResponse = new ParticipationRequestResponse();
        participationRequestResponse.setId(request.getId());
        participationRequestResponse.setEvent(request.getEventId());
        if(request.getStatus() != null){
            participationRequestResponse.setStatus(request.getStatus());
        }
        if(request.getCreated() != null){
            participationRequestResponse.setCreated(request.getCreated());
        }
        return participationRequestResponse;
    }
}
