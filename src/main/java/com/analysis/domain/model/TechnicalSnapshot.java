package com.analysis.domain.model;

import java.math.BigDecimal;

public class TechnicalSnapshot {
    private final BigDecimal closePrice;
    private final BigDecimal rsi14;
    private final BigDecimal ma50;
    private final BigDecimal ma200;

    public TechnicalSnapshot(BigDecimal closePrice, BigDecimal rsi14, BigDecimal ma50, BigDecimal ma200) {
        this.closePrice = closePrice;
        this.rsi14 = rsi14;
        this.ma50 = ma50;
        this.ma200 = ma200;
    }

    public TechnicalSnapshot(BigDecimal rsi14, BigDecimal ma50, BigDecimal ma200) {
        this(null, rsi14, ma50, ma200);
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public BigDecimal getRsi14() {
        return rsi14;
    }

    public BigDecimal getRsi() {
        return rsi14;
    }

    public BigDecimal getMa50() {
        return ma50;
    }

    public BigDecimal getMa200() {
        return ma200;
    }
}
