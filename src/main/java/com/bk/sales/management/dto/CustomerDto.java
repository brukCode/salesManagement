package com.bk.sales.management.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private String customerId;
    private String name;
    private String email;
    private String phone;
}