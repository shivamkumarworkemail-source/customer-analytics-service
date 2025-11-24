package com.devonbank.customer_analytics_service.repository;

import com.devonbank.customer_analytics_service.model.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAccountsByCustomer(Long customerId);
}
