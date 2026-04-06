package com.analysis.domain.model.signal;

public class ValuationSignals {
    private final ValuationLevel pe;
    private final StrengthLevel fcfYield;
    private final GrowthValuationLevel peg;

    public ValuationSignals(ValuationLevel pe, StrengthLevel fcfYield, GrowthValuationLevel peg) {
        this.pe = pe;
        this.fcfYield = fcfYield;
        this.peg = peg;
    }

    public ValuationLevel getPe() {
        return pe;
    }

    public StrengthLevel getFcfYield() {
        return fcfYield;
    }

    public GrowthValuationLevel getPeg() {
        return peg;
    }
}
