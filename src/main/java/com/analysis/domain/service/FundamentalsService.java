package com.analysis.domain.service;

import com.analysis.application.context.AnalysisContext;
import com.analysis.domain.model.FundamentalsSnapshot;
import com.analysis.domain.model.RawFundamentals;
import com.analysis.domain.repository.FundamentalsRepository;
import com.analysis.domain.repository.RawFundamentalsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class FundamentalsService {
    private static final Logger log = LoggerFactory.getLogger(FundamentalsService.class);

    private final FundamentalsRepository fundamentalsRepository;
    private final RawFundamentalsRepository rawFundamentalsRepository;

    public FundamentalsService(FundamentalsRepository fundamentalsRepository,
                               RawFundamentalsRepository rawFundamentalsRepository) {
        this.fundamentalsRepository = fundamentalsRepository;
        this.rawFundamentalsRepository = rawFundamentalsRepository;
    }

    public Optional<FundamentalsSnapshot> compute(String ticker, AnalysisContext context) {
        log.info("Computing fundamentals for stockId={} ticker={}", context.getStockId(), ticker);
        List<RawFundamentals> rawFundamentals = rawFundamentalsRepository.findLatestByStockIdAndDate(context.getStockId(), context.getSnapshotAt());

        if (rawFundamentals.isEmpty()) {
            log.warn("No raw fundamentals found for stockId={}", context.getStockId());
            return Optional.empty();
        }

        RawFundamentals latest = rawFundamentals.get(0);
        RawFundamentals previous = rawFundamentals.size() > 1 ? rawFundamentals.get(1) : null;

        Optional<FundamentalsSnapshot> existingSnapshot = fundamentalsRepository.findByStockIdAndSnapshotAt(context.getStockId(), context.getSnapshotAt());
        if (existingSnapshot.isPresent()) {
            log.info("Returning existing snapshot for stockId={} and date={}", context.getStockId(), context.getSnapshotAt());
            return existingSnapshot;
        }

        BigDecimal revenueGrowth = firstNonNull(
                latest.getRevenueGrowth(),
                calculateRevenueGrowth(latest.getRevenue(), previous == null ? null : previous.getRevenue())
        );
        BigDecimal netMargin = firstNonNull(
                latest.getProfitMargin(),
                calculateNetMargin(latest.getNetIncome(), latest.getRevenue())
        );
        BigDecimal roe = firstNonNull(
                latest.getRoe(),
                calculateRoe(latest.getNetIncome(), latest.getEquity())
        );
        BigDecimal roic = calculateRoic(latest.getNetIncome(), latest.getDebt(), latest.getEquity());
        BigDecimal debtEquity = calculateDebtToEquity(latest.getDebt(), latest.getEquity());
        BigDecimal freeCashFlow = toZero(latest.getFreeCashFlow());

        FundamentalsSnapshot computedSnapshot = new FundamentalsSnapshot(
                context.getStockId(),
                context.getSnapshotAt(),
                toZero(revenueGrowth),
                toZero(netMargin),
                toZero(roe),
                toZero(roic),
                toZero(debtEquity),
                freeCashFlow
        );

        fundamentalsRepository.save(computedSnapshot);
        log.info("Saved new fundamentals snapshot for stockId={}", context.getStockId());

        return Optional.of(computedSnapshot);
    }

    private BigDecimal calculateNetMargin(BigDecimal netIncome, BigDecimal revenue) {
        if (netIncome == null || revenue == null || revenue.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return netIncome.divide(revenue, 4, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDebtToEquity(BigDecimal debt, BigDecimal equity) {
        if (debt == null || equity == null || equity.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return debt.divide(equity, 4, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateRoic(BigDecimal netIncome, BigDecimal debt, BigDecimal equity) {
        if (netIncome == null || debt == null || equity == null) {
            return null;
        }
        BigDecimal investedCapital = debt.add(equity);
        if (investedCapital.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return netIncome.divide(investedCapital, 4, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateRoe(BigDecimal netIncome, BigDecimal equity) {
        if (netIncome == null || equity == null || equity.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return netIncome.divide(equity, 4, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateRevenueGrowth(BigDecimal latestRevenue, BigDecimal previousRevenue) {
        if (latestRevenue == null || previousRevenue == null || previousRevenue.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return latestRevenue.subtract(previousRevenue)
                .divide(previousRevenue, 4, RoundingMode.HALF_UP);
    }

    private BigDecimal toZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private BigDecimal firstNonNull(BigDecimal primary, BigDecimal fallback) {
        return primary != null ? primary : fallback;
    }
}
