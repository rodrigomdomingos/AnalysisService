package com.analysis.infrastructure.persistence.repository;

import com.analysis.domain.model.PriceSnapshot;
import com.analysis.domain.repository.PriceSnapshotRepository;
import com.analysis.infrastructure.persistence.entity.StockEntity;
import com.analysis.infrastructure.persistence.mapper.PriceSnapshotMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PriceSnapshotRepositoryAdapter implements PriceSnapshotRepository {
    private final SpringDataPriceSnapshotRepository delegate;
    private final SpringDataStockRepository stockRepository;

    public PriceSnapshotRepositoryAdapter(SpringDataPriceSnapshotRepository delegate, SpringDataStockRepository stockRepository) {
        this.delegate = delegate;
        this.stockRepository = stockRepository;
    }

    @Override
    public PriceSnapshot save(PriceSnapshot snapshot) {
        StockEntity stockEntity = stockRepository.getReferenceById(snapshot.getStockId());
        return PriceSnapshotMapper.toDomain(delegate.save(PriceSnapshotMapper.toEntity(snapshot, stockEntity)));
    }
}
