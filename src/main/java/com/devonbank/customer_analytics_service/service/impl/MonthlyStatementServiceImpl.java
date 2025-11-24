package com.devonbank.customer_analytics_service.service.impl;

import com.devonbank.customer_analytics_service.dto.CustomerDetailsDTO;
import com.devonbank.customer_analytics_service.dto.MonthlyStatementResponse;
import com.devonbank.customer_analytics_service.dto.MonthlyStatementSummaryDTO;
import com.devonbank.customer_analytics_service.model.Account;
import com.devonbank.customer_analytics_service.model.Transaction;
import com.devonbank.customer_analytics_service.repository.AccountRepository;
import com.devonbank.customer_analytics_service.repository.CustomerRepository;
import com.devonbank.customer_analytics_service.repository.TransactionRepository;
import com.devonbank.customer_analytics_service.service.MonthlyStatementService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        CustomerDetailsDTO customer = customerRepository.findCustomerById(customerId);

        // All accounts of the customer
        List<String> accountNumbers = accountRepository.findAccountsByCustomer(customerId)
                .stream()
                .map(Account::getAccountNumber)
                .collect(Collectors.toList());

        // Monthly transactions
        List<Transaction> txns =
                transactionRepository.findTransactionsForCustomerMonth(customerId, year, month);

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

        return response;
    }
}
