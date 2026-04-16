package com.analysis.application.exception;

public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(String ticker) {
        super("Stock not found for ticker: " + ticker);
    }

    public StockNotFoundException(Long stockId) {
        super("Stock not found for id: " + stockId);
    }
}
