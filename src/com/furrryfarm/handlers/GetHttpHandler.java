package com.furrryfarm.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;


abstract class GetHttpHandler implements HttpHandler {
    private final String entryPoint;

    GetHttpHandler(String entryPoint) { this.entryPoint = entryPoint; }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (!validateRequest(httpExchange)) {
            returnError(httpExchange, "Invalid request", 404);
            return;
        }
        Map<String, String> requestParamValue = parseQuery(httpExchange.getRequestURI().getQuery());
        LinkedList<String> components = parseComponents(httpExchange);
        handleRequest(httpExchange, components, requestParamValue);
    }

    private void returnError(HttpExchange httpExchange, String text, int code) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();

        httpExchange.sendResponseHeaders(code, text.length());
        outputStream.write(text.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private LinkedList<String> parseComponents(HttpExchange httpExchange) {
        LinkedList<String> components = new LinkedList<>(Arrays.asList(httpExchange.
                getRequestURI().
                getPath().
                split("/")));
        components.remove(0); // First element is null
                                    // because URL path begins with "/"
        return components;
    }

    private boolean validateRequest(HttpExchange httpExchange) {
        List<String> components = parseComponents(httpExchange);
        return components.get(0).equals(entryPoint) && "GET".equals(httpExchange.getRequestMethod());
    }

    public Map<String, String> parseQuery(String query) {
        if(query == null) return null;

        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) result.put(entry[0], entry[1]);
            else result.put(entry[0], "");
        }
        return result;
    }

    abstract void handleRequest(HttpExchange httpExchange,
                                List<String> components,
                                Map<String, String> parameters) throws IOException;
}
