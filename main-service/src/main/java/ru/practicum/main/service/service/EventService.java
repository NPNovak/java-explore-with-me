package ru.practicum.main.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.service.dto.event.*;
import ru.practicum.main.service.enums.StateEvent;
import ru.practicum.main.service.enums.StateSortEvent;
import ru.practicum.main.service.exception.model.ConflictException;
import ru.practicum.main.service.exception.model.NotExistException;
import ru.practicum.main.service.mapper.CategoryMapper;
import ru.practicum.main.service.mapper.EventMapper;
import ru.practicum.main.service.mapper.UserMapper;
import ru.practicum.main.service.model.Event;
import ru.practicum.main.service.model.User;
import ru.practicum.main.service.repository.CategoryRepository;
import ru.practicum.main.service.repository.EventRepository;
import ru.practicum.main.service.repository.UserRepository;
import ru.practicum.main.service.specification.EventSpecification;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import ru.practicum.stats.service.client.StatsClient;
import ru.practicum.stats.service.dto.EventRequest;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;
    private final EventMapper mapper;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final StatsClient statsClient;

    public List<EventShortResponse> getEventsAdmin(long userId, int from, int size) {
        return repository.findByInitiatorId(userId, PageRequest.of(from / size, size))
                .stream()
                .map(event -> mapper.toEventShortResponse(event,
                        categoryMapper.toCategoryDto(event.getCategory()),
                        userMapper.toUserShortResponse(event.getInitiator())))
                .collect(toList());
    }

    public EventFullResponse getEventAdmin(long userId, long eventId) {
        Event event = checkEvent(eventId);

        if (event.getInitiator().getId() != userId) {
            throw new NotExistException("User с id = " + userId + " не являеться инициатором Event с id = " + eventId);
        }

        return mapper.toEventFullResponse(event,
                categoryMapper.toCategoryDto(event.getCategory()),
                userMapper.toUserShortResponse(event.getInitiator()));
    }

    public EventFullResponse getEventPublic(long eventId, HttpServletRequest request) {
        Event event = checkEvent(eventId);
        if (!event.getState().equals(StateEvent.PUBLISHED)) {
            throw new NotExistException("Event с id = " + eventId + " недоступно");
        }

        // При каждом обращении к событию добавляем
        statsClient.addEvent(new EventRequest("ewm-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now()));

        event.setViews(statsClient.getStatistics(event.getCreatedOn(),
                LocalDateTime.now(),
                List.of(request.getRequestURI()),
                true).getFirst().getHits());

        repository.save(event);

        return mapper.toEventFullResponse(event,
                categoryMapper.toCategoryDto(event.getCategory()),
                userMapper.toUserShortResponse(event.getInitiator()));
    }

    public List<EventFullResponse> searchEventsAdmin(List<Long> users,
                                                     List<StateEvent> states,
                                                     List<Long> categories,
                                                     LocalDateTime rangeStart,
                                                     LocalDateTime rangeEnd,
                                                     int from,
                                                     int size) {
        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new ConstraintViolationException("Дата rangeStart = " + rangeStart + "позже даты rangeEnd = " + rangeEnd, null);
        }

        Pageable pageable = PageRequest.of(from / size, size);

        Specification<Event> specification = Specification
                .where(EventSpecification.hasUsers(users))
                .and(EventSpecification.hasStates(states))
                .and(EventSpecification.hasCategories(categories))
                .and(EventSpecification.hasRangeStart(rangeStart))
                .and(EventSpecification.hasRangeEnd(rangeEnd));

        return repository.findAll(specification, pageable)
                .stream()
                .map(event -> mapper.toEventFullResponse(event,
                        categoryMapper.toCategoryDto(event.getCategory()),
                        userMapper.toUserShortResponse(event.getInitiator())))
                .collect(toList());
    }

    public List<EventShortResponse> searchEventsPublic(String text,
                                                       List<Long> categories,
                                                       Boolean paid,
                                                       LocalDateTime rangeStart,
                                                       LocalDateTime rangeEnd,
                                                       boolean onlyAvailable,
                                                       StateSortEvent sort,
                                                       int from,
                                                       int size,
                                                       HttpServletRequest request) {
        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new ConstraintViolationException("Дата rangeStart = " + rangeStart + "позже даты rangeEnd = " + rangeEnd, null);
        }

        statsClient.addEvent(new EventRequest("ewm-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now()));

        Pageable pageable = PageRequest.of(from / size, size);

        Specification<Event> specification = Specification
                .where(EventSpecification.hasTextAnnotation(text))
                .or(EventSpecification.hasTextDescription(text))
                .and(EventSpecification.hasCategories(categories))
                .and(EventSpecification.hasPaid(paid))
                .and(EventSpecification.hasRangeDate(rangeStart, rangeEnd))
                .and(EventSpecification.hasLimit(onlyAvailable))
                .and(EventSpecification.hasPublished())
                .and(EventSpecification.hasSort(sort));

        return repository.findAll(specification, pageable)
                .stream()
                .map(event -> mapper.toEventShortResponse(event,
                        categoryMapper.toCategoryDto(event.getCategory()),
                        userMapper.toUserShortResponse(event.getInitiator())))
                .collect(toList());
    }

    @Transactional
    public EventFullResponse addEventUser(long userId, NewEventRequest newEventRequest) {
        Event event = mapper.toEvent(newEventRequest);

        event.setInitiator(userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User с id = " + userId + " не существует")));
        event.setCategory(categoryRepository.findById(newEventRequest.getCategory())
                .orElseThrow(() -> new ConflictException("Category с id = " + newEventRequest.getCategory() + " не существует")));

        return mapper.toEventFullResponse(repository.save(event),
                categoryMapper.toCategoryDto(event.getCategory()),
                userMapper.toUserShortResponse(event.getInitiator()));
    }

    @Transactional
    public EventFullResponse updateEventUser(long userId, long eventId, UpdateEventUserRequest updateRequest) {
        Event event = checkEvent(eventId);
        if (event.getState().equals(StateEvent.PUBLISHED)) {
            throw new ConflictException("Event с id = " + eventId + " уже опубликован");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User с id = " + userId + " не существует"));
        if (user.getId() != userId) {
            throw new ConflictException("User с id = " + userId + " не является инициатором события");
        }

        mapper.updateEventFromRequest(updateRequest, event, categoryRepository);

        return mapper.toEventFullResponse(repository.save(event),
                categoryMapper.toCategoryDto(event.getCategory()),
                userMapper.toUserShortResponse(event.getInitiator()));
    }

    @Transactional
    public EventFullResponse updateEventAdmin(long eventId, UpdateEventAdminRequest updateRequest) {
        Event event = checkEvent(eventId);

        if (!event.getState().equals(StateEvent.PENDING)) {
            throw new ConflictException("Event не находиться в ожидании публикации");
        }

        mapper.updateEventFromAdminRequest(updateRequest, event, categoryRepository);

        return mapper.toEventFullResponse(repository.save(event),
                categoryMapper.toCategoryDto(event.getCategory()),
                userMapper.toUserShortResponse(event.getInitiator()));
    }

    private Event checkEvent(long eventId) {
        return repository.findById(eventId)
                .orElseThrow(() -> new NotExistException("Event с id = " + eventId + " не существует"));
    }
}
