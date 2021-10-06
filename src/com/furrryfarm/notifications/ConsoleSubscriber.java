package com.furrryfarm.notifications;

import java.util.LinkedList;

public class ConsoleSubscriber implements Subscriber {
    @Override
    public void sendMessages(LinkedList<Message> messages) {
        for (Message msg : messages) {
            System.out.println("[Notification]: sending to the user " + msg.username + ". Text: " + msg.text);
        }
    }
}
