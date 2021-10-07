package com.furrryfarm.db.entity;

public record Subscription(int publisher, int subscriber) implements DBEntity {
    @Override
    public String[] getValues() {
        return new String[]{Integer.toString(publisher), Integer.toString(subscriber)};
    }

    @Override
    public String[] getColNames() {
        return new String[] { "publisher", "subscriber" };
    }
}
