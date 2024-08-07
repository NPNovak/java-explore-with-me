package ru.practicum.stats.service.server.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.stats.service.server.model.Event;
import ru.practicum.stats.service.server.model.Statistic;
import ru.practicum.stats.service.dto.EventRequest;
import ru.practicum.stats.service.dto.StatisticResponse;

@Component
@RequiredArgsConstructor
public class StatsMapper {

    public Event toEvent(EventRequest eventRequest) {
        Event event = new Event();
        event.setApp(eventRequest.getApp());
        event.setUri(eventRequest.getUri());
        event.setIp(eventRequest.getIp());
        event.setCreated(eventRequest.getTimestamp());
        return event;
    }

    public StatisticResponse toStatisticResponse(Statistic statistic) {
        StatisticResponse statisticResponse = new StatisticResponse();
        statisticResponse.setApp(statistic.getApp());
        statisticResponse.setUri(statistic.getUri());
        statisticResponse.setHits(statistic.getHits());
        return statisticResponse;
    }
}
