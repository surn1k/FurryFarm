package com.furrryfarm.db;

import com.furrryfarm.db.entity.Dealer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DealerTable extends Table {
    private final String name = "dealer";

    @Override
    public Object serialize(ResultSet row) throws SQLException {
        return new Dealer(row.getInt("id"), row.getString("name"));
    }
}