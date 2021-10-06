package com.furrryfarm.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;

abstract class GetHttpHandlerDecorator extends GetHttpHandler {
    protected final GetHttpHandler handler;

    public GetHttpHandlerDecorator(GetHttpHandler handler) {
        this.handler = handler;
    }

    abstract protected void handleGETRequest(HttpExchange httpExchange,
                                             List<String> components,
                                             Map<String, String> parameters) throws IOException;
}
