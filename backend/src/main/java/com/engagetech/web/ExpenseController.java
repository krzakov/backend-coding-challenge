package com.engagetech.web;

import com.engagetech.domain.model.Expense;
import com.engagetech.service.ExpenseService;
import com.engagetech.web.request.ExpenseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/expenses", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Expense>> fetchExpenses(){
        return new ResponseEntity<>(expenseService.fetchExpenses(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Expense> saveExpense(@RequestBody ExpenseRequest expenseRequest ){
        Expense expense = buildExpense(expenseRequest);
        return new ResponseEntity<>(expenseService.saveExpense(expense), HttpStatus.CREATED);
    }

    private Expense buildExpense(@RequestBody ExpenseRequest expenseRequest) {
        return Expense.builder()
                    .amount(expenseRequest.getAmount())
                    .date(expenseRequest.getDate().toDate())
                    .reason(expenseRequest.getReason())
                    .build();
    }
}
