package com.bk.sales.management.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private static final String CUSTOMER_API_URL = "http://localhost:8222/customers/get/";
    private static final String LEAD_API_URL = "http://localhost:8111/leads/get/";
    private static final String CONVERT_LEAD_API_URL = "http://localhost:8111/leads/convert/";

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequest) {
        String customerId = orderRequest.getCustomerId();

        // 1️⃣ Check if Customer Exists
        if (!isCustomerExists(customerId)) {
            // 2️⃣ If not, check if Lead Exists
            if (isLeadExists(customerId)) {
                // 3️⃣ Convert Lead to Customer
                customerId = convertLeadToCustomer(customerId);
                if (customerId == null) {
                    throw new RuntimeException("❌ Lead conversion failed for ID: " + orderRequest.getCustomerId());
                }
            } else {
                throw new RuntimeException("❌ No Customer or Lead found with ID: " + orderRequest.getCustomerId());
            }
        }

        // 4️⃣ Create Order with Verified Customer
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setTotalAmount(calculateTotal(orderRequest));
        order.setStatus("Pending");
        order.setNotes(orderRequest.getNotes());

        Order savedOrder = orderRepository.save(order);
        return new OrderResponseDto(savedOrder);
    }

    private boolean isCustomerExists(String customerId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(CUSTOMER_API_URL + customerId, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isLeadExists(String leadId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(LEAD_API_URL + leadId, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    private String convertLeadToCustomer(String leadId) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(CONVERT_LEAD_API_URL + leadId, null, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody(); // Returns new customer ID
            }
        } catch (Exception e) {
            System.err.println("❌ Lead conversion failed for leadId " + leadId);
        }
        return null;
    }

    private double calculateTotal(OrderRequestDto orderRequest) {
        return orderRequest.getProducts().stream()
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

	@Override
	public List<OrderResponseDto> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllOrders() {
		// TODO Auto-generated method stub
		
	}
}
