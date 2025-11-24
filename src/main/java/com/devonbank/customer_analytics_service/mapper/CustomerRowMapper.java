package com.devonbank.customer_analytics_service.mapper;

import com.devonbank.customer_analytics_service.model.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

        Customer c = new Customer();
        c.setId(rs.getLong("ID"));
        c.setName(rs.getString("NAME"));
        c.setSegment(rs.getString("SEGMENT"));

        java.sql.Timestamp ts = rs.getTimestamp("CREATED_AT");
        if (ts != null) {
            c.setCreatedAt(ts.toLocalDateTime());
        }

        return c;
    }
}
