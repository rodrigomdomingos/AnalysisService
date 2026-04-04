package com.analysis.infrastructure.persistence.mapper;

import com.analysis.domain.model.RawFundamentals;
import com.analysis.infrastructure.persistence.entity.RawFundamentalsEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class RawFundamentalsMapper {

    public RawFundamentals toDomain(RawFundamentalsEntity entity) {
        BigDecimal marketCap = asBigDecimal(entity.getMarketCap());
        BigDecimal priceToBook = asBigDecimal(entity.getPriceToBook());
        BigDecimal derivedEquity = deriveEquity(marketCap, priceToBook);
        BigDecimal pe = asBigDecimal(entity.getPe());
        BigDecimal earningsGrowth = asBigDecimal(entity.getEarningsGrowth());
        BigDecimal pegRatio = derivePegRatio(pe, earningsGrowth);

        return new RawFundamentals(
                entity.getStock().getId(),
                entity.getReferenceDate(),
                asBigDecimal(entity.getRevenue()),
                asBigDecimal(entity.getNetIncome()),
                asBigDecimal(entity.getDebt()),
                derivedEquity,
                asBigDecimal(entity.getOperatingCashflow()),
                asBigDecimal(entity.getRevenueGrowth()),
                asBigDecimal(entity.getRoe()),
                asBigDecimal(entity.getRoa()),
                asBigDecimal(entity.getProfitMargin()),
                pegRatio,
                marketCap,
                pe,
                earningsGrowth
        );
    }

    private BigDecimal asBigDecimal(Double value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }

    private BigDecimal deriveEquity(BigDecimal marketCap, BigDecimal priceToBook) {
        if (marketCap == null || priceToBook == null || priceToBook.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return marketCap.divide(priceToBook, 6, RoundingMode.HALF_UP);
    }

    private BigDecimal derivePegRatio(BigDecimal pe, BigDecimal earningsGrowth) {
        if (pe == null || earningsGrowth == null || earningsGrowth.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return pe.divide(earningsGrowth, 4, RoundingMode.HALF_UP);
    }
}
