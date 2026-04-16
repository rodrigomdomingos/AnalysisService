package com.analysis.infrastructure.persistence.repository;

import com.analysis.domain.model.Stock;
import com.analysis.domain.repository.StockRepository;
import com.analysis.infrastructure.persistence.mapper.StockMapper;
import com.analysis.infrastructure.persistence.entity.StockEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StockRepositoryAdapter implements StockRepository {
    private final SpringDataStockRepository delegate;

    public StockRepositoryAdapter(SpringDataStockRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public Optional<Stock> findByTicker(String ticker) {
        return delegate.findByTicker(ticker).map(StockMapper::toDomain);
    }

    @Override
    public Optional<Stock> findById(Long stockId) {
        return delegate.findById(stockId).map(StockMapper::toDomain);
    }
}
