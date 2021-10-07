package com.furrryfarm.db.migrations;

import com.furrryfarm.db.DataBase;
import com.google.gson.Gson;


import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class InitialTables {
    private static class typeTables {
        public String[] tables;
    }

    public static void initialize() throws IOException, SQLException, ClassNotFoundException {
        Gson g = new Gson();
        typeTables a = g.fromJson(new FileReader("tables.json"), typeTables.class);

        for(int i = 0; i < a.tables.length; ++i)
            DataBase.getDataBase().execute(a.tables[i]);
    }
}