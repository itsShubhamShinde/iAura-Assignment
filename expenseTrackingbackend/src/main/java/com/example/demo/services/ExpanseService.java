package com.example.demo.services;

import com.example.demo.entity.Expense;
import com.example.demo.entity.User;
import com.example.demo.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExpanseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getUserExpenses(User user) {
        return expenseRepository.findByUser(user);
    }

    public Expense getExpenseById(Long id, User user) {
        return expenseRepository.findByIdAndUser(id, user).orElse(null);
    }

    public Expense createExpense(Expense expense, User user) {
        expense.setUser(user);
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense expenseDetails, User user) {
        Expense expense = getExpenseById(id, user);
        if (expense == null) return null;

        expense.setCategory(expenseDetails.getCategory());
        expense.setAmount(expenseDetails.getAmount());
        expense.setComment(expenseDetails.getComment());

        return expenseRepository.save(expense);
    }

    public boolean deleteExpense(Long id, User user) {
        Expense expense = getExpenseById(id, user);
        if (expense != null) {
            expenseRepository.delete(expense);
            return true;
        }
        return false;
    }
}
