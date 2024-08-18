package ru.practicum.main.service.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.service.dto.compilation.CompilationResponse;
import ru.practicum.main.service.dto.compilation.NewCompilationRequest;
import ru.practicum.main.service.dto.compilation.UpdateCompilationRequest;
import ru.practicum.main.service.dto.event.EventShortResponse;
import ru.practicum.main.service.model.Compilation;
import ru.practicum.main.service.model.Event;

import java.util.List;
import java.util.Set;

public interface CompilationService {
    List<CompilationResponse> getCompilations(Boolean pinned, int from, int size);

    CompilationResponse getCompilation(long compId);

    @Transactional
    CompilationResponse addCompilation(NewCompilationRequest newCompilationRequest);

    @Transactional
    CompilationResponse updateCompilation(long compId, UpdateCompilationRequest updateCompilationRequest);

    @Transactional
    void deleteCompilation(long compId);

    Compilation checkCompilation(long compId);

    List<Event> checkEvents(Set<Long> eventsIds);

    List<EventShortResponse> convertEventToShort(List<Event> events);
}
