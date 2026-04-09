package com.analysis.application.usecase;

import com.analysis.application.context.AnalysisContext;
import com.analysis.application.dto.AnalysisResponse;
import com.analysis.application.dto.FundamentalSignals;
import com.analysis.application.dto.TechnicalSignals;
import com.analysis.application.dto.ValuationSignals;
import com.analysis.application.exception.FundamentalsNotFoundException;
import com.analysis.application.exception.StockNotFoundException;
import com.analysis.application.exception.ValuationNotFoundException;
import com.analysis.domain.model.*;
import com.analysis.domain.repository.PriceRepository;
import com.analysis.domain.repository.PriceSnapshotRepository;
import com.analysis.domain.repository.RawFundamentalsRepository;
import com.analysis.domain.repository.StockRepository;
import com.analysis.domain.repository.ValuationRepository;
import com.analysis.domain.service.FundamentalsService;
import com.analysis.domain.service.SignalService;
import com.analysis.domain.service.TechnicalAnalysisService;
import com.analysis.domain.service.TimeService;
import com.analysis.domain.service.ValuationService;
import com.analysis.infrastructure.persistence.entity.FundamentalsSnapshotEntity;
import com.analysis.infrastructure.persistence.entity.SignalSnapshotEntity;
import com.analysis.infrastructure.persistence.entity.StockEntity;
import com.analysis.infrastructure.persistence.entity.ValuationSnapshotEntity;
import com.analysis.infrastructure.persistence.repository.FundamentalsSnapshotRepository;
import com.analysis.infrastructure.persistence.repository.SignalSnapshotRepository;
import com.analysis.infrastructure.persistence.repository.SpringDataStockRepository;
import com.analysis.infrastructure.persistence.repository.ValuationSnapshotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ComputeAnalysisUseCaseImpl implements ComputeAnalysisUseCase {
    private static final Logger log = LoggerFactory.getLogger(ComputeAnalysisUseCaseImpl.class);

    private final StockRepository stockRepository;
    private final PriceRepository priceRepository;
    private final ValuationRepository valuationRepository;
    private final PriceSnapshotRepository priceSnapshotRepository;
    private final TechnicalAnalysisService technicalAnalysisService;
    private final FundamentalsService fundamentalsService;
    private final ValuationService valuationService;
    private final RawFundamentalsRepository rawFundamentalsRepository;
    private final SignalService signalService;
    private final SignalSnapshotRepository signalSnapshotRepository;
    private final SpringDataStockRepository springDataStockRepository;
    private final TimeService timeService;
    private final FundamentalsSnapshotRepository fundamentalsSnapshotRepository;
    private final ValuationSnapshotRepository valuationSnapshotRepository;

    public ComputeAnalysisUseCaseImpl(StockRepository stockRepository,
                                      PriceRepository priceRepository,
                                      ValuationRepository valuationRepository,
                                      PriceSnapshotRepository priceSnapshotRepository,
                                      TechnicalAnalysisService technicalAnalysisService,
                                      FundamentalsService fundamentalsService,
                                      ValuationService valuationService,
                                      RawFundamentalsRepository rawFundamentalsRepository,
                                      SignalService signalService,
                                      SignalSnapshotRepository signalSnapshotRepository,
                                      SpringDataStockRepository springDataStockRepository,
                                      TimeService timeService,
                                      FundamentalsSnapshotRepository fundamentalsSnapshotRepository,
                                      ValuationSnapshotRepository valuationSnapshotRepository) {
        this.stockRepository = stockRepository;
        this.priceRepository = priceRepository;
        this.valuationRepository = valuationRepository;
        this.priceSnapshotRepository = priceSnapshotRepository;
        this.technicalAnalysisService = technicalAnalysisService;
        this.fundamentalsService = fundamentalsService;
        this.valuationService = valuationService;
        this.rawFundamentalsRepository = rawFundamentalsRepository;
        this.signalService = signalService;
        this.signalSnapshotRepository = signalSnapshotRepository;
        this.springDataStockRepository = springDataStockRepository;
        this.timeService = timeService;
        this.fundamentalsSnapshotRepository = fundamentalsSnapshotRepository;
        this.valuationSnapshotRepository = valuationSnapshotRepository;
    }

    @Override
    public AnalysisResponse runAnalysisForStock(String ticker) {
        log.info("Starting analysis for ticker {}", ticker);
        
        Stock stock = findStock(ticker);
        final AnalysisContext context = new AnalysisContext(stock.getId(), timeService.getUtcNow());

        List<Price> prices = loadPrices(context.getStockId());
        TechnicalSnapshot technicalSnapshot = computeTechnical(prices);
        FundamentalsSnapshot fundamentalsSnapshot = loadFundamentals(stock, context);
        ValuationSnapshot valuationSnapshot = loadValuation(context);

        PriceSnapshot priceSnapshot = buildPriceSnapshot(context, prices, technicalSnapshot);
        log.info("Price snapshot: {}", priceSnapshot);
        priceSnapshotRepository.save(priceSnapshot);
        
        TechnicalSignals technicalSignals = signalService.analyze(technicalSnapshot);
        ValuationSignals valuationSignals = signalService.analyze(valuationSnapshot);
        FundamentalSignals fundamentalSignals = signalService.analyze(fundamentalsSnapshot);
        
        SignalSnapshotEntity signalSnapshot = buildSignalSnapshot(context, valuationSignals, fundamentalSignals);
        signalSnapshotRepository.save(signalSnapshot);

        fundamentalsSnapshotRepository.save(toFundamentalsEntity(fundamentalsSnapshot, stock));
        valuationSnapshotRepository.save(toValuationEntity(valuationSnapshot, stock));

        AnalysisSnapshot analysisSnapshot = buildAnalysisSnapshot(context, technicalSnapshot, fundamentalsSnapshot, valuationSnapshot);

        log.info("Completed analysis for {} on {}", ticker, context.getSnapshotAt());
        return toResponse(stock, analysisSnapshot);
    }
    
    private SignalSnapshotEntity buildSignalSnapshot(AnalysisContext context, ValuationSignals valuationSignals, FundamentalSignals fundamentalSignals) {
        StockEntity stockEntity = springDataStockRepository.findById(context.getStockId())
            .orElseThrow(() -> new RuntimeException("StockEntity not found"));
            
        SignalSnapshotEntity entity = signalSnapshotRepository.findByStockIdAndSnapshotAt(context.getStockId(), context.getSnapshotAt())
            .orElse(new SignalSnapshotEntity());
            
        entity.setStock(stockEntity);
        entity.setSnapshotAt(context.getSnapshotAt());
        entity.setPeSignal(valuationSignals.getPe() != null ? valuationSignals.getPe().name() : null);
        entity.setPegSignal(valuationSignals.getPeg() != null ? valuationSignals.getPeg().name() : null);
        entity.setFcfYieldSignal(valuationSignals.getFcfYield() != null ? valuationSignals.getFcfYield().name() : null);
        entity.setRoeSignal(fundamentalSignals.getRoe() != null ? fundamentalSignals.getRoe().name() : null);
        entity.setDebtSignal(fundamentalSignals.getDebtEquity() != null ? fundamentalSignals.getDebtEquity().name() : null);
        entity.setNetMarginSignal(fundamentalSignals.getNetMargin() != null ? fundamentalSignals.getNetMargin().name() : null);
        entity.setScore(null);
        
        return entity;
    }

    private Stock findStock(String ticker) {
        Stock stock = stockRepository.findByTicker(ticker)
                .orElseThrow(() -> new StockNotFoundException(ticker));
        log.info("Found stock {} with id {}", stock.getTicker(), stock.getId());
        return stock;
    }

    private List<Price> loadPrices(Long stockId) {
        List<Price> prices = priceRepository.findByStockIdOrderBySnapshotAtAsc(stockId);
        log.info("Loaded {} prices for stock {}", prices.size(), stockId);
        return prices;
    }

    private TechnicalSnapshot computeTechnical(List<Price> prices) {
        return technicalAnalysisService.analyze(prices);
    }

    private FundamentalsSnapshot loadFundamentals(Stock stock, AnalysisContext context) {
        return fundamentalsService.compute(stock.getTicker(), context)
                .orElseThrow(() -> new FundamentalsNotFoundException(context.getStockId()));
    }

    private ValuationSnapshot loadValuation(AnalysisContext context) {
        List<RawFundamentals> rawFundamentalsList = rawFundamentalsRepository.findByStockIdOrderByReferenceDateDesc(context.getStockId());
        if (rawFundamentalsList.isEmpty()) {
            throw new ValuationNotFoundException(context.getStockId());
        }
        RawFundamentals latestRaw = rawFundamentalsList.get(0);

        return valuationService.process(latestRaw, context);
    }

    private PriceSnapshot buildPriceSnapshot(AnalysisContext context, List<Price> prices, TechnicalSnapshot technicalSnapshot) {
        Price latestPrice = prices.isEmpty() ? null : prices.get(prices.size() - 1);
        BigDecimal closePrice = latestPrice != null ? latestPrice.getClosePrice() : BigDecimal.ZERO;
        Long volume = latestPrice != null ? latestPrice.getVolume() : 0L;

        return new PriceSnapshot(
                context.getStockId(),
                context.getSnapshotAt(),
                closePrice,
                volume,
                technicalSnapshot.getRsi(),
                technicalSnapshot.getMa50(),
                technicalSnapshot.getMa200()
        );
    }

    private AnalysisSnapshot buildAnalysisSnapshot(AnalysisContext context, TechnicalSnapshot technicalSnapshot, FundamentalsSnapshot fundamentalsSnapshot, ValuationSnapshot valuationSnapshot) {
        return new AnalysisSnapshot(
                context.getStockId(),
                context.getSnapshotAt(),
                technicalSnapshot.getRsi(),
                technicalSnapshot.getMa50(),
                technicalSnapshot.getMa200(),
                valuationSnapshot.getPe(),
                valuationSnapshot.getEvEbitda(),
                valuationSnapshot.getPeg(),
                valuationSnapshot.getFcfYield(),
                fundamentalsSnapshot.getRevenueGrowth(),
                fundamentalsSnapshot.getNetMargin(),
                fundamentalsSnapshot.getRoe(),
                fundamentalsSnapshot.getRoic(),
                fundamentalsSnapshot.getDebtEquity(),
                fundamentalsSnapshot.getFreeCashFlow()
        );
    }

    private AnalysisResponse toResponse(Stock stock, AnalysisSnapshot snapshot) {
        return new AnalysisResponse(
                snapshot.getStockId(),
                stock.getTicker(),
                snapshot.getSnapshotAt(),
                snapshot.getRsi(),
                snapshot.getMa50(),
                snapshot.getMa200(),
                snapshot.getPe(),
                snapshot.getEvEbitda(),
                snapshot.getPeg(),
                snapshot.getFcfYield(),
                snapshot.getRevenueGrowth(),
                snapshot.getNetMargin(),
                snapshot.getRoe(),
                snapshot.getRoic(),
                snapshot.getDebtEquity(),
                snapshot.getFreeCashFlow()
        );
    }

    private FundamentalsSnapshotEntity toFundamentalsEntity(FundamentalsSnapshot snapshot, Stock stock) {
        StockEntity stockEntity = springDataStockRepository.findById(stock.getId())
                .orElseThrow(() -> new RuntimeException("StockEntity not found"));
        FundamentalsSnapshotEntity entity = new FundamentalsSnapshotEntity();
        entity.setStock(stockEntity);
        entity.setSnapshotAt(snapshot.getSnapshotAt());
        entity.setRevenueGrowth(snapshot.getRevenueGrowth());
        entity.setNetMargin(snapshot.getNetMargin());
        entity.setRoe(snapshot.getRoe());
        entity.setRoic(snapshot.getRoic());
        entity.setDebtEquity(snapshot.getDebtEquity());
        entity.setFreeCashFlow(snapshot.getFreeCashFlow());
        return entity;
    }

    private ValuationSnapshotEntity toValuationEntity(ValuationSnapshot snapshot, Stock stock) {
        StockEntity stockEntity = springDataStockRepository.findById(stock.getId())
                .orElseThrow(() -> new RuntimeException("StockEntity not found"));
        ValuationSnapshotEntity entity = new ValuationSnapshotEntity();
        entity.setStock(stockEntity);
        entity.setSnapshotAt(snapshot.getSnapshotAt());
        entity.setPeRatio(snapshot.getPe());
        entity.setEvEbitda(snapshot.getEvEbitda());
        entity.setPegRatio(snapshot.getPeg());
        entity.setFcfYield(snapshot.getFcfYield());
        return entity;
    }
}
