package com.devonbank.customer_analytics_service.service;

import com.devonbank.customer_analytics_service.dto.MonthlyStatementResponse;

public interface MonthlyStatementService {
    MonthlyStatementResponse getMonthlyStatement(Long customerId, int year, int month);
}
