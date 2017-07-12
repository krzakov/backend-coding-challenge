package com.engagetech.infrastructure;

import com.engagetech.domain.model.Expense;
import com.engagetech.web.dto.ExpenseDto;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class MapperTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void mapperShouldMapToDto() throws Exception{

        //when
        Expense entity = Expense.builder()
                .amount(TEN)
                .reason("Reason")
                .build();

        ExpenseDto mapped = modelMapper.map(entity, ExpenseDto.class);

        assertThat(mapped.getAmount(),  is(TEN));
        assertThat(mapped.getReason(),  is("Reason"));
        assertThat(mapped.getDate(),    is(nullValue()));
        assertThat(mapped.getVat(),     is(nullValue()));
    }

    @Test
    public void mapperShouldMapToEntity() throws Exception{

        //when
        ExpenseDto dto = ExpenseDto.builder()
                .amount(ONE)
                .reason("Reason")
                .build();

        Expense mapped = modelMapper.map(dto, Expense.class);

        assertThat(mapped.getAmount(),  is(ONE));
        assertThat(mapped.getReason(),  is("Reason"));
        assertThat(mapped.getDate(),    is(nullValue()));
    }
}
