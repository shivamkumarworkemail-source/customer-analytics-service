package com.devonbank.customer_analytics_service.mapper;

import com.devonbank.customer_analytics_service.model.HighValueTxn;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HighValueTxnRowMapper implements RowMapper<HighValueTxn> {

    @Override
    public HighValueTxn mapRow(ResultSet rs, int rowNum) throws SQLException {

        HighValueTxn hv = new HighValueTxn();
        hv.setId(rs.getLong("ID"));
        hv.setTxnId(rs.getLong("TXN_ID"));
        hv.setAccountId(rs.getLong("ACCOUNT_ID"));
        hv.setAmount(rs.getDouble("AMOUNT"));
        hv.setReason(rs.getString("REASON"));

        java.sql.Timestamp tsTxn = rs.getTimestamp("TXN_TIMESTAMP");
        if (tsTxn != null) {
            hv.setTxnTimestamp(tsTxn.toLocalDateTime());
        }

        java.sql.Timestamp tsCreated = rs.getTimestamp("CREATED_AT");
        if (tsCreated != null) {
            hv.setCreatedAt(tsCreated.toLocalDateTime());
        }

        return hv;
    }
}
