package com.furrryfarm.db;

import com.furrryfarm.db.entity.DBEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


public abstract class Table {
    protected String name = null;
    public String getName() { return name; }

    public LinkedList<Object> getByID(int id) throws SQLException, ClassNotFoundException {
        return getRows("select * from " + name + " where id=" + id);
    }

    public LinkedList<Object> all() throws SQLException, ClassNotFoundException {
        return getRows("select * from " + name + ";");
    }

    private LinkedList<Object> getRows(String sql) throws SQLException, ClassNotFoundException {
        LinkedList<Object> response = new LinkedList<>();

        ResultSet result = DataBase.getDataBase().execute(sql);
        while (result.next()){ response.add(serialize(result)); }

        return response;
    }

    protected static String tupleValues(DBEntity entity) {
        return "(" + String.join(", ", entity.getValues()) + ")";
    }

    public void insert(DBEntity entity) throws SQLException, ClassNotFoundException {
        String request = "insert into " + name + " values " + tupleValues(entity) + ";";
        DataBase.getDataBase().execute(request);
    }

    protected abstract Object serialize(ResultSet row) throws SQLException;
}
