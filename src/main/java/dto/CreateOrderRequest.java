package dto;

import java.math.BigDecimal;

public class CreateOrderRequest {

    private Long userId;
    private String product;
    private Integer quantity;
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
