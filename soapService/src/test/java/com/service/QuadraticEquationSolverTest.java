package com.service;

import com.exception.NegativeDiscriminantException;
import org.junit.jupiter.api.Test;

import dev.akmedvedev.equation.solver.EquationSolverResponse;


import static org.junit.jupiter.api.Assertions.*;

class QuadraticEquationSolverTest {

    @Test
    void testSolve_TwoRealRoots() {
        EquationSolverResponse response = QuadraticEquationSolver.solve(1, -3, 2);
        assertEquals("1x^2-3x+2=0", response.getFormula());
        assertEquals(1, response.getD());
        assertEquals(2, response.getX1());
        assertEquals(1, response.getX2());
    }

    @Test
    void testSolve_OneRealRoot() {
        EquationSolverResponse response = QuadraticEquationSolver.solve(1, 2, 1);
        assertEquals("1x^2+2x+1=0", response.getFormula());
        assertEquals(0, response.getD());
        assertEquals(-1, response.getX1());
        assertNull(response.getX2());
    }

    @Test
    void testSolve_NegativeDiscriminant_Throws() {
        NegativeDiscriminantException ex = assertThrows(NegativeDiscriminantException.class, () -> {
            QuadraticEquationSolver.solve(1, 0, 1);
        });

        assertEquals("Отрицательный дискриминант", ex.getMessage());
        assertEquals("1x^2+0x+1=0", ex.getFormula());
        assertEquals(-4, ex.getD());
    }
}
