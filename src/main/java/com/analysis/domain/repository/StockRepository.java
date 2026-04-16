package com.analysis.domain.repository;

import com.analysis.domain.model.Stock;

import java.util.Optional;

public interface StockRepository {
    Optional<Stock> findByTicker(String ticker);

    Optional<Stock> findById(Long stockId);
}
