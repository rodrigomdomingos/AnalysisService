package com.analysis.application.exception;

public class FundamentalsNotFoundException extends RuntimeException {
    public FundamentalsNotFoundException(Long stockId) {
        super("Fundamentals not found for stock " + stockId);
    }
}
