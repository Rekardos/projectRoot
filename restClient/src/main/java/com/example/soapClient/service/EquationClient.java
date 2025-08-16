package com.example.soapClient.service;

import com.example.soap.wsdl.EquationSolverPort;
import com.example.soap.wsdl.EquationSolverRequest;
import com.example.soap.wsdl.EquationSolverResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquationClient {
    private final EquationSolverPort soapClient;

    public EquationSolverResponse solve(double a, double b, double c) {
        EquationSolverRequest request = new EquationSolverRequest();
        request.setA(a);
        request.setB(b);
        request.setC(c);

        return soapClient.equationSolver(request);
    }
}
