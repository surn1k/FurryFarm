package com.furrryfarm.db;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.DriverManager;
import java.sql.*;

public class DataBase {
    private static boolean created;
    static private volatile DataBase dataBase;
    private static final Object mutex = new Object();


     static public DataBase getDataBase() {
        if (!created) dataBase = new DataBase();
        return dataBase;
    }

    private DataBase() {
        created = true;
    }


    public CachedRowSet execute(String request) throws ClassNotFoundException, SQLException {
        Connection connection;

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:furryfarm.db");

        Statement statement = connection.createStatement();
        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet rowSet = factory.createCachedRowSet();

        synchronized (mutex) {
            if (request.startsWith("select")) {
                connection.setAutoCommit(false);
                ResultSet result = statement.executeQuery(request);
                rowSet.populate(result);
            } else if (request.startsWith("create")) {
                statement.execute(request);
            } else {
                connection.setAutoCommit(false);
                statement.executeUpdate(request);
            }

            statement.close();
            if (!request.startsWith("create")) connection.commit();
            connection.close();
        }
        return rowSet;
    }
}
