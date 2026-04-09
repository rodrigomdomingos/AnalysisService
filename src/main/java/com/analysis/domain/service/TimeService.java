package com.analysis.domain.service;

import java.time.Clock;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Service;

@Service
public class TimeService {

    private final Clock clock;

    public TimeService(Clock clock) {
        this.clock = clock;
    }

    public OffsetDateTime getUtcNow() {
        return OffsetDateTime.now(clock);
    }
}
