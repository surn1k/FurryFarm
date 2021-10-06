package com.furrryfarm.notifications;

import java.util.LinkedList;

public class ConsoleSubscriber implements Subscriber {
    public ConsoleSubscriber() {

    }

    @Override
    public void sendMessages(LinkedList<Message> messages) {
        for (Message msg : messages) {
            System.out.print(msg.username + ": ");
            System.out.println(msg.text);
        }
    }
}
