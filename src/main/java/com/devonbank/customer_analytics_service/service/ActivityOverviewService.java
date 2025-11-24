package com.devonbank.customer_analytics_service.service;

import com.devonbank.customer_analytics_service.dto.CustomerActivityOverviewResponse;

public interface ActivityOverviewService {
    CustomerActivityOverviewResponse getCustomerActivityOverview(Long customerId, int days);
}
