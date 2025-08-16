package com.example.soapClient.client;

import com.example.soap.wsdl.EquationSolverPort;
import com.example.soap.wsdl.EquationSolverResponse;
import com.example.soapClient.service.EquationClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EquationClientTest {
    private EquationSolverPort mockSoapPort;
    private EquationClient client;

    @BeforeEach
    void setUp() {
        mockSoapPort = mock(EquationSolverPort.class);
        client = new EquationClient(mockSoapPort);
    }

    @Test
    void solve_ReturnsExpectedResponse() {
        EquationSolverResponse mockResponse = new EquationSolverResponse();
        mockResponse.setFormula("1x^2-3x+2=0");
        mockResponse.setD(1.0);
        mockResponse.setX1(2.0);
        mockResponse.setX2(1.0);

        when(mockSoapPort.equationSolver(any())).thenReturn(mockResponse);

        EquationSolverResponse response = client.solve(1, -3, 2);

        assertNotNull(response);
        assertEquals("1x^2-3x+2=0", response.getFormula());
        assertEquals(1.0, response.getD());
        assertEquals(2.0, response.getX1());
        assertEquals(1.0, response.getX2());

        verify(mockSoapPort, times(1)).equationSolver(any());
    }
}
