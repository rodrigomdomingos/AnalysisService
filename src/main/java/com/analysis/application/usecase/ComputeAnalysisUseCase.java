package com.analysis.application.usecase;

import com.analysis.application.dto.AnalysisResponse;

import java.time.OffsetDateTime;

public interface ComputeAnalysisUseCase {
    AnalysisResponse runAnalysisForStock(String ticker);

    void execute(Long stockId, OffsetDateTime snapshotAt);
}
