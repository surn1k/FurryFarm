package com.furrryfarm.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountTable extends Table {
    private final String name = "account";

    @Override
    protected Object serialize(ResultSet row) throws SQLException {
        return null;
    }
}
