package ru.practicum.stats.service.exception;

public class ServerResponseException extends RuntimeException {
    public ServerResponseException(String message) {
        super(message);
    }
}
