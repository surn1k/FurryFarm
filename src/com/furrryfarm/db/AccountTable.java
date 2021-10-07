package com.furrryfarm.db;
import com.furrryfarm.db.entity.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class AccountTable extends Table {
    final String name = "account";

    @Override
    protected String getName() {
        return name;
    }

    @Override
    protected Object serialize(ResultSet row) throws SQLException {
        return new Account(row.getInt("id"),
                row.getString("login"), row.getString("password"));
    }

    public Account getByLogin(String login) throws SQLException, ClassNotFoundException {
        String sql = "select * from " + name + "where login= " + login + ";";
        LinkedList<Object> rows = getRows(sql);
        if (rows.isEmpty()) return null;
        return (Account) rows.get(0);
    }
}
