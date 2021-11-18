package by.it_academy.food_diary.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Component
public class TimeUtil {
    public LocalDateTime microsecondsToLocalDateTime(Long timeMicros) {
        Instant instant = Instant.EPOCH.plus(timeMicros, ChronoUnit.MICROS);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
