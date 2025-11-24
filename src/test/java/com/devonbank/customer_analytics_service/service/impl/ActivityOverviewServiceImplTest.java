package com.devonbank.customer_analytics_service.service.impl;

import com.devonbank.customer_analytics_service.dto.CustomerActivityOverviewResponse;
import com.devonbank.customer_analytics_service.dto.CustomerDetailsDTO;
import com.devonbank.customer_analytics_service.model.Transaction;
import com.devonbank.customer_analytics_service.repository.CustomerRepository;
import com.devonbank.customer_analytics_service.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class ActivityOverviewServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private ActivityOverviewServiceImpl activityOverviewService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomerActivityOverview_Success() {

        CustomerDetailsDTO mockCustomer = new CustomerDetailsDTO(101L, "Anil", "RETAIL");
        when(customerRepository.findCustomerById(101L)).thenReturn(mockCustomer);

        Transaction t1 = new Transaction();
        t1.setTxnType("CREDIT");
        t1.setAmount(5000.0);
        t1.setStatus("SUCCESS");
        t1.setChannel("UPI");
        t1.setTxnTimestamp(LocalDateTime.now());

        Transaction t2 = new Transaction();
        t2.setTxnType("DEBIT");
        t2.setAmount(3000.0);
        t2.setStatus("SUCCESS");
        t2.setChannel("UPI");
        t2.setTxnTimestamp(LocalDateTime.now());

        when(transactionRepository.findTransactionsForCustomer(101L, 90))
                .thenReturn(List.of(t1, t2));

        CustomerActivityOverviewResponse response =
                activityOverviewService.getCustomerActivityOverview(101L, 90);

        assertNotNull(response);
        assertEquals("Anil", response.getCustomer().getName());
        assertEquals(5000.0, response.getSummary().getTotalCreditAmount());
        assertEquals(3000.0, response.getSummary().getTotalDebitAmount());
        assertEquals(2, response.getSummary().getTotalTransactions());
        assertEquals("UPI", response.getSummary().getMostUsedChannel());
    }

    @Test
    void testGetCustomerActivityOverview_NoTransactions() {

        // Mock customer
        CustomerDetailsDTO mockCustomer = new CustomerDetailsDTO(200L, "Raj", "SME");
        when(customerRepository.findCustomerById(200L)).thenReturn(mockCustomer);

        // Return empty list
        when(transactionRepository.findTransactionsForCustomer(200L, 90))
                .thenReturn(List.of());

        CustomerActivityOverviewResponse response =
                activityOverviewService.getCustomerActivityOverview(200L, 90);

        assertNotNull(response);
        assertEquals(0, response.getSummary().getTotalTransactions());
        assertEquals(0.0, response.getSummary().getTotalCreditAmount());
        assertEquals(0.0, response.getSummary().getTotalDebitAmount());
        assertEquals("N/A", response.getSummary().getMostUsedChannel());
    }

    @Test
    void testFailedTransactionsCount() {

        when(customerRepository.findCustomerById(300L))
                .thenReturn(new CustomerDetailsDTO(300L, "Priya", "RETAIL"));

        Transaction failedTxn = new Transaction();
        failedTxn.setTxnType("DEBIT");
        failedTxn.setAmount(1000.0);
        failedTxn.setStatus("FAILED");
        failedTxn.setChannel("NETBANKING");
        failedTxn.setTxnTimestamp(LocalDateTime.now());

        when(transactionRepository.findTransactionsForCustomer(300L, 30))
                .thenReturn(List.of(failedTxn));

        CustomerActivityOverviewResponse response =
                activityOverviewService.getCustomerActivityOverview(300L, 30);

        assertEquals(1, response.getSummary().getFailedTransactionCount());
    }
}