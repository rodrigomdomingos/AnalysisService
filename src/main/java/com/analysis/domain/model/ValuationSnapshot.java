package com.analysis.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ValuationSnapshot {
    private final Long stockId;
    private final OffsetDateTime snapshotAt;
    private final BigDecimal pe;
    private final BigDecimal evEbitda;
    private final BigDecimal peg;
    private final BigDecimal fcfYield;

    public ValuationSnapshot(Long stockId, OffsetDateTime snapshotAt, BigDecimal pe, BigDecimal evEbitda, BigDecimal peg, BigDecimal fcfYield) {
        this.stockId = stockId;
        this.snapshotAt = snapshotAt;
        this.pe = pe;
        this.evEbitda = evEbitda;
        this.peg = peg;
        this.fcfYield = fcfYield;
    }

    public Long getStockId() {
        return stockId;
    }

    public OffsetDateTime getSnapshotAt() {
        return snapshotAt;
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
