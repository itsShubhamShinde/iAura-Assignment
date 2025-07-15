package com.example.demo.controllers;

import com.example.demo.dto.ExpenseDTO;
import com.example.demo.entity.Expense;
import com.example.demo.entity.User;
import com.example.demo.services.AuthService;
import com.example.demo.services.ExpanseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expenses")
public class ExpanseController {

    @Autowired
    private ExpanseService expenseService;

    @Autowired
    private AuthService authService;

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses(HttpSession session) {
        User user = authService.getCurrentUser(session);
        if (user == null) return ResponseEntity.status(401).build();

        List<Expense> expenses = expenseService.getUserExpenses(user);
        List<ExpenseDTO> expenseDTOs = expenses.stream()
                .map(expense -> new ExpenseDTO(
                        expense.getId(),
                        expense.getCategory(),
                        expense.getAmount(),
                        expense.getComment(),
                        user.getId(),
                        expense.getCreatedAt(),
                        expense.getUpdatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(expenseDTOs);
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody Expense expense, HttpSession session) {
        User user = authService.getCurrentUser(session);
        if (user == null) return ResponseEntity.status(401).build();

        Expense createdExpense = expenseService.createExpense(expense, user);
        return ResponseEntity.ok(new ExpenseDTO(
                createdExpense.getId(),
                createdExpense.getCategory(),
                createdExpense.getAmount(),
                createdExpense.getComment(),
                user.getId(),
                createdExpense.getCreatedAt(),
                createdExpense.getUpdatedAt()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getExpense(@PathVariable Long id, HttpSession session) {
        User user = authService.getCurrentUser(session);
        if (user == null) return ResponseEntity.status(401).build();

        Expense expense = expenseService.getExpenseById(id, user);
        if (expense == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new ExpenseDTO(
                expense.getId(),
                expense.getCategory(),
                expense.getAmount(),
                expense.getComment(),
                user.getId(),
                expense.getCreatedAt(),
                expense.getUpdatedAt()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateExpense(
            @PathVariable Long id,
            @RequestBody Expense expenseDetails,
            HttpSession session) {
        User user = authService.getCurrentUser(session);
        if (user == null) return ResponseEntity.status(401).build();

        Expense updatedExpense = expenseService.updateExpense(id, expenseDetails, user);
        if (updatedExpense == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new ExpenseDTO(
                updatedExpense.getId(),
                updatedExpense.getCategory(),
                updatedExpense.getAmount(),
                updatedExpense.getComment(),
                user.getId(),
                updatedExpense.getCreatedAt(),
                updatedExpense.getUpdatedAt()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id, HttpSession session) {
        User user = authService.getCurrentUser(session);
        if (user == null) return ResponseEntity.status(401).build();

        boolean deleted = expenseService.deleteExpense(id, user);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
