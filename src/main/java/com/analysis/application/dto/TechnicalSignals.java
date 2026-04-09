package com.analysis.application.dto;

import com.analysis.domain.model.signal.MomentumSignal;
import com.analysis.domain.model.signal.RsiSignal;
import com.analysis.domain.model.signal.TrendSignal;

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
