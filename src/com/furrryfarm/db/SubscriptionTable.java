package com.furrryfarm.db;

import com.furrryfarm.db.entity.DBEntity;
import com.furrryfarm.db.entity.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SubscriptionTable extends Table {
    private final String name = "notification";

    @Override
    protected String getName() {
        return name;
    }

    @Override
    protected Subscription serialize(ResultSet row) throws SQLException {
        return new Subscription(row.getInt("publisher"), row.getInt("subscriber"));
    }

    public LinkedList<Subscription> getSubscribers(int id) throws SQLException, ClassNotFoundException {
//        LinkedList<DBEntity> entities
//                = getRows("select * from " + getName() + " where publisher=" + id + ";");
//        LinkedList<Subscription> subEntities = new LinkedList<>();
//        for (DBEntity tmp:
//             entities) {
//            subEntities.add((Subscription) tmp);
//        }
//
//        return subEntities;

        return (LinkedList<Subscription>)
               (LinkedList<?>)
               getRows("select * from " + getName() + " where publisher=" + id + ";");

    }
}
