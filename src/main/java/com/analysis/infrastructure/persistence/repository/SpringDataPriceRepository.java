package com.analysis.infrastructure.persistence.repository;

import com.analysis.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {
    List<PriceEntity> findByStockIdOrderByDateAsc(Long stockId);
}
