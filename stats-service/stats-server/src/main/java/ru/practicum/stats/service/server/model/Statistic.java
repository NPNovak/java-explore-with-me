package ru.practicum.stats.service.server.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Statistic {
    private String app;
    private String uri;
    private long hits;
}
