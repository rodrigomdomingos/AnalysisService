package com.analysis.domain.model;

import java.math.BigDecimal;

public class TechnicalSnapshot {
    private final BigDecimal rsi;
    private final BigDecimal ma50;
    private final BigDecimal ma200;

    public TechnicalSnapshot(BigDecimal rsi, BigDecimal ma50, BigDecimal ma200) {
        this.rsi = rsi;
        this.ma50 = ma50;
        this.ma200 = ma200;
    }

    public BigDecimal getRsi() {
        return rsi;
    }

    public BigDecimal getMa50() {
        return ma50;
    }

    public BigDecimal getMa200() {
        return ma200;
    }
}
