package ru.practicum.stats.service.server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ExceptionHandlerResponse {
    private final String message;
    private final String error;
}

