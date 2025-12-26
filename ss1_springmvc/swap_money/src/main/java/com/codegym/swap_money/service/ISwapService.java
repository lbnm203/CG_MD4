package com.codegym.swap_money.service;

import com.codegym.swap_money.exception.InvalidAmountException;

public interface ISwapService {
    double swap(double rate, double usd) throws InvalidAmountException;
}
