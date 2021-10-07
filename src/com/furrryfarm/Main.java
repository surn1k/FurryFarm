package com.furrryfarm;

import com.furrryfarm.handlers.*;
import com.furrryfarm.utils.auth.UserRoleHelper;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Method createContext checks only a prefix entry of path in url
        // So urls like /test_and_some_rubbish will be accepted (for path: /test)
        // TODO: fix it (constructor parameter for handler is not allowed)
        server.createContext("/home",
                new LoggingGetHandlerDecorator(
                        new AuthorizedGetHandlerDecorator(new HomeHandler(), UserRoleHelper.UserRole.AUTHORISED)));
        server.createContext("/login", new LoggingGetHandlerDecorator(new LoginHandler()));
        server.createContext("/register", new RegisterHandler());

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);
        server.start();
    }
//    public static void main(String[] args) throws Exception {
//
//        List<Subscriber> subscribers = Arrays.asList(new TelegramSubscriber(), new ConsoleSubscriber());
//        NotificationsPublisher publisher = new NotificationsPublisher(new LinkedList<>(subscribers));
//
//
//        LinkedList<Message> messages = new LinkedList<>();
//        messages.add(new Message("Лох", "identiki_t"));
//        messages.add(new Message("ылпотвалп", "identiki_t"));
//        messages.add(new Message("Оч", "suriknik"));
//
//        publisher.notifySubscribers(messages);
//    }
}