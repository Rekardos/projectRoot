package com.exception;

public class NegativeDiscriminantException extends RuntimeException{
    private final String formula;
    private final double D;

    public NegativeDiscriminantException(String message, String formula, double d) {
        super(message);
        this.formula = formula;
        this.D = d;
    }

    public String getFormula() {
        return formula;
    }

    public double getD() {
        return D;
    }
}
