package ru.practicum.main.service.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.main.service.dto.compilation.CompilationResponse;
import ru.practicum.main.service.dto.compilation.NewCompilationRequest;
import ru.practicum.main.service.dto.event.EventShortResponse;
import ru.practicum.main.service.model.Compilation;

import java.util.List;

@Component
public class CompilationMapper {

    public Compilation toCompilation(NewCompilationRequest newCompilationRequest) {
        Compilation compilation = new Compilation();
        if (newCompilationRequest != null) {
            compilation.setPinned(newCompilationRequest.isPinned());

            if (newCompilationRequest.getTitle() != null) {
                compilation.setTitle(newCompilationRequest.getTitle());
            }
            if (newCompilationRequest.getEvents() != null) {
                compilation.setEvents(newCompilationRequest.getEvents());
            }
        }
        return compilation;
    }

    public CompilationResponse toCompilationResponse(Compilation compilation, List<EventShortResponse> events) {
        CompilationResponse compilationResponse = new CompilationResponse();
        if (compilation != null) {
            compilationResponse.setId(compilation.getId());
            compilationResponse.setPinned(compilation.isPinned());
            if (compilation.getTitle() != null) {
                compilationResponse.setTitle(compilation.getTitle());
            }
        }
        if (events != null) {
            compilationResponse.setEvents(events);
        }
        return compilationResponse;
    }
}
