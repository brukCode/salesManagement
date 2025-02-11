package com.bk.sales.management.Service;

import java.util.List;

import com.bk.sales.management.dto.OrderRequestDto;
import com.bk.sales.management.dto.OrderResponseDto;
import com.bk.sales.management.model.Order;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);
    OrderResponseDto getOrderById(String orderId);
    OrderResponseDto updateOrder(String orderId, OrderRequestDto orderRequestDto);
    List<OrderResponseDto> getAllOrders();
   // OrderResponseDto  deleteAllOrders();
	void deleteAllOrders();
	
}