package com.endpoint;

import com.service.QuadraticEquationSolver;
import dev.akmedvedev.equation.solver.EquationSolverRequest;
import dev.akmedvedev.equation.solver.EquationSolverResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EquationSolverEndpoint {

    private static final String NAMESPACE_URI = "http://akmedvedev.dev/equation/solver";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "EquationSolverRequest")
    @ResponsePayload
    public EquationSolverResponse solveEquation(@RequestPayload EquationSolverRequest request) {
        return QuadraticEquationSolver.solve(request.getA(), request.getB(), request.getC());
    }
}
