package com.furrryfarm.handlers;

import com.furrryfarm.utils.html.TemplateLoader;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;
import java.util.Map;

public class LoginHandler extends GetHttpHandler {

    @Override
    void handleGETRequest(HttpExchange httpExchange,
                          List<String> components,
                          Map<String, String> parameters) throws IOException {
        if (components.size() == 1) {
            String content = TemplateLoader.read( "login.html");
            assert content != null;
            returnString(httpExchange, content, 200);
        } else if (components.size() == 2 && components.get(1).equals("auth")) {
                if (parameters.containsKey("login") && parameters.containsKey("password")) {

                    String login = parameters.get("login");
                    String password = parameters.get("password");

                    if (login.equals("admin") && password.equals("admin")) {
                        // GET UserID from db
                        HttpCookie cookie = new HttpCookie("UserID", "0");
                        httpExchange.getResponseHeaders().add("Set-Cookie", cookie + "; Path=/");
                        redirect(httpExchange, "/home");
                    } else {
                        returnString(httpExchange, "Invalid login or password", 403);
                    }
                } else {
                    returnString(httpExchange, "Invalid request", 404);
                }
        } else if (components.size() == 2 && components.get(1).equals("logout")) {
            HttpCookie cookie = new HttpCookie("UserID", null);
            httpExchange.getResponseHeaders().add("Set-Cookie", cookie + "; Path=/");
            redirect(httpExchange, "/login");
        }

        returnString(httpExchange, "Invalid request", 404);
    }
}
