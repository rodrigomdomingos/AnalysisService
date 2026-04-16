package com.analysis.infrastructure.messaging;

import com.analysis.application.event.MarketDataUpdatedEvent;
import com.analysis.application.usecase.ComputeAnalysisUseCase;
import com.analysis.infrastructure.config.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MarketDataUpdatedConsumer {
    private static final Logger log = LoggerFactory.getLogger(MarketDataUpdatedConsumer.class);

    private final ComputeAnalysisUseCase computeAnalysisUseCase;

    public MarketDataUpdatedConsumer(ComputeAnalysisUseCase computeAnalysisUseCase) {
        this.computeAnalysisUseCase = computeAnalysisUseCase;
    }

    @RabbitListener(
            queues = RabbitMqConfig.MARKET_DATA_UPDATED_QUEUE,
            containerFactory = "analysisRabbitListenerContainerFactory"
    )
    public void onMarketDataUpdated(MarketDataUpdatedEvent event) {
        try (MDC.MDCCloseable ignoredStockId = MDC.putCloseable("stockId", String.valueOf(event.stockId()));
             MDC.MDCCloseable ignoredSnapshotAt = MDC.putCloseable("snapshotAt", String.valueOf(event.snapshotAt()))) {
            log.info("Received market data update event");
            computeAnalysisUseCase.execute(event.stockId(), event.snapshotAt());
            log.info("Analysis pipeline triggered");
        }
    }
}
