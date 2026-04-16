package com.analysis.domain.model.signal;

public class TechnicalSignals {
    private final TrendSignal trend;
    private final RsiSignal rsi;
    private final MomentumSignal momentum;

    public TechnicalSignals(TrendSignal trend, RsiSignal rsi, MomentumSignal momentum) {
        this.trend = trend;
        this.rsi = rsi;
        this.momentum = momentum;
    }

    public TrendSignal getTrend() {
        return trend;
    }

    public RsiSignal getRsi() {
        return rsi;
    }

    public MomentumSignal getMomentum() {
        return momentum;
    }
}
