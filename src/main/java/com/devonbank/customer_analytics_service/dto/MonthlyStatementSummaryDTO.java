package com.devonbank.customer_analytics_service.dto;

import lombok.Data;

@Data
public class MonthlyStatementSummaryDTO {
    private double openingBalance;
    private double closingBalance;
    private double totalCredit;
    private double totalDebit;
    private long totalTransactions;
}
