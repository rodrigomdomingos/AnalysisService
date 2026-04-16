package com.analysis.domain.model.signal;

public class FundamentalSignals {
    private final StrengthLevel roe;
    private final RiskLevel debtEquity;
    private final StrengthLevel netMargin;

    public FundamentalSignals(StrengthLevel roe, RiskLevel debtEquity, StrengthLevel netMargin) {
        this.roe = roe;
        this.debtEquity = debtEquity;
        this.netMargin = netMargin;
    }

    public StrengthLevel getRoe() {
        return roe;
    }

    public RiskLevel getDebtEquity() {
        return debtEquity;
    }

    public StrengthLevel getNetMargin() {
        return netMargin;
    }
}
