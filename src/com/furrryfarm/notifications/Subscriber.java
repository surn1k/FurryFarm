package com.furrryfarm.notifications;

import java.io.IOException;
import java.util.LinkedList;

public interface Subscriber {
    void sendMessages(LinkedList<Message> messages) throws IOException;
}
