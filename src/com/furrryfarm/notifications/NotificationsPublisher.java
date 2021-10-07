package com.furrryfarm.notifications;

import java.io.IOException;
import java.util.LinkedList;

public class NotificationsPublisher {
    private final LinkedList<Subscriber> subscribers;

    public NotificationsPublisher() {
        this.subscribers = new LinkedList<>();
    }

    public NotificationsPublisher(LinkedList<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    // TODO: catch exceptions
    public void notifySubscribers(LinkedList<Message> messages) throws IOException {
        for (Subscriber s: subscribers) {
            s.sendMessages(messages);
        }
    }
}
