package com.furrryfarm.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class TestAuthorizedHandler extends AuthorizedGetHandler {
    public TestAuthorizedHandler(String entryPoint) { super(entryPoint); }

    @Override
    void handleAuthorizedRequest(HttpExchange httpExchange,
                                 List<String> components,
                                 Map<String, String> parameters) throws IOException {
        String response = "You are authorized!";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
