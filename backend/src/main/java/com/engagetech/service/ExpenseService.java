package com.engagetech.service;

import com.engagetech.domain.model.Expense;

import java.util.List;

public interface ExpenseService {

    /**
     * Gets all the Expenses from the database
     */
    List<Expense> fetchExpenses();

    /**
     * Persists expense into database
     */
    Expense saveExpense(Expense expense);
}
