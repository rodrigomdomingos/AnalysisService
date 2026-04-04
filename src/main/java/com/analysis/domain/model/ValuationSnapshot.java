package com.analysis.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class ValuationSnapshot {
    private final Long stockId;
    private final OffsetDateTime date;
    private final LocalDate snapshotDate;
    private final BigDecimal pe;
    private final BigDecimal evEbitda;
    private final BigDecimal peg;
    private final BigDecimal fcfYield;

    public ValuationSnapshot(Long stockId, OffsetDateTime date, LocalDate snapshotDate, BigDecimal pe, BigDecimal evEbitda, BigDecimal peg, BigDecimal fcfYield) {
        this.stockId = stockId;
        this.date = date;
        this.snapshotDate = snapshotDate;
        this.pe = pe;
        this.evEbitda = evEbitda;
        this.peg = peg;
        this.fcfYield = fcfYield;
    }

    public Long getStockId() {
        return stockId;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public LocalDate getSnapshotDate() {
        return snapshotDate;
    }

    public BigDecimal getPe() {
        return pe;
    }

    public BigDecimal getEvEbitda() {
        return evEbitda;
    }

    public BigDecimal getPeg() {
        return peg;
    }

    public BigDecimal getFcfYield() {
        return fcfYield;
    }
}
