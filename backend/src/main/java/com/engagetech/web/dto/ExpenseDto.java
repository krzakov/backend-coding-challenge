package com.engagetech.web.dto;

import com.engagetech.infrastructure.constans.ApplicationConstans;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDto {

    private Long entityId;

    @NotNull
    @DecimalMax("999999.99")
    private BigDecimal amount;

    @JsonFormat(pattern = ApplicationConstans.DATE_PATTERN)
    @NotNull
    private LocalDate date;

    @NotNull
    @Size(max = 500)
    private String reason;

    private BigDecimal vat;
}
