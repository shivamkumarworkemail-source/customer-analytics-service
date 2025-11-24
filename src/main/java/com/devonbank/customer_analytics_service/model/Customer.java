package com.devonbank.customer_analytics_service.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Customer {
    private Long id;
    private String name;
    private String segment;   // RETAIL / SME / CORPORATE
    private LocalDateTime createdAt;
}
