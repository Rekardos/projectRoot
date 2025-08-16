package com.example.soapClient.controller;

import com.example.soap.wsdl.EquationSolverResponse;
import com.example.soapClient.dto.EquationResponseDto;
import com.example.soapClient.service.EquationClient;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EquationControllerTest {
    private final EquationClient mockClient = mock(EquationClient.class);
    private final EquationController controller = new EquationController(mockClient);

    @Test
    void solve_ReturnsDto_WhenClientSucceeds() {
        EquationSolverResponse mockResponse = new EquationSolverResponse();
        mockResponse.setFormula("x^2 - 3x + 2 = 0");
        mockResponse.setD(1.0);
        mockResponse.setX1(2.0);
        mockResponse.setX2(1.0);

        when(mockClient.solve(1, -3, 2)).thenReturn(mockResponse);

        ResponseEntity<EquationResponseDto> responseEntity = controller.solve(1, -3, 2);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("x^2 - 3x + 2 = 0", responseEntity.getBody().getFormula());
    }

    @Test
    void solve_ReturnsBadRequest_WhenClientThrows() {
        when(mockClient.solve(anyDouble(), anyDouble(), anyDouble())).thenThrow(new RuntimeException("SOAP error"));

        ResponseEntity<EquationResponseDto> responseEntity = controller.solve(1, -3, 2);

        assertEquals(400, responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());
    }
}
