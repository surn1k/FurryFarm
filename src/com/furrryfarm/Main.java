package com.furrryfarm;

import com.furrryfarm.notifications.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow;

public class Main {
    // TODO: move telegram bot credentials in Singleton
    private static final String token = "2002000409:AAHQxT1jycCHCKvJ78bbV3RdnDzxKl-jXf0";

//    public static void main(String[] args) throws Exception {
//        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
//
//        // The use of entryPoint parameter is not beautiful
//        // But method createContext checks only a prefix entry of path in url
//        // So urls like /test_and_some_rubbish will be accepted (for path: /test)
//        // TODO: think about more elegant way
//        server.createContext("/test", new TestAuthorizedHandler("test"));
//
//        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
//        server.setExecutor(threadPoolExecutor);
//        server.start();
//    }
    public static void main(String[] args) throws Exception {

        List<Subscriber> subscribers = Arrays.asList(new TelegramSubscriber(token), new ConsoleSubscriber());
        NotificationsPublisher publisher = new NotificationsPublisher(new LinkedList<>(subscribers));


        LinkedList<Message> messages = new LinkedList<>();
        messages.add(new Message("Hello World", "suriknik"));
        messages.add(new Message("Keklol", "suriknik"));

        publisher.notifySubscribers(messages);
    }
}