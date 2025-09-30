package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.List;

public class UserWithOrdersDto {

    private Long id;
    private String name;
    private List<OrderSummaryDto> orders;

    public UserWithOrdersDto() {
    }

    public UserWithOrdersDto(Long id, String name, List<OrderSummaryDto> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<OrderSummaryDto> getOrders() {
        return orders;
    }

    public static class OrderSummaryDto {
        private Long id;
        private String product;
        private BigDecimal total;

        public OrderSummaryDto() {
        }

        public OrderSummaryDto(Long id, String product, BigDecimal total) {
            this.id = id;
            this.product = product;
            this.total = total;
        }

        public Long getId() {
            return id;
        }

        public String getProduct() {
            return product;
        }

        public BigDecimal getTotal() {
            return total;
        }
    }
}
