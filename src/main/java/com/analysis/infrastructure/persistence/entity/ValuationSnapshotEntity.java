package com.analysis.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "valuation_snapshot", schema = "investments",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_valuation_snapshot", columnNames = {"stock_id", "snapshot_date"})
        })
public class ValuationSnapshotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private StockEntity stock;

    @Column(name = "date", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    private OffsetDateTime date;

    @Column(name = "snapshot_date", nullable = false)
    private LocalDate snapshotDate;

    @Column
    private BigDecimal pe;

    @Column(name = "ev_ebitda")
    private BigDecimal evEbitda;

    @Column
    private BigDecimal peg;

    @Column(name = "fcf_yield")
    private BigDecimal fcfYield;

    protected ValuationSnapshotEntity() {
    }

    public ValuationSnapshotEntity(StockEntity stock, OffsetDateTime date, LocalDate snapshotDate, BigDecimal pe, BigDecimal evEbitda, BigDecimal peg, BigDecimal fcfYield) {
        this.stock = stock;
        this.date = date;
        this.snapshotDate = snapshotDate;
        this.pe = pe;
        this.evEbitda = evEbitda;
        this.peg = peg;
        this.fcfYield = fcfYield;
    }

    public Long getId() {
        return id;
    }

    public StockEntity getStock() {
        return stock;
    }

    public void setStock(StockEntity stock) {
        this.stock = stock;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public LocalDate getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(LocalDate snapshotDate) {
        this.snapshotDate = snapshotDate;
    }

    public BigDecimal getPe() {
        return pe;
    }

    public void setPe(BigDecimal pe) {
        this.pe = pe;
    }

    public BigDecimal getEvEbitda() {
        return evEbitda;
    }

    public void setEvEbitda(BigDecimal evEbitda) {
        this.evEbitda = evEbitda;
    }

    public BigDecimal getPeg() {
        return peg;
    }

    public void setPeg(BigDecimal peg) {
        this.peg = peg;
    }

    public BigDecimal getFcfYield() {
        return fcfYield;
    }

    public void setFcfYield(BigDecimal fcfYield) {
        this.fcfYield = fcfYield;
    }
}
