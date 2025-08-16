package com.endpoint;

import dev.akmedvedev.equation.solver.EquationSolverRequest;
import dev.akmedvedev.equation.solver.EquationSolverResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquationSolverEndpointTest {

    private final EquationSolverEndpoint endpoint = new EquationSolverEndpoint();

    @Test
    void testSolveEquation() {
        EquationSolverRequest request = new EquationSolverRequest();
        request.setA(1);
        request.setB(-3);
        request.setC(2);

        EquationSolverResponse response = endpoint.solveEquation(request);

        assertEquals("1x^2-3x+2=0", response.getFormula());
        assertEquals(1, response.getD());
        assertEquals(2, response.getX1());
        assertEquals(1, response.getX2());
    }
}
