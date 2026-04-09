package com.analysis.infrastructure.persistence.mapper;

import com.analysis.domain.model.PriceSnapshot;
import com.analysis.infrastructure.persistence.entity.PriceSnapshotEntity;
import com.analysis.infrastructure.persistence.entity.StockEntity;

public final class PriceSnapshotMapper {
    private PriceSnapshotMapper() {
    }

    public static PriceSnapshot toDomain(PriceSnapshotEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PriceSnapshot(
                entity.getStock().getId(),
                entity.getSnapshotAt(),
                entity.getClosePrice(),
                entity.getVolume(),
                entity.getRsi(),
                entity.getMa50(),
                entity.getMa200()
        );
    }

    public static PriceSnapshotEntity toEntity(PriceSnapshot snapshot, StockEntity stock) {
        if (snapshot == null) {
            return null;
        }
        return new PriceSnapshotEntity(
                stock,
                snapshot.getSnapshotAt(),
                snapshot.getClosePrice(),
                snapshot.getVolume(),
                snapshot.getRsi(),
                snapshot.getMa50(),
                snapshot.getMa200(),
                null
        );
    }
}
