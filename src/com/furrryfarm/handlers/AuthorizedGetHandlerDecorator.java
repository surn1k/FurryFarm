package com.furrryfarm.handlers;


import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;
import java.util.Map;

public class AuthorizedGetHandlerDecorator extends GetHttpHandlerDecorator {
    public AuthorizedGetHandlerDecorator(GetHttpHandler handler) {
        super(handler);
    }

    @Override
    protected void handleGETRequest(HttpExchange httpExchange,
                                    List<String> components,
                                    Map<String, String> parameters) throws IOException {
        String sessionCookie = httpExchange.getRequestHeaders().getFirst("Cookie");
        if (sessionCookie != null) {
            HttpCookie idCookie = HttpCookie.parse(sessionCookie)
                                            .stream()
                                            .filter(item -> item.getName().equals("UserID"))
                                            .findFirst()
                                            .orElse(null);

            if (idCookie == null || idCookie.getValue().equals("null")) handleUnauthorizedRequest(httpExchange);
            else {
                handler.handleGETRequest(httpExchange, components, parameters);
            }
        } else handleUnauthorizedRequest(httpExchange);
    }

    void handleUnauthorizedRequest(HttpExchange httpExchange) throws IOException {
        redirect(httpExchange, "/login");
    }
}
