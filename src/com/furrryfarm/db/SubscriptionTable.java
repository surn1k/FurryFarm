package com.furrryfarm.db;

import com.furrryfarm.db.entity.DBEntity;
import com.furrryfarm.db.entity.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class SubscriptionTable extends Table {
    private final String name = "subscription";

    @Override
    protected String getName() {
        return name;
    }

    @Override
    protected Subscription serialize(ResultSet row) throws SQLException {
        return new Subscription(row.getInt("publisher"), row.getInt("subscriber"));
    }

    public LinkedList<Subscription> getSubscribers(int id) throws SQLException, ClassNotFoundException {
        return getRows("select * from " + getName() + " where publisher=" + id + ";")
                .stream()
                .map(elt -> (Subscription) elt)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
