package com.devonbank.customer_analytics_service.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Account {
    private Long id;
    private Long customerId;
    private String accountNumber;
    private String currency;
    private LocalDateTime openDate;
    private Double currentBalance;
}
