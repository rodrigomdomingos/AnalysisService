package com.analysis.domain.service;

import com.analysis.domain.model.FundamentalsSnapshot;
import com.analysis.domain.model.TechnicalSnapshot;
import com.analysis.domain.model.ValuationSnapshot;
import com.analysis.domain.model.signal.FundamentalSignals;
import com.analysis.domain.model.signal.GrowthValuationLevel;
import com.analysis.domain.model.signal.MomentumSignal;
import com.analysis.domain.model.signal.RiskLevel;
import com.analysis.domain.model.signal.RsiSignal;
import com.analysis.domain.model.signal.StrengthLevel;
import com.analysis.domain.model.signal.TechnicalSignals;
import com.analysis.domain.model.signal.TrendSignal;
import com.analysis.domain.model.signal.ValuationLevel;
import com.analysis.domain.model.signal.ValuationSignals;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SignalService {
    private static final BigDecimal ZERO = BigDecimal.ZERO;

    private static final BigDecimal THIRTY = BigDecimal.valueOf(30);
    private static final BigDecimal SEVENTY = BigDecimal.valueOf(70);

    private static final BigDecimal TEN = BigDecimal.TEN;
    private static final BigDecimal TWENTY = BigDecimal.valueOf(20);
    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final BigDecimal TWO = BigDecimal.valueOf(2);

    private static final BigDecimal FCF_YIELD_STRONG = new BigDecimal("0.08");
    private static final BigDecimal FCF_YIELD_OK = new BigDecimal("0.04");

    private static final BigDecimal STRONG_THRESHOLD = new BigDecimal("0.15");
    private static final BigDecimal OK_THRESHOLD = new BigDecimal("0.10");
    private static final BigDecimal NET_MARGIN_OK_THRESHOLD = new BigDecimal("0.05");

    private static final BigDecimal DEBT_SAFE = new BigDecimal("0.5");
    private static final BigDecimal DEBT_MODERATE = ONE;

    public TechnicalSignals analyze(TechnicalSnapshot technical) {
        if (technical == null) {
            return new TechnicalSignals(TrendSignal.UNKNOWN, RsiSignal.UNKNOWN, MomentumSignal.UNKNOWN);
        }

        TrendSignal trend = analyzeTrend(technical.getClosePrice(), technical.getMa200());
        RsiSignal rsi = analyzeRsi(technical.getRsi14());
        MomentumSignal momentum = analyzeMomentum(technical.getMa50(), technical.getMa200());

        return new TechnicalSignals(trend, rsi, momentum);
    }

    public ValuationSignals analyze(ValuationSnapshot valuation) {
        if (valuation == null) {
            return new ValuationSignals(ValuationLevel.UNKNOWN, StrengthLevel.UNKNOWN, GrowthValuationLevel.UNKNOWN);
        }

        ValuationLevel pe = analyzePe(valuation.getPe());
        StrengthLevel fcfYield = analyzeFcfYield(valuation.getFcfYield());
        GrowthValuationLevel peg = analyzePeg(valuation.getPeg());

        return new ValuationSignals(pe, fcfYield, peg);
    }

    public FundamentalSignals analyze(FundamentalsSnapshot fundamentals) {
        if (fundamentals == null) {
            return new FundamentalSignals(StrengthLevel.UNKNOWN, RiskLevel.UNKNOWN, StrengthLevel.UNKNOWN);
        }

        StrengthLevel roe = analyzeRoe(fundamentals.getRoe());
        RiskLevel debtEquity = analyzeDebtEquity(fundamentals.getDebtEquity());
        StrengthLevel netMargin = analyzeNetMargin(fundamentals.getNetMargin());

        return new FundamentalSignals(roe, debtEquity, netMargin);
    }

    private TrendSignal analyzeTrend(BigDecimal closePrice, BigDecimal ma200) {
        if (closePrice == null || ma200 == null) {
            return TrendSignal.UNKNOWN;
        }
        if (closePrice.compareTo(ma200) > 0) {
            return TrendSignal.BULLISH;
        }
        if (closePrice.compareTo(ma200) < 0) {
            return TrendSignal.BEARISH;
        }
        return TrendSignal.UNKNOWN;
    }

    private RsiSignal analyzeRsi(BigDecimal rsi) {
        if (rsi == null) {
            return RsiSignal.UNKNOWN;
        }
        if (rsi.compareTo(THIRTY) < 0) {
            return RsiSignal.OVERSOLD;
        }
        if (rsi.compareTo(SEVENTY) <= 0) {
            return RsiSignal.NEUTRAL;
        }
        return RsiSignal.OVERBOUGHT;
    }

    private MomentumSignal analyzeMomentum(BigDecimal ma50, BigDecimal ma200) {
        if (ma50 == null || ma200 == null) {
            return MomentumSignal.UNKNOWN;
        }
        if (ma50.compareTo(ma200) > 0) {
            return MomentumSignal.UPTREND;
        }
        if (ma50.compareTo(ma200) < 0) {
            return MomentumSignal.DOWNTREND;
        }
        return MomentumSignal.UNKNOWN;
    }

    private ValuationLevel analyzePe(BigDecimal pe) {
        if (pe == null || pe.compareTo(ZERO) < 0) {
            return ValuationLevel.UNKNOWN;
        }
        if (pe.compareTo(TEN) < 0) {
            return ValuationLevel.CHEAP;
        }
        if (pe.compareTo(TWENTY) <= 0) {
            return ValuationLevel.FAIR;
        }
        return ValuationLevel.EXPENSIVE;
    }

    private StrengthLevel analyzeFcfYield(BigDecimal fcfYield) {
        if (fcfYield == null) {
            return StrengthLevel.UNKNOWN;
        }
        if (fcfYield.compareTo(FCF_YIELD_STRONG) > 0) {
            return StrengthLevel.STRONG;
        }
        if (fcfYield.compareTo(FCF_YIELD_OK) >= 0) {
            return StrengthLevel.OK;
        }
        return StrengthLevel.WEAK;
    }

    private GrowthValuationLevel analyzePeg(BigDecimal peg) {
        if (peg == null) {
            return GrowthValuationLevel.UNKNOWN;
        }
        if (peg.compareTo(ONE) < 0) {
            return GrowthValuationLevel.UNDERVALUED;
        }
        if (peg.compareTo(TWO) <= 0) {
            return GrowthValuationLevel.FAIR;
        }
        return GrowthValuationLevel.OVERVALUED;
    }

    private StrengthLevel analyzeRoe(BigDecimal roe) {
        if (roe == null) {
            return StrengthLevel.UNKNOWN;
        }
        if (roe.compareTo(STRONG_THRESHOLD) > 0) {
            return StrengthLevel.STRONG;
        }
        if (roe.compareTo(OK_THRESHOLD) >= 0) {
            return StrengthLevel.OK;
        }
        return StrengthLevel.WEAK;
    }

    private RiskLevel analyzeDebtEquity(BigDecimal debtEquity) {
        if (debtEquity == null) {
            return RiskLevel.UNKNOWN;
        }
        if (debtEquity.compareTo(DEBT_SAFE) < 0) {
            return RiskLevel.SAFE;
        }
        if (debtEquity.compareTo(DEBT_MODERATE) <= 0) {
            return RiskLevel.MODERATE;
        }
        return RiskLevel.RISKY;
    }

    private StrengthLevel analyzeNetMargin(BigDecimal netMargin) {
        if (netMargin == null) {
            return StrengthLevel.UNKNOWN;
        }
        if (netMargin.compareTo(STRONG_THRESHOLD) > 0) {
            return StrengthLevel.STRONG;
        }
        if (netMargin.compareTo(NET_MARGIN_OK_THRESHOLD) >= 0) {
            return StrengthLevel.OK;
        }
        return StrengthLevel.WEAK;
    }
}
