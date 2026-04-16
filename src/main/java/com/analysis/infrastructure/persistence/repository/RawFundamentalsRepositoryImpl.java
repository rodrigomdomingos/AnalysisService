package com.analysis.infrastructure.persistence.repository;

import com.analysis.domain.model.RawFundamentals;
import com.analysis.domain.repository.RawFundamentalsRepository;
import com.analysis.infrastructure.persistence.mapper.RawFundamentalsMapper;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RawFundamentalsRepositoryImpl implements RawFundamentalsRepository {

    private final RawFundamentalsJpaRepository jpaRepository;
    private final RawFundamentalsMapper mapper;

    public RawFundamentalsRepositoryImpl(RawFundamentalsJpaRepository jpaRepository, RawFundamentalsMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<RawFundamentals> findByStockIdOrderByReferenceDateDesc(Long stockId) {
        return jpaRepository.findByStockIdOrderByReferenceDateDesc(stockId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<RawFundamentals> findLatestByStockIdAndDate(Long stockId, OffsetDateTime snapshotAt) {
        return jpaRepository.findByStockIdAndReferenceDateLessThanEqualOrderByReferenceDateDesc(stockId, snapshotAt)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
