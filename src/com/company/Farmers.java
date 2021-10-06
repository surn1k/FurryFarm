package com.company;
import java.lang.reflect.Executable;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.util.ArrayList;



public class Farmers extends Table{

    @Override
    String getName() {
        return tableName;
    }

    @Override
    ArrayList<String> getRef(int id) {
        ArrayList<String> response = new ArrayList<String>();
        String request = "SELECT " + id + " FROM " + tableName+";";
        try {
            ResultSet result = DataBase.GetDataBase().AskDB(request);
            while(result.next()){
                if(id == result.getInt("id")){
                    response.add(result.getString("login"));
                    response.add(result.getString("password"));
                }
            }
        } catch (Exception ignored){}
        return response;
    }

    public void writeRef(int id, String login, String password) {
        String request = "INSERT INTO " + tableName + " (ID, LOGIN, PASSWORD) VALUES " +
                "(" + id + ", " + login + ", " + password + ");";
        try{
            DataBase.GetDataBase().AskDB(request);
        }catch (Exception ignored){}
    }

    public ArrayList<String> findRef(String login){
        ArrayList<String> response = new ArrayList<String>();
        String request = "SELECT * " + tableName + " WHERE LOGIN=" + login + ";";
        try{
            ResultSet result = DataBase.GetDataBase().AskDB(request);
            response.add(result.getString("id"));
            response.add(result.getString("login"));
            response.add(result.getString("password"));
        }catch(Exception ignored){}
        return response;
    }

    @Override
    public void deleteRef(int id) {

    }

    @Override
    public int getTableSize() {
        return 0;
    }

    @Override
    public ArrayList<String> getAll() {
        return null;
    }

    private final static String tableName = "FARMERS";
}
