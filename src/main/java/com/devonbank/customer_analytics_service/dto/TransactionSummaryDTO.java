package com.devonbank.customer_analytics_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummaryDTO {
    private double totalCreditAmount;
    private double totalDebitAmount;
    private long totalTransactions;
    private double averageTransactionAmount;
    private String mostUsedChannel;
    private long failedTransactionCount;
}
