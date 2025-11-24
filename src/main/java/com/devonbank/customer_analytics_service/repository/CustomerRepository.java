package com.devonbank.customer_analytics_service.repository;

import com.devonbank.customer_analytics_service.dto.CustomerDetailsDTO;

public interface CustomerRepository {
    CustomerDetailsDTO findCustomerById(Long customerId);
}
