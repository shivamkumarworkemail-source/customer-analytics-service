package com.devonbank.customer_analytics_service.controller;

import com.devonbank.customer_analytics_service.dto.CustomerActivityOverviewResponse;
import com.devonbank.customer_analytics_service.service.ActivityOverviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customer Activity Overview")
@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerActivityOverviewController {

    private final ActivityOverviewService activityOverviewService;

    public CustomerActivityOverviewController(ActivityOverviewService activityOverviewService) {
        this.activityOverviewService = activityOverviewService;
    }

    @Operation(summary = "Get customer activity overview")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{customerId}/activity-overview")
    public CustomerActivityOverviewResponse getActivityOverview(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "90") int days
    ) {
        return activityOverviewService.getCustomerActivityOverview(customerId, days);
    }
}

