package com.bk.sales.management.dto;

import com.bk.sales.management.model.OrderItem;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private String productId;
    private Integer quantity;
    private Double price;

    public OrderItemDto(OrderItem item) {
        this.productId = item.getProductId();
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
    }
}