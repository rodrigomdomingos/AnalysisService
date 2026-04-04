package com.analysis.application.exception;

public class ValuationNotFoundException extends RuntimeException {
    public ValuationNotFoundException(Long stockId) {
        super("Valuation not found for stock " + stockId);
    }
}
