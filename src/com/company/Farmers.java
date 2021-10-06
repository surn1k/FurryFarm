package com.company;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.util.ArrayList;



public class Farmers extends Table{
    public Farmers(){
        tableName = "FARMERS";
    }

    @Override
    String getName() {
        return tableName;
    }

    @Override
    String getRef(int id) {
        String request = "SELECT " + id + " FROM " + tableName+";";
        try {
            ResultSet result = DataBase.GetDataBase().AskDB(request);
        } catch (Exception ignored){}

    }

    @Override
    public void writeRef(int id) {
        String request = "INSERT INTO " + tableName + ";";
        try {
        }
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

    private String tableName;
}
