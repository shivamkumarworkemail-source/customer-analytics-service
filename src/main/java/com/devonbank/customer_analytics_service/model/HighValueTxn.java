package com.devonbank.customer_analytics_service.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HighValueTxn {
    private Long id;
    private Long txnId;
    private Long accountId;
    private Double amount;
    private LocalDateTime txnTimestamp;
    private String reason;
    private LocalDateTime createdAt;
}
