package com.furrryfarm.db.entity;

public record Dealer(int id, String name) implements DBEntity {
    @Override
    public String[] getValues() {
        return new String[]{Integer.toString(id), name};
    }

    @Override
    public String[] getColNames() {
        return new String[] { "id", "name" };
    }
}
