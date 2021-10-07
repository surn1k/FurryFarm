package com.furrryfarm.db;

import com.furrryfarm.db.entity.DBEntity;
import com.google.gson.Gson;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;

public class InitialTables {
    private static class typeTables {
        public String[] tables;
    }

    public InitialTables() throws IOException, FileNotFoundException, SQLException, ClassNotFoundException {
        Gson g = new Gson();
        typeTables a = g.fromJson(new FileReader("tables.json"), typeTables.class);

        for(int i = 0; i < a.tables.length; ++i)
        {
            // System.out.println(a.tables[i]);
            DataBase.getDataBase().execute(a.tables[i]);
        }
    }
}