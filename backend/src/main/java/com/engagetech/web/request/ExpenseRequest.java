package com.engagetech.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

@Data
public class ExpenseRequest {

    private BigDecimal amount;

    @JsonFormat(pattern = "dd/mm/yyyy")
    private LocalDate date;

    private String reason;
}
