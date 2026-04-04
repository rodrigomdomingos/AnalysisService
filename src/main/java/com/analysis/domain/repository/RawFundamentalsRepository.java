package com.analysis.domain.repository;

import com.analysis.domain.model.RawFundamentals;

import java.util.List;

public interface RawFundamentalsRepository {
    List<RawFundamentals> findByStockIdOrderByReferenceDateDesc(Long stockId);
}
