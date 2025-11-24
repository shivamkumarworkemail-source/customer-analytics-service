package com.devonbank.customer_analytics_service.repository.impl;

import com.devonbank.customer_analytics_service.mapper.TransactionRowMapper;
import com.devonbank.customer_analytics_service.model.Account;
import com.devonbank.customer_analytics_service.model.Transaction;
import com.devonbank.customer_analytics_service.repository.TransactionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Fetch transactions for last N days.
    @Override
    public List<Transaction> findTransactionsForCustomer(Long customerId, int lastNDays) {

        String sql = """
                SELECT 
                t.id,
                t.account_id,
                t.txn_type,
                t.amount,
                t.channel,
                t.status,
                t.description,        
                t.txn_timestamp
            FROM TRANSACTION t
            JOIN ACCOUNT a ON t.account_id = a.id
            WHERE a.customer_id = ?
              AND t.txn_timestamp >= SYSTIMESTAMP - (? * INTERVAL '1' DAY)
            ORDER BY t.txn_timestamp DESC
            """;

        return jdbcTemplate.query(
                sql,
                new Object[]{customerId, lastNDays},
                new TransactionRowMapper()
        );
    }


    // Fetch transactions for specific month (year + month).
    @Override
    public List<Transaction> findTransactionsForCustomerMonth(Long customerId, int year, int month) {

        String sql = """
        SELECT 
            t.id,
            t.account_id,
            t.txn_type,
            t.amount,
            t.channel,
            t.status,
            t.description,
            t.txn_timestamp
        FROM TRANSACTION t
        JOIN ACCOUNT a ON t.account_id = a.id
        WHERE a.customer_id = ?
          AND EXTRACT(YEAR FROM t.txn_timestamp) = ?
          AND EXTRACT(MONTH FROM t.txn_timestamp) = ?
        ORDER BY t.txn_timestamp DESC
    """;

        return jdbcTemplate.query(
                sql,
                new Object[]{customerId, year, month},
                new TransactionRowMapper()
        );
    }
}
