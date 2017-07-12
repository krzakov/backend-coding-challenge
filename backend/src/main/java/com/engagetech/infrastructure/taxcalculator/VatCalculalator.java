package com.engagetech.infrastructure.taxcalculator;

import java.math.BigDecimal;

public interface VatCalculalator {

    BigDecimal calculateVat(BigDecimal amountInclusive);
}
