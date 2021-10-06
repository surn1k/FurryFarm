package com.furrryfarm.db;
import com.furrryfarm.db.entity.Farmer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


class FarmerTable extends Table {
    private final String name = "farmer";

    @Override
    public Object serialize(ResultSet row) throws SQLException {
        return new Farmer(row.getInt("id"), row.getString("name"));
    }
}
