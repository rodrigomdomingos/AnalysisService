package com.analysis.infrastructure.persistence.mapper;

import com.analysis.domain.model.Price;
import com.analysis.infrastructure.persistence.entity.PriceEntity;

public final class PriceMapper {
    private PriceMapper() {
    }

    public static Price toDomain(PriceEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Price(
                entity.getId(),
                entity.getStock().getId(),
                entity.getSnapshotAt(),
                entity.getClosePrice(),
                entity.getVolume()
        );
    }
}
