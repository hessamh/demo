package com.example.demo.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CreateOrderRequest {

    @NotNull(message = "{order.userId.not-null}")
    @Positive(message = "{order.userId.positive}")
    private Long userId;
    @NotBlank(message = "{order.product.not-blank}")
    @Size(max = 100, message = "{order.product.size}")
    private String product;
    @NotNull(message = "{order.quantity.not-null}")
    @Min(value = 1, message = "{order.quantity.min}")
    private Integer quantity;
    @NotNull(message = "{order.total.not-null}")
    @DecimalMin(value = "0.0", inclusive = true, message = "{order.total.min}")
    @Digits(integer = 12, fraction = 2, message = "{order.total.digits}")
    private BigDecimal total;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(Long userId, String product, Integer quantity, BigDecimal total) {
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
        this.total = total;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
