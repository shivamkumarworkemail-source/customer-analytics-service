package com.devonbank.customer_analytics_service.controller;

import com.devonbank.customer_analytics_service.dto.CustomerActivityOverviewResponse;
import com.devonbank.customer_analytics_service.service.ActivityOverviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerActivityOverviewController {

    private final ActivityOverviewService activityOverviewService;

    public CustomerActivityOverviewController(ActivityOverviewService activityOverviewService) {
        this.activityOverviewService = activityOverviewService;
    }

    @GetMapping("/{customerId}/activity-overview")
    public CustomerActivityOverviewResponse getActivityOverview(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "90") int days
    ) {
        return activityOverviewService.getCustomerActivityOverview(customerId, days);
    }
}

