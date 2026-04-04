package com.analysis.infrastructure.persistence.repository;

import com.analysis.domain.model.ValuationSnapshot;
import com.analysis.domain.repository.ValuationRepository;
import com.analysis.infrastructure.persistence.entity.StockEntity;
import com.analysis.infrastructure.persistence.mapper.ValuationMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class ValuationRepositoryAdapter implements ValuationRepository {
    private final SpringDataValuationRepository delegate;
    private final SpringDataStockRepository stockRepository;

    public ValuationRepositoryAdapter(SpringDataValuationRepository delegate, SpringDataStockRepository stockRepository) {
        this.delegate = delegate;
        this.stockRepository = stockRepository;
    }

    @Override
    public Optional<ValuationSnapshot> findLatestByStockId(Long stockId) {
        return delegate.findFirstByStockIdOrderByDateDesc(stockId)
                .map(ValuationMapper::toDomain);
    }
    
    @Override
    public void save(ValuationSnapshot snapshot) {
        StockEntity stockEntity = stockRepository.findById(snapshot.getStockId())
                .orElseThrow(() -> new IllegalStateException("Stock not found for ID: " + snapshot.getStockId()));
        delegate.save(ValuationMapper.toEntity(snapshot, stockEntity));
    }

    @Override
    public Optional<ValuationSnapshot> findByStockIdAndSnapshotDate(Long stockId, LocalDate snapshotDate) {
        return delegate.findByStockIdAndSnapshotDate(stockId, snapshotDate)
                .map(ValuationMapper::toDomain);
    }
}
