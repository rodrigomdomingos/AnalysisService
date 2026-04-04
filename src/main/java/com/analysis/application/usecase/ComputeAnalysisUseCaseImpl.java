package com.analysis.application.usecase;

import com.analysis.application.dto.AnalysisResponse;
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
import com.analysis.domain.service.TechnicalAnalysisService;
import com.analysis.domain.service.ValuationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;
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
    private final Clock clock;

    public ComputeAnalysisUseCaseImpl(StockRepository stockRepository,
                                      PriceRepository priceRepository,
                                      ValuationRepository valuationRepository,
                                      PriceSnapshotRepository priceSnapshotRepository,
                                      TechnicalAnalysisService technicalAnalysisService,
                                      FundamentalsService fundamentalsService,
                                      ValuationService valuationService,
                                      RawFundamentalsRepository rawFundamentalsRepository,
                                      Clock clock) {
        this.stockRepository = stockRepository;
        this.priceRepository = priceRepository;
        this.valuationRepository = valuationRepository;
        this.priceSnapshotRepository = priceSnapshotRepository;
        this.technicalAnalysisService = technicalAnalysisService;
        this.fundamentalsService = fundamentalsService;
        this.valuationService = valuationService;
        this.rawFundamentalsRepository = rawFundamentalsRepository;
        this.clock = clock;
    }

    @Override
    public AnalysisResponse runAnalysisForStock(String ticker) {
        log.info("Starting analysis for ticker {}", ticker);
        final OffsetDateTime analysisDate = OffsetDateTime.now(clock);

        Stock stock = findStock(ticker);
        List<Price> prices = loadPrices(stock.getId());
        TechnicalSnapshot technicalSnapshot = computeTechnical(prices);
        FundamentalsSnapshot fundamentalsSnapshot = loadFundamentals(stock);
        ValuationSnapshot valuationSnapshot = loadValuation(stock.getId());

        PriceSnapshot priceSnapshot = buildPriceSnapshot(stock.getId(), analysisDate, prices, technicalSnapshot);
        log.info("Price snapshot: {}", priceSnapshot);
        priceSnapshotRepository.save(priceSnapshot);

        AnalysisSnapshot analysisSnapshot = buildAnalysisSnapshot(stock.getId(), analysisDate, technicalSnapshot, fundamentalsSnapshot, valuationSnapshot);

        log.info("Completed analysis for {} on {}", ticker, analysisDate);
        return toResponse(stock, analysisSnapshot);
    }

    private Stock findStock(String ticker) {
        Stock stock = stockRepository.findByTicker(ticker)
                .orElseThrow(() -> new StockNotFoundException(ticker));
        log.info("Found stock {} with id {}", stock.getTicker(), stock.getId());
        return stock;
    }

    private List<Price> loadPrices(Long stockId) {
        List<Price> prices = priceRepository.findByStockIdOrderByDateAsc(stockId);
        log.info("Loaded {} prices for stock {}", prices.size(), stockId);
        return prices;
    }

    private TechnicalSnapshot computeTechnical(List<Price> prices) {
        return technicalAnalysisService.analyze(prices);
    }

    private FundamentalsSnapshot loadFundamentals(Stock stock) {
        return fundamentalsService.compute(stock.getId(), stock.getTicker())
                .orElseThrow(() -> new FundamentalsNotFoundException(stock.getId()));
    }

    private ValuationSnapshot loadValuation(Long stockId) {
        List<RawFundamentals> rawFundamentalsList = rawFundamentalsRepository.findByStockIdOrderByReferenceDateDesc(stockId);
        if (rawFundamentalsList.isEmpty()) {
            throw new ValuationNotFoundException(stockId);
        }
        RawFundamentals latestRaw = rawFundamentalsList.get(0);

        return valuationService.process(latestRaw);
    }

    private PriceSnapshot buildPriceSnapshot(Long stockId, OffsetDateTime analysisDate, List<Price> prices, TechnicalSnapshot technicalSnapshot) {
        Price latestPrice = prices.isEmpty() ? null : prices.get(prices.size() - 1);
        BigDecimal closePrice = latestPrice != null ? latestPrice.getClosePrice() : BigDecimal.ZERO;
        Long volume = latestPrice != null ? latestPrice.getVolume() : 0L;
        OffsetDateTime snapshotDate = latestPrice != null ? latestPrice.getDate() : analysisDate;


        return new PriceSnapshot(stockId, analysisDate, snapshotDate, closePrice, volume, technicalSnapshot.getRsi(), technicalSnapshot.getMa50(), technicalSnapshot.getMa200());
    }

    private AnalysisSnapshot buildAnalysisSnapshot(Long stockId, OffsetDateTime analysisDate, TechnicalSnapshot technicalSnapshot, FundamentalsSnapshot fundamentalsSnapshot, ValuationSnapshot valuationSnapshot) {
        return new AnalysisSnapshot(
                stockId,
                analysisDate,
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
                snapshot.getDate(),
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
}
