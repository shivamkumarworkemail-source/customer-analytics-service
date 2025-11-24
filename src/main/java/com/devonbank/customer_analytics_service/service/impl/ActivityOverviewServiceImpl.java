package com.devonbank.customer_analytics_service.service.impl;

import com.devonbank.customer_analytics_service.dto.CustomerActivityOverviewResponse;
import com.devonbank.customer_analytics_service.dto.CustomerDetailsDTO;
import com.devonbank.customer_analytics_service.dto.TransactionSummaryDTO;
import com.devonbank.customer_analytics_service.model.Transaction;
import com.devonbank.customer_analytics_service.repository.CustomerRepository;
import com.devonbank.customer_analytics_service.repository.TransactionRepository;
import com.devonbank.customer_analytics_service.service.ActivityOverviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Service
public class ActivityOverviewServiceImpl implements ActivityOverviewService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public ActivityOverviewServiceImpl(CustomerRepository customerRepository,
                                       TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public CustomerActivityOverviewResponse getCustomerActivityOverview(Long customerId, int days) {

        // 1. Fetch Customer
        CustomerDetailsDTO customer = customerRepository.findCustomerById(customerId);

        // 2. Fetch last N days transactions
        List<Transaction> transactions =
                transactionRepository.findTransactionsForCustomer(customerId, days);

        // 3. Streams Aggregation
        double totalCredit = transactions.stream()
                .filter(t -> "CREDIT".equalsIgnoreCase(t.getTxnType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalDebit = transactions.stream()
                .filter(t -> "DEBIT".equalsIgnoreCase(t.getTxnType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        long totalCount = transactions.size();

        double averageAmount = transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .average()
                .orElse(0.0);

        // Most frequently used channel
        String mostFrequentChannel = transactions.stream()
                .filter(t -> t.getChannel() != null)
                .collect(groupingBy(Transaction::getChannel, counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        long failedCount = transactions.stream()
                .filter(t -> "FAILED".equalsIgnoreCase(t.getStatus()))
                .count();

        // 4️. Prepare Summary DTO
        TransactionSummaryDTO summaryDTO = new TransactionSummaryDTO(
                totalCredit,
                totalDebit,
                totalCount,
                averageAmount,
                mostFrequentChannel,
                failedCount
        );

        // 5️. Final Response DTO
        return new CustomerActivityOverviewResponse(customer, summaryDTO);
    }
}
