package ru.practicum.stats.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class EventRequest {
    @NotBlank(message = "app пустой или null")
    private String app;
    @NotBlank(message = "uri пустой или null")
    private String uri;
    @NotBlank(message = "ip пустой или null")
    private String ip;
    @NotNull(message = "timestamp null")
    @PastOrPresent(message = "timestamp в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}