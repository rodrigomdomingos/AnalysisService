package com.analysis.infrastructure.persistence.mapper;

import com.analysis.domain.model.ValuationSnapshot;
import com.analysis.infrastructure.persistence.entity.StockEntity;
import com.analysis.infrastructure.persistence.entity.ValuationSnapshotEntity;

public final class ValuationMapper {
    private ValuationMapper() {
    }

    public static ValuationSnapshot toDomain(ValuationSnapshotEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ValuationSnapshot(
                entity.getStock().getId(),
                entity.getSnapshotAt(),
                entity.getPeRatio(),
                entity.getEvEbitda(),
                entity.getPegRatio(),
                entity.getFcfYield()
        );
    }
    
    public static ValuationSnapshotEntity toEntity(ValuationSnapshot snapshot, StockEntity stock) {
        if (snapshot == null) {
            return null;
        }
        return new ValuationSnapshotEntity(
                stock,
                snapshot.getSnapshotAt(),
                snapshot.getPe(),
                snapshot.getEvEbitda(),
                snapshot.getPeg(),
                snapshot.getFcfYield(),
                null
        );
    }
}
