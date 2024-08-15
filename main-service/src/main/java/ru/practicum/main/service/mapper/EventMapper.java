package ru.practicum.main.service.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.main.service.dto.category.CategoryResponse;
import ru.practicum.main.service.dto.event.*;
import ru.practicum.main.service.dto.user.UserShortResponse;
import ru.practicum.main.service.enums.StateAdminAction;
import ru.practicum.main.service.enums.StateEvent;
import ru.practicum.main.service.enums.StateUserAction;
import ru.practicum.main.service.exception.model.NotExistException;
import ru.practicum.main.service.model.Event;

import java.time.LocalDateTime;

@Component
public class EventMapper {

    public Event toEvent(NewEventRequest newEventRequest) {
        Event event = new Event();
        if (newEventRequest.getAnnotation() != null) {
            event.setAnnotation(newEventRequest.getAnnotation());
        }
        event.setCreatedOn(LocalDateTime.now());
        if (newEventRequest.getDescription() != null) {
            event.setDescription(newEventRequest.getDescription());
        }
        if (newEventRequest.getEventDate() != null) {
            event.setEventDate(newEventRequest.getEventDate());
        }
        if(newEventRequest.getLocation() != null) {
            if(newEventRequest.getLocation().getLat() != null){
                event.setLat(newEventRequest.getLocation().getLat());
            }
            if(newEventRequest.getLocation().getLon() != null){
                event.setLon(newEventRequest.getLocation().getLon());
            }
        }
        event.setPaid(newEventRequest.isPaid());
        event.setParticipantLimit(newEventRequest.getParticipantLimit());
        if (newEventRequest.getRequestModeration() == null) {
            event.setRequestModeration(true);
        } else {
            event.setRequestModeration(newEventRequest.getRequestModeration());
        }
        event.setState(StateEvent.PENDING);
        if(newEventRequest.getTitle() != null){
            event.setTitle(newEventRequest.getTitle());
        }
        return event;
    }

    public EventShortResponse toEventShortResponse(Event event,
                                                   CategoryResponse categoryResponse,
                                                   UserShortResponse userShortDto) {
        EventShortResponse eventShortResponse = new EventShortResponse();
        if(event.getAnnotation() != null){
            eventShortResponse.setAnnotation(event.getAnnotation());
        }
        if(categoryResponse != null){
            eventShortResponse.setCategory(categoryResponse);
        }
        if (event.getConfirmedRequests() != null) {
            eventShortResponse.setConfirmedRequests((long) event.getConfirmedRequests().size());
        }
        if(event.getEventDate() != null){
            eventShortResponse.setEventDate(event.getEventDate());
        }
        eventShortResponse.setId(event.getId());
        if(userShortDto != null){
            eventShortResponse.setInitiator(userShortDto);
        }
        eventShortResponse.setPaid(event.isPaid());
        if(event.getTitle() != null){
            eventShortResponse.setTitle(event.getTitle());
        }
        eventShortResponse.setViews(event.getViews());
        return eventShortResponse;
    }

    public EventFullResponse toEventFullResponse(Event event,
                                                 CategoryResponse categoryResponse,
                                                 UserShortResponse userShortDto) {
        EventFullResponse eventFullResponse = new EventFullResponse();
        if(eventFullResponse.getId() != null){
            eventFullResponse.setId(event.getId());
        }
        if(event.getAnnotation() != null){
            eventFullResponse.setAnnotation(event.getAnnotation());
        }
        if(categoryResponse != null){
            eventFullResponse.setCategory(categoryResponse);
        }
        if (event.getConfirmedRequests() != null) {
            eventFullResponse.setConfirmedRequests((long) event.getConfirmedRequests().size());
        }
        eventFullResponse.setId(event.getId());
        if(event.getCreatedOn() != null){
            eventFullResponse.setCreatedOn(event.getCreatedOn());
        }
        if(event.getDescription() != null){
            eventFullResponse.setDescription(event.getDescription());
        }
        if(event.getEventDate() != null){
            eventFullResponse.setEventDate(event.getEventDate());
        }
        if(userShortDto != null){
            eventFullResponse.setInitiator(userShortDto);
        }
        eventFullResponse.setLocation(new LocationDto(event.getLat(), event.getLon()));
        eventFullResponse.setPaid(event.isPaid());
        eventFullResponse.setParticipantLimit(event.getParticipantLimit());
        if(event.getPublishedOn() != null){
            eventFullResponse.setPublishedOn(event.getPublishedOn());
        }
        eventFullResponse.setRequestModeration(event.isRequestModeration());
        if(event.getState() != null){
            eventFullResponse.setState(event.getState());
        }
        if(event.getTitle() != null){
            eventFullResponse.setTitle(event.getTitle());
        }
        eventFullResponse.setViews(event.getViews());
        return eventFullResponse;
    }

    public void updateEventFromRequest(UpdateEventUserRequest updateRequest, Event event, ru.practicum.main.service.repository.CategoryRepository categoryRepository) {
        if (updateRequest.getAnnotation() != null) {
            event.setAnnotation(updateRequest.getAnnotation());
        }
        if (updateRequest.getCategory() != null) {
            event.setCategory(categoryRepository.findById(updateRequest.getCategory())
                    .orElseThrow(() -> new NotExistException("Category с id = " + updateRequest.getCategory() + " не существует")));
        }
        if (updateRequest.getDescription() != null) {
            event.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getEventDate() != null) {
            event.setEventDate(updateRequest.getEventDate());
        }
        if (updateRequest.getLocation() != null) {
            if (updateRequest.getLocation().getLat() != null) {
                event.setLat(updateRequest.getLocation().getLat());
            }
            if (updateRequest.getLocation().getLon() != null) {
                event.setLon(updateRequest.getLocation().getLon());
            }
        }
        if (updateRequest.getPaid() != null) {
            event.setPaid(updateRequest.getPaid());
        }
        if (updateRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateRequest.getParticipantLimit());
        }
        if (updateRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateRequest.getRequestModeration());
        }
        if (updateRequest.getStateAction() != null) {
            if (updateRequest.getStateAction().equals(StateUserAction.CANCEL_REVIEW)) {
                event.setState(StateEvent.CANCELED);
            } else {
                event.setState(StateEvent.PENDING);
            }
        }

        if (updateRequest.getTitle() != null) {
            event.setTitle(updateRequest.getTitle());
        }
    }

    public void updateEventFromAdminRequest(UpdateEventAdminRequest updateRequest, Event event, ru.practicum.main.service.repository.CategoryRepository categoryRepository) {
        if (updateRequest.getAnnotation() != null) {
            event.setAnnotation(updateRequest.getAnnotation());
        }
        if (updateRequest.getCategory() != null) {
            event.setCategory(categoryRepository.findById(updateRequest.getCategory())
                    .orElseThrow(() -> new NotExistException("Category с id = " + updateRequest.getCategory() + " не существует")));
        }
        if (updateRequest.getDescription() != null) {
            event.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getEventDate() != null) {
            event.setEventDate(updateRequest.getEventDate());
        }
        if (updateRequest.getLocation() != null) {
            if (updateRequest.getLocation().getLat() != null) {
                event.setLat(updateRequest.getLocation().getLat());
            }
            if (updateRequest.getLocation().getLon() != null) {
                event.setLon(updateRequest.getLocation().getLon());
            }
        }
        if (updateRequest.getPaid() != null) {
            event.setPaid(updateRequest.getPaid());
        }
        if (updateRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateRequest.getParticipantLimit());
        }
        if (updateRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateRequest.getRequestModeration());
        }
        if (updateRequest.getStateAction() != null) {
            if (updateRequest.getStateAction().equals(StateAdminAction.PUBLISH_EVENT)) {
                event.setState(StateEvent.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            } else {
                event.setState(StateEvent.CANCELED);
            }
        }
        if (updateRequest.getTitle() != null) {
            event.setTitle(updateRequest.getTitle());
        }
    }

}
