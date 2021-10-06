package com.furrryfarm.db.entity;

public record Farmer(int id, String name) implements DBEntity {
    @Override
    public String[] getValues() {
        return new String[]{Integer.toString(id), name};
    }
}
