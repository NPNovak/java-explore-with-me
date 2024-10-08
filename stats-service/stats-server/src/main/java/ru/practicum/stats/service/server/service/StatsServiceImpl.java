package ru.practicum.stats.service.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.service.server.mapper.StatsMapper;
import ru.practicum.stats.service.server.repository.StatsRepository;
import ru.practicum.stats.service.dto.EventRequest;
import ru.practicum.stats.service.dto.StatisticResponse;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final StatsRepository repository;
    private final StatsMapper mapper;

    @Override
    public List<StatisticResponse> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (start.isAfter(end)) {
            throw new ConstraintViolationException("Дата начала позже даты старта", null);
        }

        if (uris == null || uris.isEmpty()) {
            if (unique) {
                return repository.getAllStatisticsUniqueIp(start, end).stream().map(mapper::toStatisticResponse).collect(Collectors.toList());
            } else {
                return repository.getAllStatisticsAnyIp(start, end).stream().map(mapper::toStatisticResponse).collect(Collectors.toList());
            }
        } else {
            if (unique) {
                return repository.getStatisticsUniqueIp(start, end, uris).stream().map(mapper::toStatisticResponse).collect(Collectors.toList());
            } else {
                return repository.getStatisticsAnyIp(start, end, uris).stream().map(mapper::toStatisticResponse).collect(Collectors.toList());
            }
        }
    }

    @Override
    @Transactional
    public void addEvent(EventRequest eventRequest) {
        repository.save(mapper.toEvent(eventRequest));
    }
}
