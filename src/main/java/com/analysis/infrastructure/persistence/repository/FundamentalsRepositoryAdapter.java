package com.analysis.infrastructure.persistence.repository;

import com.analysis.domain.model.FundamentalsSnapshot;
import com.analysis.domain.repository.FundamentalsRepository;
import com.analysis.infrastructure.persistence.entity.StockEntity;
import com.analysis.infrastructure.persistence.mapper.FundamentalsMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class FundamentalsRepositoryAdapter implements FundamentalsRepository {
    private final SpringDataFundamentalsRepository delegate;
    private final SpringDataStockRepository stockRepository;

    public FundamentalsRepositoryAdapter(SpringDataFundamentalsRepository delegate,
                                         SpringDataStockRepository stockRepository) {
        this.delegate = delegate;
        this.stockRepository = stockRepository;
    }

    @Override
    public Optional<FundamentalsSnapshot> findLatestByStockId(Long stockId) {
        return delegate.findFirstByStockIdOrderByDateDesc(stockId)
                .map(FundamentalsMapper::toDomain);
    }

    @Override
    public void save(FundamentalsSnapshot snapshot) {
        StockEntity stockEntity = stockRepository.findById(snapshot.getStockId())
                .orElseThrow(() -> new IllegalStateException("Stock not found for ID: " + snapshot.getStockId()));
        delegate.save(FundamentalsMapper.toEntity(snapshot, stockEntity));
    }

    @Override
    public Optional<FundamentalsSnapshot> findByStockIdAndPeriodDate(Long stockId, LocalDate referenceDate) {
        return delegate.findByStockIdAndPeriodDate(stockId, referenceDate)
                .map(FundamentalsMapper::toDomain);
    }
}
