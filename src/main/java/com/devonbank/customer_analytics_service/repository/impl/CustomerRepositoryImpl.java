package com.devonbank.customer_analytics_service.repository.impl;

import com.devonbank.customer_analytics_service.dto.CustomerDetailsDTO;
import com.devonbank.customer_analytics_service.repository.CustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CustomerDetailsDTO findCustomerById(Long customerId) {

        String sql = """
            SELECT id, name, segment
            FROM CUSTOMER
            WHERE id = ?
        """;

        return jdbcTemplate.queryForObject(sql,
                new Object[]{customerId},
                (rs, rowNum) -> new CustomerDetailsDTO(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("segment")
                ));
    }
}
