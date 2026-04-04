package com.analysis.infrastructure.persistence.repository;

import com.analysis.infrastructure.persistence.entity.RawFundamentalsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RawFundamentalsJpaRepository extends JpaRepository<RawFundamentalsEntity, Long> {
    List<RawFundamentalsEntity> findByStockIdOrderByReferenceDateDesc(Long stockId);
}
