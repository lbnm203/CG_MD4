package com.codegym.swap_money.service;

import com.codegym.swap_money.exception.InvalidAmountException;
import org.springframework.stereotype.Service;

@Service
public class SwapService implements ISwapService {

    @Override
    public double swap(double rate, double usd) throws InvalidAmountException {
        if (rate <= 0) {
            throw new InvalidAmountException("Tỉ giá phải lớn hơn 0");
        }
        
        if (usd <= 0) {
            throw new InvalidAmountException("Số tiền USD phải lớn hơn 0");
        }
        
        return rate * usd;
    }
}
