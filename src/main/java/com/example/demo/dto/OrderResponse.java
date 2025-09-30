package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderResponse {

    private Long id;
    private Long userId;
    private String product;
    private Integer quantity;
    private BigDecimal total;
    private Instant createdAt;

    public OrderResponse() {
    }

    public OrderResponse(Long id, Long userId, String product, Integer quantity, BigDecimal total, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
        this.total = total;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
