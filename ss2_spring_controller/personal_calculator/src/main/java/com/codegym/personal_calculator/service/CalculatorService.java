package com.codegym.personal_calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService implements ICalculatorService{
    @Override
    public double add(double aValue, double bValue) {
        return aValue + bValue;
    }

    @Override
    public double sub(double aValue, double bValue) {
        return aValue - bValue;
    }

    @Override
    public double mul(double aValue, double bValue) {
        return aValue * bValue;
    }

    @Override
    public double div(double aValue, double bValue) {
        return aValue / bValue;
    }
}
