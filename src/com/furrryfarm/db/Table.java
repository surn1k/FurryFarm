package com.furrryfarm.db;

import com.furrryfarm.db.entity.DBEntity;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


public abstract class Table {
    protected abstract String getName();

    public LinkedList<DBEntity> getByID(int id) throws SQLException, ClassNotFoundException {
        return getRows("select * from " + getName() + " where id =" + id + ";");
    }

    public LinkedList<DBEntity> all() throws SQLException, ClassNotFoundException {
        return getRows("select * from " + getName() + ";");
    }

    protected LinkedList<DBEntity> getRows(String sql) throws SQLException, ClassNotFoundException {
        LinkedList<DBEntity> response = new LinkedList<>();

        CachedRowSet result = DataBase.getDataBase().execute(sql);
        while (result.next()){ response.add(serialize(result)); }

        return response;
    }

    protected static String tupleValues(DBEntity entity) {
        return "('" + String.join("', '", entity.getValues()) + "')";
    }

    protected static String tupleColNames(DBEntity entity) {
        return "(" + String.join(", ", entity.getColNames()) + ")";
    }

    public void insert(DBEntity entity) throws SQLException, ClassNotFoundException {
        String request = "insert into " + getName() + " " + tupleColNames(entity) +
                " values " + tupleValues(entity) + ";";
        DataBase.getDataBase().execute(request);
    }

    protected abstract DBEntity serialize(ResultSet row) throws SQLException;
}
