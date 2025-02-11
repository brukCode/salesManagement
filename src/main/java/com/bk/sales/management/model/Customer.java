package com.bk.sales.management.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phone;
}
