package com.analysis.domain.repository;

import com.analysis.domain.model.PriceSnapshot;

public interface PriceSnapshotRepository {
    PriceSnapshot save(PriceSnapshot snapshot);
}
