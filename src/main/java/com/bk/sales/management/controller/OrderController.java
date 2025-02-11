package com.bk.sales.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bk.sales.management.Service.OrderService;
import com.bk.sales.management.dto.OrderRequestDto;
import com.bk.sales.management.dto.OrderResponseDto;
//import com.bk.sales.management.service.OrderService;
import com.bk.sales.management.model.Order;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // ✅ Create a new sales order
    @PostMapping("/create")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderService.createOrder(orderRequestDto));
    }

    // ✅ Retrieve an order by ID
    @GetMapping("/get/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    // ✅ Update an existing sales order
    @PutMapping("/update/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable String orderId, @RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, orderRequestDto));
    }
    
    @GetMapping("/getAll")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllOrders() {
        orderService.deleteAllOrders();
        return ResponseEntity.noContent().build();
    }
}
