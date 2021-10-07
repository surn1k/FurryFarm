package com.furrryfarm.db;
import com.furrryfarm.db.entity.DBEntity;
import com.furrryfarm.db.entity.Farmer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class FarmerTable extends Table {
    private final String name = "farmer";

    @Override
    protected String getName() {
        return name;
    }

    @Override
    public Farmer serialize(ResultSet row) throws SQLException {
        return new Farmer(row.getInt("id"), row.getString("name"));
    }
}
