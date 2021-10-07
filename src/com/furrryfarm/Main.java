package com.furrryfarm;

import com.furrryfarm.db.migrations.InitialTables;
import com.furrryfarm.handlers.*;
import com.furrryfarm.utils.auth.UserRoleHelper;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) throws Exception {
        InitialTables.initialize();

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // TODO: fix it (constructor parameter for handler is not allowed)
        server.createContext("/home",
                new LoggingGetHandlerDecorator(
                        new AuthorizedGetHandlerDecorator(new HomeHandler(), UserRoleHelper.UserRole.AUTHORISED)));
        server.createContext("/login", new LoggingGetHandlerDecorator(new LoginHandler()));
        server.createContext("/register", new RegisterHandler());
        server.createContext("/dealer", new AuthorizedGetHandlerDecorator(new DealerHandler(),
                UserRoleHelper.UserRole.DEALER));
        server.createContext("/farmer", new AuthorizedGetHandlerDecorator(new FarmerHandler(),
                UserRoleHelper.UserRole.FARMER));

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);
        server.start();
    }
}