package com.devonbank.customer_analytics_service.service.impl;

import com.devonbank.customer_analytics_service.dto.CustomerDetailsDTO;
import com.devonbank.customer_analytics_service.dto.MonthlyStatementResponse;
import com.devonbank.customer_analytics_service.dto.MonthlyStatementSummaryDTO;
import com.devonbank.customer_analytics_service.exception.CustomerNotFoundException;
import com.devonbank.customer_analytics_service.model.Account;
import com.devonbank.customer_analytics_service.model.Transaction;
import com.devonbank.customer_analytics_service.repository.AccountRepository;
import com.devonbank.customer_analytics_service.repository.CustomerRepository;
import com.devonbank.customer_analytics_service.repository.TransactionRepository;
import com.devonbank.customer_analytics_service.service.MonthlyStatementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MonthlyStatementServiceImpl implements MonthlyStatementService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public MonthlyStatementServiceImpl(
            CustomerRepository customerRepository,
            AccountRepository accountRepository,
            TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public MonthlyStatementResponse getMonthlyStatement(Long customerId, int year, int month) {

        log.info("Fetching monthly statement for customerId={}, year={}, month={}", customerId, year, month);

        if (month < 1 || month > 12) {
            log.warn("Invalid month parameter: {}", month);
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        }

        if (year < 1900 || year > LocalDate.now().getYear()) {
            log.warn("Invalid year parameter: {}", year);
            throw new IllegalArgumentException("Invalid year.");
        }

        CustomerDetailsDTO customer = customerRepository.findCustomerById(customerId);
        log.debug("Customer details fetched: {}", customer);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }

        // All accounts of the customer
        List<String> accountNumbers = accountRepository.findAccountsByCustomer(customerId)
                .stream()
                .map(Account::getAccountNumber)
                .collect(Collectors.toList());

        log.debug("Found {} accounts for customer {}", accountNumbers.size(), customerId);

        // Monthly transactions
        List<Transaction> txns =
                transactionRepository.findTransactionsForCustomerMonth(customerId, year, month);

        log.debug("Fetched {} transactions for statement", txns.size());

        // Compute Summary
        double totalCredit = txns.stream()
                .filter(t -> "CREDIT".equalsIgnoreCase(t.getTxnType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalDebit = txns.stream()
                .filter(t -> "DEBIT".equalsIgnoreCase(t.getTxnType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        long txnCount = txns.size();

        double netMovement = totalCredit - totalDebit;
        double openingBalance = Math.max(netMovement * 0.10, 0);
        double closingBalance = openingBalance + netMovement;


        MonthlyStatementSummaryDTO summary = new MonthlyStatementSummaryDTO();
        summary.setOpeningBalance(openingBalance);
        summary.setClosingBalance(closingBalance);
        summary.setTotalCredit(totalCredit);
        summary.setTotalDebit(totalDebit);
        summary.setTotalTransactions(txnCount);

        // Last 50 transactions
        List<Transaction> last50 = txns.stream()
                .limit(50)
                .collect(Collectors.toList());

        MonthlyStatementResponse response = new MonthlyStatementResponse();
        response.setCustomer(customer);
        response.setAccountNumbers(accountNumbers);
        response.setSummary(summary);
        response.setTransactions(last50);

        log.info("Monthly statement generated for customer {}", customerId);

        return response;
    }
}
