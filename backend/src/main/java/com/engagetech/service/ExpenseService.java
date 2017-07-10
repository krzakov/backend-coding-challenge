package com.engagetech.service;

import com.engagetech.domain.model.Expense;

import java.util.List;

public interface ExpenseService {

    List<Expense> fetchExpenses();
    Expense saveExpense(Expense expense);
}
