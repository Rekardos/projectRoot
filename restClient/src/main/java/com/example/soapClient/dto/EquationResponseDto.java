package com.example.soapClient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquationResponseDto {
    private String formula;
    private double D;
    private double x1;
    private double x2;
}
