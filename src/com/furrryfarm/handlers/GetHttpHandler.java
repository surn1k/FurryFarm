package com.furrryfarm.handlers;

import com.furrryfarm.utils.ParameterStringParser;
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
        Map<String, String> requestParamValue = ParameterStringParser.parse(httpExchange.getRequestURI().getQuery());
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

    abstract void handleRequest(HttpExchange httpExchange,
                                List<String> components,
                                Map<String, String> parameters) throws IOException;
}
