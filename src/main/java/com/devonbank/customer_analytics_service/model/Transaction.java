package com.devonbank.customer_analytics_service.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private Long id;
    private Long accountId;
    private LocalDateTime txnTimestamp;
    private Double amount;
    private String txnType;     // DEBIT / CREDIT
    private String channel;     // UPI / NEFT / NETBANKING / ATM / POS
    private String status;      // SUCCESS / FAILED / REVERSED
    private String description;
}
