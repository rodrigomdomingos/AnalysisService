package com.analysis.domain.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Service;

@Service
public class TimeService {

    private final Clock clock;

    public TimeService(Clock clock) {
        this.clock = clock;
    }

    public OffsetDateTime getNow() {
        return OffsetDateTime.now(clock);
    }

    public OffsetDateTime getStartOfDay() {
        ZoneId zone = clock.getZone();
        return LocalDate.now(clock).atStartOfDay(zone).toOffsetDateTime();
    }
}
