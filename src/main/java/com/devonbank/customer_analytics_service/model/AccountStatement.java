package com.devonbank.customer_analytics_service.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountStatement {
    private Long id;
    private Long accountId;
    private Integer yearNum;
    private Integer monthNum;
    private Double openingBalance;
    private Double closingBalance;
    private Double totalCredit;
    private Double totalDebit;
    private Integer txnCount;
    private LocalDateTime generatedAt;
}
