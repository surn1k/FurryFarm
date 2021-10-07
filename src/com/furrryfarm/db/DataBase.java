package com.furrryfarm.db;

import java.sql.DriverManager;
import java.sql.*;

public class DataBase {
    private  static  boolean created;
    static private DataBase dataBase;


     static public DataBase getDataBase() {
        if (!created) dataBase = new DataBase();
        return dataBase;
    }

    private DataBase() {
        created = true;
    }

    public ResultSet execute(String request) throws ClassNotFoundException, SQLException {
        Connection connection;
        ResultSet result = null;

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement statement = connection.createStatement();
        if (request.startsWith("select")) {
            result = statement.executeQuery(request);
        } else if (request.startsWith("CREATE")){

            statement.execute(request);
        } else {
            statement.executeUpdate(request);
        }

        statement.close();
        if (!request.startsWith("CREATE")) {
            connection.commit();
        }
        connection.close();
        return result;
    }
}
