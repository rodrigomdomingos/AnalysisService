package com.analysis.domain.service;

import com.analysis.domain.model.Price;
import com.analysis.domain.model.TechnicalSnapshot;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class TechnicalAnalysisService {
    private static final int RSI_PERIOD = 14;
    private static final int MA_50 = 50;
    private static final int MA_200 = 200;

    public TechnicalSnapshot analyze(List<Price> prices) {
        BigDecimal rsi = calculateRSI(prices);
        BigDecimal ma50 = calculateMA(prices, MA_50);
        BigDecimal ma200 = calculateMA(prices, MA_200);
        return new TechnicalSnapshot(rsi, ma50, ma200);
    }

    private BigDecimal calculateRSI(List<Price> prices) {
        if (prices == null || prices.size() <= RSI_PERIOD) {
            return null;
        }

        BigDecimal gainSum = BigDecimal.ZERO;
        BigDecimal lossSum = BigDecimal.ZERO;
        for (int i = prices.size() - RSI_PERIOD - 1; i < prices.size() - 1; i++) {
            BigDecimal change = prices.get(i + 1).getClosePrice().subtract(prices.get(i).getClosePrice());
            if (change.compareTo(BigDecimal.ZERO) > 0) {
                gainSum = gainSum.add(change);
            } else {
                lossSum = lossSum.add(change.abs());
            }
        }

        BigDecimal avgGain = gainSum.divide(BigDecimal.valueOf(RSI_PERIOD), 8, RoundingMode.HALF_UP);
        BigDecimal avgLoss = lossSum.divide(BigDecimal.valueOf(RSI_PERIOD), 8, RoundingMode.HALF_UP);

        if (avgLoss.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.valueOf(100);
        }

        BigDecimal rs = avgGain.divide(avgLoss, 8, RoundingMode.HALF_UP);
        return BigDecimal.valueOf(100).subtract(BigDecimal.valueOf(100).divide(BigDecimal.ONE.add(rs), 8, RoundingMode.HALF_UP));
    }

    private BigDecimal calculateMA(List<Price> prices, int period) {
        if (prices == null || prices.size() < period || period <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (int i = prices.size() - period; i < prices.size(); i++) {
            sum = sum.add(prices.get(i).getClosePrice());
        }
        return sum.divide(BigDecimal.valueOf(period), 8, RoundingMode.HALF_UP);
    }
}
