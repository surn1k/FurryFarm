package com.furrryfarm.handlers;

import com.furrryfarm.utils.html.TemplateLoader;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.List;
import java.util.Map;


public class HomeHandler extends GetHttpHandler {
    @Override
    void handleGETRequest(HttpExchange httpExchange,
                          List<String> components,
                          Map<String, String> parameters) throws IOException {

        String sessionCookie = httpExchange.getRequestHeaders().getFirst("Cookie");

        HttpCookie idCookie = HttpCookie.parse(sessionCookie)
                .stream()
                .filter(item -> item.getName().equals("UserID"))
                .findFirst()
                .orElse(null);

        if (components.size() == 1) {
            String content;

            if (userIsDealer()) {
                content = TemplateLoader.read( "dealer.html");
            } else {
                content = TemplateLoader.read( "farmer.html");
            }
            assert content != null;
            returnString(httpExchange, content, 200);
            return;
        }
        returnString(httpExchange, "Invalid request", 404);
    }

    boolean userIsDealer() {return true;} // TODO
}