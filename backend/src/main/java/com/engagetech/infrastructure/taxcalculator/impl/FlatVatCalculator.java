package com.engagetech.infrastructure.taxcalculator.impl;

import com.engagetech.infrastructure.taxcalculator.CalculatorConfig;
import com.engagetech.infrastructure.taxcalculator.VatCalculalator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FlatVatCalculator implements VatCalculalator {

    private static final int SCALE_LENGTH = 2;

    private final CalculatorConfig calculatorConfig;

    @Autowired
    public FlatVatCalculator(CalculatorConfig calculatorConfig) {
        this.calculatorConfig = calculatorConfig;
    }

    @Override
    public BigDecimal calculateVat(BigDecimal amountInclusive) {

        validateAmount(amountInclusive);

        final BigDecimal roundedAmountInclusive = amountInclusive.setScale(SCALE_LENGTH, BigDecimal.ROUND_HALF_UP);
        final BigDecimal divisor = BigDecimal.ONE.add(calculatorConfig.getRate());

        return roundedAmountInclusive.subtract(roundedAmountInclusive.divide(divisor, BigDecimal.ROUND_HALF_UP)
                ).setScale(SCALE_LENGTH, BigDecimal.ROUND_HALF_UP);
    }

    private void validateAmount(BigDecimal amountInclusive){
        if(amountInclusive.scale() > SCALE_LENGTH){
            throw new IllegalArgumentException(String.format("Input Amount cannot have more than %s decimal places", SCALE_LENGTH));
        }
    }
}
