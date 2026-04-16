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
        PriceSnapshotEntity entity = new PriceSnapshotEntity();
        apply(snapshot, stock, entity);
        return entity;
    }

    public static void apply(PriceSnapshot snapshot, StockEntity stock, PriceSnapshotEntity entity) {
        if (snapshot == null || entity == null) {
            return;
        }
        entity.setStock(stock);
        entity.setSnapshotAt(snapshot.getSnapshotAt());
        entity.setClosePrice(snapshot.getClosePrice());
        entity.setVolume(snapshot.getVolume());
        entity.setRsi(snapshot.getRsi());
        entity.setMa50(snapshot.getMa50());
        entity.setMa200(snapshot.getMa200());
    }
}
