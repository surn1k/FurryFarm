package com.furrryfarm.handlers;


import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.List;
import java.util.Map;

public abstract class AuthorizedGetHandler extends GetHttpHandler {
    AuthorizedGetHandler(String entryPoint) { super(entryPoint); }

    @Override
    protected void handleRequest(HttpExchange httpExchange,
                                 List<String> components,
                                 Map<String, String> parameters) throws IOException {
        String sessionCookie = httpExchange.getRequestHeaders().getFirst("Cookie");
        if (sessionCookie != null) {
            HttpCookie cookie = HttpCookie.parse(sessionCookie).get(0);
            System.out.print("Cookie Here: ");
            System.out.println(cookie.getValue());
            handleAuthorizedRequest(httpExchange, components, parameters);
            return;
        }

        handleUnauthorizedRequest(httpExchange);
    }

    abstract void handleAuthorizedRequest(HttpExchange httpExchange,
                                 List<String> components,
                                 Map<String, String> parameters) throws IOException;


    void handleUnauthorizedRequest(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add("Location", "/login");

        String response = "Unauthorized request. Redirecting...";
        httpExchange.sendResponseHeaders(302, response.length());

        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
