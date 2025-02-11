package com.bk.sales.management.reposiroty;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bk.sales.management.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderId(String orderId);
}
