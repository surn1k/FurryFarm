package com.furrryfarm.handlers;


import com.furrryfarm.utils.cookies.CookieManager;
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
        HttpCookie idCookie = CookieManager.getCookieByName(httpExchange, "UserID");
        if (idCookie == null || idCookie.getValue().equals("null")) handleUnauthorizedRequest(httpExchange);
        else handler.handleGETRequest(httpExchange, components, parameters);
    }

    void handleUnauthorizedRequest(HttpExchange httpExchange) throws IOException {
        redirect(httpExchange, "/login");
    }
}
