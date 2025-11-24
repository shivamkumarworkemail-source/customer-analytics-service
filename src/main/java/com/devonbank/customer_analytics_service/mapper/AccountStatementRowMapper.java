package com.devonbank.customer_analytics_service.mapper;

import com.devonbank.customer_analytics_service.model.AccountStatement;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountStatementRowMapper implements RowMapper<AccountStatement> {

    @Override
    public AccountStatement mapRow(ResultSet rs, int rowNum) throws SQLException {

        AccountStatement as = new AccountStatement();

        as.setId(rs.getLong("ID"));
        as.setAccountId(rs.getLong("ACCOUNT_ID"));
        as.setYearNum(rs.getInt("YEAR_NUM"));
        as.setMonthNum(rs.getInt("MONTH_NUM"));
        as.setOpeningBalance(rs.getDouble("OPENING_BALANCE"));
        as.setClosingBalance(rs.getDouble("CLOSING_BALANCE"));
        as.setTotalCredit(rs.getDouble("TOTAL_CREDIT"));
        as.setTotalDebit(rs.getDouble("TOTAL_DEBIT"));
        as.setTxnCount(rs.getInt("TXN_COUNT"));

        java.sql.Timestamp ts = rs.getTimestamp("GENERATED_AT");
        if (ts != null) {
            as.setGeneratedAt(ts.toLocalDateTime());
        }

        return as;
    }
}
