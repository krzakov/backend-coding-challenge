package com.engagetech.service.impl;


import com.engagetech.domain.model.Expense;
import com.engagetech.repository.ExpenseRepository;
import com.engagetech.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> fetchExpenses(){
        return expenseRepository.findAll();
    }

    @Override
    public Expense saveExpense(Expense expense){
        return expenseRepository.save(expense);
    }
}
