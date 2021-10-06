import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.*;
import java.util.concurrent.ExecutionException;

public class DataBase {

     static public DataBase GetDataBase() throws Exception{
        if(!created){
            dataBase = new DataBase();
        }
        return dataBase;
    }

    private  static  boolean created;
    static private DataBase dataBase;
    private HashMap<String, Table> tables;
    private HashMap<String, String> table_gen;
    private int sizeDB;

    private DataBase(){
        tables = new HashMap<String, Table>();
        created = true;
    }

    public ResultSet AskDB(String request) throws Exception {
        Connection connection;
        ResultSet result = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            result =  connection.createStatement().executeQuery(request);
            connection.close();
        }catch (Exception ignored){}
        return result;
    }
}
