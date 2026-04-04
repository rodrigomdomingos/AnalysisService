package com.analysis.infrastructure.persistence.mapper;

import com.analysis.domain.model.Stock;
import com.analysis.infrastructure.persistence.entity.StockEntity;

public final class StockMapper {
    private StockMapper() {
    }

    public static Stock toDomain(StockEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Stock(
                entity.getId(),
                entity.getTicker(),
                entity.getExchange(),
                entity.getSector(),
                entity.getCurrency()
        );
    }

    public static StockEntity toEntity(Stock stock) {
        if (stock == null) {
            return null;
        }
        StockEntity entity = new StockEntity(stock.getTicker(), stock.getExchange(), stock.getSector(), stock.getCurrency());
        return entity;
    }
}
