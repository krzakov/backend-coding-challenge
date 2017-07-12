package com.engagetech.web;

import com.engagetech.domain.model.Expense;
import com.engagetech.infrastructure.taxcalculator.VatCalculalator;
import com.engagetech.service.ExpenseService;
import com.engagetech.web.dto.ExpenseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.engagetech.infrastructure.utils.DateUtils.asDate;
import static com.engagetech.infrastructure.utils.DateUtils.asLocalDate;

@RestController
@RequestMapping(value = "api/expenses", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ModelMapper modelMapper;
    private final VatCalculalator vatCalculalator;

    @Autowired
    public ExpenseController(ExpenseService expenseService,
                             VatCalculalator vatCalculalator) {
        this.expenseService = expenseService;
        this.vatCalculalator = vatCalculalator;
        this.modelMapper= new ModelMapper();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ExpenseDto> fetchExpenses(){
        return  expenseService.fetchExpenses()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseDto saveExpense(@Valid @RequestBody ExpenseDto expenseDto) {

        Expense expense = convertToEntity(expenseDto);
        return convertToDto(expenseService.saveExpense(expense));
    }

    private Expense convertToEntity(ExpenseDto expenseDto) {

        Expense entity =  modelMapper.map(expenseDto, Expense.class);
        entity.setDate(asDate(expenseDto.getDate()));
        return entity;
    }

    private ExpenseDto convertToDto(Expense expense){

        ExpenseDto dto = modelMapper.map(expense, ExpenseDto.class);
        dto.setDate(asLocalDate(expense.getDate()));
        dto.setVat(vatCalculalator.calculateVat(expense.getAmount()));
        return dto;
    }
}
