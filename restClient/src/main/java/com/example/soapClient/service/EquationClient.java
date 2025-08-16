package com.example.soapClient.service;

import com.example.soap.wsdl.EquationSolverPort;
import com.example.soap.wsdl.EquationSolverPortService;
import com.example.soap.wsdl.EquationSolverRequest;
import com.example.soap.wsdl.EquationSolverResponse;
import org.springframework.stereotype.Component;

@Component
public class EquationClient {
    private final EquationSolverPort soapClient;

    public EquationClient() {
        EquationSolverPortService service = new EquationSolverPortService();
        this.soapClient = service.getEquationSolverPortSoap11();
    }

    public EquationSolverResponse solve(double a, double b, double c) {
        EquationSolverRequest request = new EquationSolverRequest();
        request.setA(a);
        request.setB(b);
        request.setC(c);

        return soapClient.equationSolver(request);
    }
}
