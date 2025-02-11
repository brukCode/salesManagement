package com.bk.sales.management.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private Integer quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // ✅ Added reference to Order
}