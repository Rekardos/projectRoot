package com.example.soapClient.controller;

import com.example.soap.wsdl.EquationSolverResponse;
import com.example.soapClient.dto.EquationResponseDto;
import com.example.soapClient.service.EquationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calc")
@RequiredArgsConstructor
public class EquationController {

    private final EquationClient client;

    @GetMapping
    public ResponseEntity<EquationResponseDto> solve(
            @RequestParam double a,
            @RequestParam double b,
            @RequestParam double c) {

        try {
            EquationSolverResponse response = client.solve(a, b, c);

            EquationResponseDto dto = new EquationResponseDto(
                    response.getFormula(),
                    response.getD(),
                    response.getX1(),
                    response.getX2()
            );

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
