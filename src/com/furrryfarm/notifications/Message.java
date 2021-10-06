package com.furrryfarm.notifications;

public class Message {
    public String text;
    public String username; // Replace with destination User object

    public Message(String text, String username) {
        this.username = username;
        this.text = text;
    }
}
