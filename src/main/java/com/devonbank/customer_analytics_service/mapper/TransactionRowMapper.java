package com.devonbank.customer_analytics_service.mapper;

import com.devonbank.customer_analytics_service.model.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<Transaction> {

    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {

        Transaction t = new Transaction();
        t.setId(rs.getLong("ID"));
        t.setAccountId(rs.getLong("ACCOUNT_ID"));
        t.setAmount(rs.getDouble("AMOUNT"));
        t.setTxnType(rs.getString("TXN_TYPE"));
        t.setChannel(rs.getString("CHANNEL"));
        t.setStatus(rs.getString("STATUS"));
        t.setDescription(rs.getString("DESCRIPTION"));

        java.sql.Timestamp ts = rs.getTimestamp("TXN_TIMESTAMP");
        if (ts != null) {
            t.setTxnTimestamp(ts.toLocalDateTime());
        }

        return t;
    }
}
