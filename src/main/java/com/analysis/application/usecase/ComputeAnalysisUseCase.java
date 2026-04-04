package com.analysis.application.usecase;

import com.analysis.application.dto.AnalysisResponse;

public interface ComputeAnalysisUseCase {
    AnalysisResponse runAnalysisForStock(String ticker);
}
