package ru.practicum.main.service.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.service.dto.event.*;
import ru.practicum.main.service.enums.StateEvent;
import ru.practicum.main.service.enums.StateSortEvent;
import ru.practicum.main.service.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortResponse> getEventsAdmin(long userId, int from, int size);

    EventFullResponse getEventAdmin(long userId, long eventId);

    EventFullResponse getEventPublic(long eventId, HttpServletRequest request);

    List<EventFullResponse> searchEventsAdmin(List<Long> users,
                                              List<StateEvent> states,
                                              List<Long> categories,
                                              LocalDateTime rangeStart,
                                              LocalDateTime rangeEnd,
                                              int from,
                                              int size);

    List<EventShortResponse> searchEventsPublic(String text,
                                                List<Long> categories,
                                                Boolean paid,
                                                LocalDateTime rangeStart,
                                                LocalDateTime rangeEnd,
                                                boolean onlyAvailable,
                                                StateSortEvent sort,
                                                int from,
                                                int size,
                                                HttpServletRequest request);

    @Transactional
    EventFullResponse addEventUser(long userId, NewEventRequest newEventRequest);

    @Transactional
    EventFullResponse updateEventUser(long userId, long eventId, UpdateEventUserRequest updateRequest);

    @Transactional
    EventFullResponse updateEventAdmin(long eventId, UpdateEventAdminRequest updateRequest);

    Event checkEvent(long eventId);
}
