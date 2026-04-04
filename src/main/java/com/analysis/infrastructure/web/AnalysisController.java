package com.analysis.infrastructure.web;

import com.analysis.application.dto.AnalysisResponse;
import com.analysis.application.exception.StockNotFoundException;
import com.analysis.application.usecase.ComputeAnalysisUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    private static final Logger log = LoggerFactory.getLogger(AnalysisController.class);

    private final ComputeAnalysisUseCase computeAnalysisUseCase;

    public AnalysisController(ComputeAnalysisUseCase computeAnalysisUseCase) {
        this.computeAnalysisUseCase = computeAnalysisUseCase;
    }

    @PostMapping("/run/{ticker}")
    public ResponseEntity<AnalysisResponse> runAnalysis(@PathVariable String ticker) {
        log.info("Received analysis request for {}", ticker);
        AnalysisResponse response = computeAnalysisUseCase.runAnalysisForStock(ticker);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<String> handleStockNotFound(StockNotFoundException ex) {
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
