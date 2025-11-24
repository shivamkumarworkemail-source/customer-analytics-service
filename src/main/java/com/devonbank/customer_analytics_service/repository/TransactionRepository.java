package com.devonbank.customer_analytics_service.repository;

import com.devonbank.customer_analytics_service.model.Transaction;

import java.util.List;

public interface TransactionRepository {

    List<Transaction> findTransactionsForCustomer(Long customerId, int lastNDays);

    List<Transaction> findTransactionsForCustomerMonth(Long customerId, int year, int month);

}
