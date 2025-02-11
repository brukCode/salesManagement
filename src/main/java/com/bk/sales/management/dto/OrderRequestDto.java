package com.bk.sales.management.dto;



import com.bk.sales.management.model.ShippingAddress;
import lombok.*;



import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {
	//private Long idLong;
    private String customerId;
    private List<OrderItemDto> products;
    private Double discount;
    private ShippingAddress shippingAddress;
    private String notes;
	public String getStatus() {
		// TODO Auto-generated method stub
		return null;
	}
}
