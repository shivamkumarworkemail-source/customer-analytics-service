package com.devonbank.customer_analytics_service.mapper;

import com.devonbank.customer_analytics_service.model.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account a = new Account();
        a.setId(rs.getLong("ID"));
        a.setCustomerId(rs.getLong("CUSTOMER_ID"));
        a.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
        a.setCurrency(rs.getString("CURRENCY"));
        a.setCurrentBalance(rs.getDouble("CURRENT_BALANCE"));

        java.sql.Timestamp ts = rs.getTimestamp("OPEN_DATE");
        if (ts != null) {
            a.setOpenDate(ts.toLocalDateTime());
        }

        return a;
    }
}