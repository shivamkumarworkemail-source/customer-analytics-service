package com.devonbank.customer_analytics_service.controller;

import com.devonbank.customer_analytics_service.dto.MonthlyStatementResponse;
import com.devonbank.customer_analytics_service.service.MonthlyStatementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Customer Monthly Statement")
@RestController
@Slf4j
@RequestMapping("/customers")
public class MonthlyStatementController {

    private final MonthlyStatementService monthlyStatementService;

    public MonthlyStatementController(MonthlyStatementService monthlyStatementService) {
        this.monthlyStatementService = monthlyStatementService;
    }

    @GetMapping("/{customerId}/statement")
    public MonthlyStatementResponse getStatement(
            @PathVariable Long customerId,
            @RequestParam int year,
            @RequestParam int month) {
        return monthlyStatementService.getMonthlyStatement(customerId, year, month);
    }
}
