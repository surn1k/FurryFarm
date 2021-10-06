package com.furrryfarm.handlers;


import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LoggingGetHandlerDecorator extends GetHttpHandlerDecorator {
    public LoggingGetHandlerDecorator(GetHttpHandler handler) {
        super(handler);
    }

    @Override
    protected void handleGETRequest(HttpExchange httpExchange,
                                    List<String> components,
                                    Map<String, String> parameters) throws IOException {
        System.out.println("[GET Request]: " + httpExchange.getRequestURI());
        handler.handleGETRequest(httpExchange, components, parameters);
    }
}
