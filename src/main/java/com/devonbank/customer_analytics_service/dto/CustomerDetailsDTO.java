package com.devonbank.customer_analytics_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailsDTO {
    private Long id;
    private String name;
    private String segment;
}
