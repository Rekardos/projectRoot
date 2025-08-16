package com.service;

import com.exception.NegativeDiscriminantException;
import dev.akmedvedev.equation.solver.EquationSolverResponse;

public class QuadraticEquationSolver {
    public static EquationSolverResponse solve(double a, double b, double c) {
        double D = b * b - 4 * a * c;
        String formula = String.format("%dx^2%+dx%+d=0", (int) a, (int) b, (int) c);

        if (D < 0) {
            throw new NegativeDiscriminantException("Отрицательный дискриминант", formula, D);
        }

        EquationSolverResponse response = new EquationSolverResponse();
        response.setFormula(formula);
        response.setD(D);
        double x1 = (-b + Math.sqrt(D)) / (2 * a);
        response.setX1(x1);
        if (D > 0) {
            double x2 = (-b - Math.sqrt(D)) / (2 * a);
            response.setX2(x2);
        }
        return response;
    }
}
