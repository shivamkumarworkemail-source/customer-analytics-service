package com.devonbank.customer_analytics_service.dto;

import com.devonbank.customer_analytics_service.model.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class MonthlyStatementResponse {
    private CustomerDetailsDTO customer;
    private List<String> accountNumbers;
    private MonthlyStatementSummaryDTO summary;
    private List<Transaction> transactions;
}
