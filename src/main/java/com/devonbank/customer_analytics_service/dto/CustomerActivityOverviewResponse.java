package com.devonbank.customer_analytics_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerActivityOverviewResponse {
    private CustomerDetailsDTO customer;
    private TransactionSummaryDTO summary;
}
