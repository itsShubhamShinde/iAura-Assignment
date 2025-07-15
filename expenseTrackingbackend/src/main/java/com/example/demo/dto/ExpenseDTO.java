package com.example.demo.dto;

import java.time.LocalDateTime;

public class ExpenseDTO {
    private Long id;
    private String category;
    private long amount;
    private String comment;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ExpenseDTO() {}

    public ExpenseDTO(Long id, String category, long amount, String comment, Long userId,
                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.comment = comment;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
