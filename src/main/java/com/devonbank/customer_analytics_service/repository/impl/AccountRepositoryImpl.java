package com.devonbank.customer_analytics_service.repository.impl;

import com.devonbank.customer_analytics_service.mapper.AccountRowMapper;
import com.devonbank.customer_analytics_service.model.Account;
import com.devonbank.customer_analytics_service.repository.AccountRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public AccountRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> findAccountsByCustomer(Long customerId) {
        String sql = """
            SELECT 
                id,
                customer_id,
                account_number,
                currency,
                open_date,
                current_balance
            FROM ACCOUNT
            WHERE customer_id = ?
            ORDER BY id
        """;

        return jdbcTemplate.query(
                sql,
                new Object[]{customerId},
                new AccountRowMapper()
        );
    }
}
