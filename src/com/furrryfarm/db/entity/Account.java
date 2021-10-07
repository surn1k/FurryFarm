package com.furrryfarm.db.entity;

public record Account(int id, String login, String password) implements DBEntity {
    @Override
    public String[] getValues() {
        return new String[]{login, password};
    }

    @Override
    public String[] getColNames() {
        return new String[] { "login", "password" };
    }


}

