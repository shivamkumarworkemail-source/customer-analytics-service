package com.devonbank.customer_analytics_service.service.impl;

import com.devonbank.customer_analytics_service.dto.CustomerDetailsDTO;
import com.devonbank.customer_analytics_service.dto.MonthlyStatementResponse;
import com.devonbank.customer_analytics_service.model.Account;
import com.devonbank.customer_analytics_service.model.Transaction;
import com.devonbank.customer_analytics_service.repository.AccountRepository;
import com.devonbank.customer_analytics_service.repository.CustomerRepository;
import com.devonbank.customer_analytics_service.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MonthlyStatementServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private MonthlyStatementServiceImpl monthlyStatementService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMonthlyStatement_Success() {

        CustomerDetailsDTO mockCustomer = new CustomerDetailsDTO(101L, "Anil", "RETAIL");
        when(customerRepository.findCustomerById(101L)).thenReturn(mockCustomer);

        Account acc1 = new Account();
        acc1.setAccountNumber("ACC1001");

        Account acc2 = new Account();
        acc2.setAccountNumber("ACC2001");

        when(accountRepository.findAccountsByCustomer(101L))
                .thenReturn(List.of(acc1, acc2));

        Transaction t1 = new Transaction();
        t1.setTxnType("CREDIT");
        t1.setAmount(5000.0);
        t1.setTxnTimestamp(LocalDateTime.now());

        Transaction t2 = new Transaction();
        t2.setTxnType("DEBIT");
        t2.setAmount(2000.0);
        t2.setTxnTimestamp(LocalDateTime.now());

        when(transactionRepository.findTransactionsForCustomerMonth(101L, 2025, 1))
                .thenReturn(List.of(t1, t2));

        MonthlyStatementResponse response =
                monthlyStatementService.getMonthlyStatement(101L, 2025, 1);

        assertNotNull(response);
        assertEquals("Anil", response.getCustomer().getName());
        assertEquals(2, response.getAccountNumbers().size());
        assertEquals(2, response.getSummary().getTotalTransactions());

        assertEquals(5000.0, response.getSummary().getTotalCredit());
        assertEquals(2000.0, response.getSummary().getTotalDebit());

        assertEquals(300.0, response.getSummary().getOpeningBalance());


        assertEquals(3300.0, response.getSummary().getClosingBalance());

        // Ensure transaction list is limited correctly
        assertEquals(2, response.getTransactions().size());
    }


    @Test
    void testGetMonthlyStatement_NoTransactions() {

        // Mock customer
        when(customerRepository.findCustomerById(200L))
                .thenReturn(new CustomerDetailsDTO(200L, "Raj", "SME"));

        // Mock accounts
        Account acc = new Account();
        acc.setAccountNumber("ACC500");
        when(accountRepository.findAccountsByCustomer(200L))
                .thenReturn(List.of(acc));

        // No transactions
        when(transactionRepository.findTransactionsForCustomerMonth(200L, 2025, 1))
                .thenReturn(List.of());

        MonthlyStatementResponse response =
                monthlyStatementService.getMonthlyStatement(200L, 2025, 1);

        assertNotNull(response);
        assertEquals("Raj", response.getCustomer().getName());
        assertEquals(1, response.getAccountNumbers().size());
        assertEquals(0, response.getSummary().getTotalTransactions());
        assertEquals(0.0, response.getSummary().getTotalCredit());
        assertEquals(0.0, response.getSummary().getTotalDebit());
        assertEquals(0.0, response.getSummary().getOpeningBalance());
        assertEquals(0.0, response.getSummary().getClosingBalance());
    }

}