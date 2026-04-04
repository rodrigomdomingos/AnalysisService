package com.analysis.infrastructure.persistence.mapper;

import com.analysis.domain.model.FundamentalsSnapshot;
import com.analysis.infrastructure.persistence.entity.FundamentalsSnapshotEntity;
import com.analysis.infrastructure.persistence.entity.StockEntity;

public final class FundamentalsMapper {
    private FundamentalsMapper() {
    }

    public static FundamentalsSnapshot toDomain(FundamentalsSnapshotEntity entity) {
        if (entity == null) {
            return null;
        }
        return new FundamentalsSnapshot(
                entity.getStock().getId(),
                entity.getPeriodDate(),
                entity.getDate(),
                entity.getRevenueGrowth(),
                entity.getNetMargin(),
                entity.getRoe(),
                entity.getRoic(),
                entity.getDebtEquity(),
                entity.getFreeCashFlow(),
                entity.getPegRatio()
        );
    }

    public static FundamentalsSnapshotEntity toEntity(FundamentalsSnapshot snapshot, StockEntity stock) {
        if (snapshot == null) {
            return null;
        }
        return new FundamentalsSnapshotEntity(
                stock,
                snapshot.getPeriodDate(),
                snapshot.getDate(),
                snapshot.getRevenueGrowth(),
                snapshot.getNetMargin(),
                snapshot.getRoe(),
                snapshot.getRoic(),
                snapshot.getDebtEquity(),
                snapshot.getFreeCashFlow(),
                snapshot.getPegRatio()
        );
    }
}
