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
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "price_snapshot", schema = "investments")
public class PriceSnapshotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private StockEntity stock;

    @Column(name = "date", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    private OffsetDateTime date;

    @Column(name = "snapshot_date", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    private OffsetDateTime snapshotDate;

    @Column(name = "close_price", nullable = false)
    private BigDecimal closePrice;

    @Column
    private Long volume;

    @Column
    private BigDecimal rsi;

    @Column
    private BigDecimal ma50;

    @Column
    private BigDecimal ma200;

    @Column(name = "above_ma_200")
    private Boolean aboveMa200;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    protected PriceSnapshotEntity() {
    }

    public PriceSnapshotEntity(StockEntity stock, OffsetDateTime date, OffsetDateTime snapshotDate, BigDecimal closePrice, Long volume, BigDecimal rsi, BigDecimal ma50, BigDecimal ma200, Boolean aboveMa200) {
        this.stock = stock;
        this.date = date;
        this.snapshotDate = snapshotDate;
        this.closePrice = closePrice;
        this.volume = volume;
        this.rsi = rsi;
        this.ma50 = ma50;
        this.ma200 = ma200;
        this.aboveMa200 = aboveMa200;
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

    public OffsetDateTime getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(OffsetDateTime snapshotDate) {
        this.snapshotDate = snapshotDate;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public BigDecimal getRsi() {
        return rsi;
    }

    public void setRsi(BigDecimal rsi) {
        this.rsi = rsi;
    }

    public BigDecimal getMa50() {
        return ma50;
    }

    public void setMa50(BigDecimal ma50) {
        this.ma50 = ma50;
    }

    public BigDecimal getMa200() {
        return ma200;
    }

    public void setMa200(BigDecimal ma200) {
        this.ma200 = ma200;
    }

    public Boolean getAboveMa200() {
        return aboveMa200;
    }

    public void setAboveMa200(Boolean aboveMa200) {
        this.aboveMa200 = aboveMa200;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
