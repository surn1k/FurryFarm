package com.furrryfarm.handlers;


import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
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
            HttpCookie cookie = HttpCookie.parse(sessionCookie).get(0);
            System.out.print("Cookie Here: ");
            System.out.println(cookie.getValue());
            handler.handleGETRequest(httpExchange, components, parameters);
            return;
        }

        handleUnauthorizedRequest(httpExchange);
    }

    void handleUnauthorizedRequest(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add("Location", "/login");

        String response = "Unauthorized request. Redirecting...";
        httpExchange.sendResponseHeaders(302, response.length());

        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
