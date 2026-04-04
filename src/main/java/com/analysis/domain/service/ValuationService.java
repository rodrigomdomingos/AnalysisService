package com.analysis.domain.service;

import com.analysis.domain.model.RawFundamentals;
import com.analysis.domain.model.ValuationSnapshot;
import com.analysis.domain.repository.ValuationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class ValuationService {

    private final ValuationRepository valuationRepository;
    private final Clock clock;

    public ValuationService(ValuationRepository valuationRepository, Clock clock) {
        this.valuationRepository = valuationRepository;
        this.clock = clock;
    }

    public ValuationSnapshot process(RawFundamentals raw) {
        Optional<ValuationSnapshot> existingSnapshot = valuationRepository.findByStockIdAndSnapshotDate(raw.getStockId(), raw.getReferenceDate());
        if (existingSnapshot.isPresent()) {
            return existingSnapshot.get();
        }

        ValuationSnapshot snapshot = compute(raw);
        valuationRepository.save(snapshot);
        return snapshot;
    }

    public ValuationSnapshot compute(RawFundamentals raw) {
        BigDecimal fcfYield = calculateFcfYield(raw.getFreeCashFlow(), raw.getMarketCap());
        BigDecimal peg = calculatePeg(raw.getPegRatio(), raw.getPe(), raw.getEarningsGrowth());
        BigDecimal evEbitda = null;

        return new ValuationSnapshot(
                raw.getStockId(),
                OffsetDateTime.now(clock),
                raw.getReferenceDate(),
                raw.getPe(),
                evEbitda,
                peg,
                fcfYield
        );
    }

    private BigDecimal calculateFcfYield(BigDecimal freeCashFlow, BigDecimal marketCap) {
        if (freeCashFlow == null || marketCap == null || marketCap.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return safeDivide(freeCashFlow, marketCap);
    }

    private BigDecimal calculatePeg(BigDecimal pegRatio, BigDecimal pe, BigDecimal earningsGrowth) {
        if (pegRatio != null) {
            return pegRatio;
        }
        
        if (earningsGrowth != null && earningsGrowth.compareTo(BigDecimal.ZERO) > 0 && pe != null) {
            return safeDivide(pe, earningsGrowth);
        }
        
        return null;
    }

    private BigDecimal safeDivide(BigDecimal numerator, BigDecimal denominator) {
        if (numerator == null || denominator == null || denominator.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return numerator.divide(denominator, 6, RoundingMode.HALF_UP);
    }
}
