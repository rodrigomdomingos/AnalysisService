package com.analysis.infrastructure.persistence.repository;

import com.analysis.domain.model.PriceSnapshot;
import com.analysis.domain.repository.PriceSnapshotRepository;
import com.analysis.infrastructure.persistence.mapper.PriceSnapshotMapper;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

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
        return PriceSnapshotMapper.toDomain(delegate.save(PriceSnapshotMapper.toEntity(snapshot, stockRepository.getReferenceById(snapshot.getStockId()))));
    }

    @Override
    public Optional<PriceSnapshot> findByStockIdAndSnapshotAt(Long stockId, OffsetDateTime snapshotAt) {
        return delegate.findByStockIdAndSnapshotAt(stockId, snapshotAt)
                .map(PriceSnapshotMapper::toDomain);
    }
}
