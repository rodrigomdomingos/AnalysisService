package com.analysis.domain.repository;

import com.analysis.domain.model.Price;

import java.util.List;

public interface PriceRepository {
    List<Price> findByStockIdOrderBySnapshotAtAsc(Long stockId);
}
