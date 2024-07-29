package ru.practicum.stats.service.server.service;

import ru.practicum.stats.service.dto.EventRequest;
import ru.practicum.stats.service.dto.StatisticResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    List<StatisticResponse> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

    void addEvent(EventRequest eventRequest);
}
