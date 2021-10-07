package com.furrryfarm.db;

import com.furrryfarm.db.entity.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionTable extends Table {
    private final String name = "notification";

    @Override
    protected String getName() {
        return name;
    }

    @Override
    protected Object serialize(ResultSet row) throws SQLException {
        return new Subscription(row.getInt("publisher"), row.getInt("subscriber"));
    }
}
