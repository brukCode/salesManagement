package com.bk.sales.management.dto;

import java.util.List;

import com.bk.sales.management.model.Order;
import com.bk.sales.management.model.ShippingAddress;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private String orderId;
    private String customerId;
    private List<OrderItemDto> products;
    private Double totalAmount;
    private String status;
    private Double discount;
    private ShippingAddress shippingAddress;
    private String notes;

    public OrderResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.customerId = order.getCustomerId();
        this.totalAmount = order.getTotalAmount();
        this.status = order.getStatus();
        this.discount = order.getDiscount();
        this.shippingAddress = order.getShippingAddress();
        this.notes = order.getNotes();
    }
}
