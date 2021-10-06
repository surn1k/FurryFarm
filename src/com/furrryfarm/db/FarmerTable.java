package com.furrryfarm.db;
import com.furrryfarm.db.entity.Farmer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class FarmerTable extends Table {
    private final String name = "farmer";

    @Override
    protected String getName() {
        return name;
    }

    @Override
    public Object serialize(ResultSet row) throws SQLException {
        return new Farmer(row.getInt("id"), row.getString("name"));
    }
}
