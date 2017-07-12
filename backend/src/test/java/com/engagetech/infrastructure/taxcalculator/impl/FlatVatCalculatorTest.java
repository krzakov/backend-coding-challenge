package com.engagetech.infrastructure.taxcalculator.impl;

import com.engagetech.infrastructure.taxcalculator.CalculatorConfig;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

public class FlatVatCalculatorTest {

    private CalculatorConfig calculatorConfig = new CalculatorConfig();
    private BigDecimal rate = new BigDecimal(0.20);
    private FlatVatCalculator flatVatCalculator;

    @BeforeSuite
    public void setUp() throws Exception {
        calculatorConfig.setRate(rate);
        flatVatCalculator = new FlatVatCalculator(calculatorConfig);
    }

    @DataProvider(name = "calculationData")
    public Object[][] provideCalculationData() {
        return new Object[][]{
                {new BigDecimal(120.00),    new BigDecimal(20.00)},
                {new BigDecimal(1.00),      new BigDecimal(0.2)},
                {new BigDecimal(520.00),    new BigDecimal(86.67)},
                {new BigDecimal(0.00),      new BigDecimal(0.00)},
                {new BigDecimal(52000.00),  new BigDecimal(8667.00)}
        };
    }

    @Test(dataProvider = "calculationData")
    public void shouldCalculateVat(BigDecimal amount, BigDecimal expected){
        //given

        //when
        BigDecimal result = flatVatCalculator.calculateVat(amount);

        //then
        assertThat(result, closeTo(expected.setScale(2, BigDecimal.ROUND_HALF_UP), new BigDecimal(0.4)));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowException(){
        //given

        //when
        BigDecimal result = flatVatCalculator.calculateVat(new BigDecimal(100.0000).setScale(3));

        //then exception
    }
}