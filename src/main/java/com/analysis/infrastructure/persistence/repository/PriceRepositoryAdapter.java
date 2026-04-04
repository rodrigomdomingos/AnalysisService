package com.analysis.infrastructure.persistence.repository;

import com.analysis.domain.model.Price;
import com.analysis.domain.repository.PriceRepository;
import com.analysis.infrastructure.persistence.mapper.PriceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PriceRepositoryAdapter implements PriceRepository {
    private final SpringDataPriceRepository delegate;

    public PriceRepositoryAdapter(SpringDataPriceRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Price> findByStockIdOrderByDateAsc(Long stockId) {
        return delegate.findByStockIdOrderByDateAsc(stockId).stream()
                .map(PriceMapper::toDomain)
                .collect(Collectors.toList());
    }
}
