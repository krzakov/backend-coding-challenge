package com.engagetech.web.dto;

import com.engagetech.infrastructure.constans.ApplicationConstans;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDto {

    private Long entityId;

    @NotNull
    private BigDecimal amount;

    @JsonFormat(pattern = ApplicationConstans.DATE_PATTERN)
    @NotNull
    private LocalDate date;

    @NotNull
    private String reason;

    private BigDecimal vat;
}
