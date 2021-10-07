package com.furrryfarm.db;

import com.furrryfarm.db.entity.DBEntity;
import com.furrryfarm.db.entity.Dealer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DealerTable extends Table {
    private final String name = "dealer";

    @Override
    protected String getName() {
        return name;
    }

    @Override
    public DBEntity serialize(ResultSet row) throws SQLException {
        return new Dealer(row.getInt("id"), row.getString("name"));
    }
}
