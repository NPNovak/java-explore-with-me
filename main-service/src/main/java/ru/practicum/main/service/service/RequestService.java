package ru.practicum.main.service.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.service.dto.event.EventRequestStatusUpdateRequest;
import ru.practicum.main.service.dto.event.EventRequestStatusUpdateResponse;
import ru.practicum.main.service.dto.request.ParticipationRequestResponse;
import ru.practicum.main.service.model.Event;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestResponse> getRequests(long userId);

    List<ParticipationRequestResponse> getRequestsOnEvent(long userId, long eventId);

    @Transactional
    ParticipationRequestResponse addRequest(long userId, long eventId);

    @Transactional
    ParticipationRequestResponse cancelRequest(long userId, long requestId);

    @Transactional
    EventRequestStatusUpdateResponse updateRequestStatusOnEvent(long userId,
                                                                long eventId,
                                                                EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);

    void checkUser(long userId);

    Event checkEvent(long eventId);

    EventRequestStatusUpdateResponse getAllEventRequests(long eventId);
}
