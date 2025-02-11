package com.bk.sales.management.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.bk.sales.management.dto.OrderRequestDto;
import com.bk.sales.management.dto.OrderResponseDto;
import com.bk.sales.management.model.Order;
import com.bk.sales.management.reposiroty.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    // Customer Management Service & Lead Service URLs
    private static final String CUSTOMER_API_URL = "http://localhost:8081/customers/getAll/";
    private static final String LEAD_API_URL = "http://localhost:8082/leads/get/";
    private static final String CONVERT_LEAD_API_URL = "http://localhost:8082/leads/convert/";

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * Create a new order after validating the customer
     */
    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequest) {
        String customerId = orderRequest.getCustomerId();

        // 1️⃣ First, check if the customer exists in CMS
        if (!validateCustomer(customerId)) {
            // 2️⃣ If customer does NOT exist, check if it is a lead
            if (validateLead(customerId)) {
                // 3️⃣ Convert lead to customer
                customerId = convertLeadToCustomer(customerId);
                if (customerId == null) {
                    throw new RuntimeException("❌ Lead conversion failed for ID: " + orderRequest.getCustomerId());
                }
            } else {
                throw new RuntimeException("❌ Neither customer nor lead found with ID: " + orderRequest.getCustomerId());
            }
        }

        // 4️⃣ Proceed with order creation using the confirmed customerId
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setTotalAmount(calculateTotal(orderRequest));
        order.setStatus("Pending");
        order.setNotes(orderRequest.getNotes());

        Order savedOrder = orderRepository.save(order);
        return new OrderResponseDto(savedOrder);
    }

    /**
     * Validate if the customer exists in CMS
     */
    private boolean validateCustomer(String customerId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(CUSTOMER_API_URL + customerId, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false; // Customer does not exist
        }
    }

    /**
     * Validate if the lead exists in the Lead Management System
     */
    private boolean validateLead(String leadId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(LEAD_API_URL + leadId, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false; // Lead does not exist
        }
    }

    /**
     * Convert a lead into a customer
     */
    private String convertLeadToCustomer(String leadId) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(CONVERT_LEAD_API_URL + leadId, null, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody(); // Assuming response body contains new customerId
            }
        } catch (Exception e) {
            System.err.println("❌ Lead conversion failed for leadId " + leadId);
        }
        return null;
    }

    /**
     * Calculate total order amount
     */
    private double calculateTotal(OrderRequestDto orderRequest) {
        return (orderRequest.getProducts() == null) ? 0.0 :
                orderRequest.getProducts().stream()
                        .mapToDouble(p -> p.getPrice() * p.getQuantity())
                        .sum() - orderRequest.getDiscount();
    }

	@Override
	public OrderResponseDto getOrderById(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderResponseDto updateOrder(String orderId, OrderRequestDto orderRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}
}
