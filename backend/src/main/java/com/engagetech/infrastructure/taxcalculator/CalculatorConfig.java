package com.engagetech.infrastructure.taxcalculator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter
@Setter
public class CalculatorConfig {

    @Value("${taxes.vat.uk}")
    private BigDecimal rate;
}
