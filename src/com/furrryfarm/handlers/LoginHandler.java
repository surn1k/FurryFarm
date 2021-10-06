package com.furrryfarm.handlers;

import com.furrryfarm.utils.auth.CredentialsManager;
import com.furrryfarm.utils.cookies.CookieManager;
import com.furrryfarm.utils.html.TemplateLoader;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
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

                    if (CredentialsManager.validate(login, password)) {
                        Integer id = CredentialsManager.getID(login, password);
                        assert id != null;
                        CookieManager.setCookie(httpExchange,"UserID", id.toString());
                        redirect(httpExchange, "/home");
                    } else returnString(httpExchange, "Invalid login or password", 403);
                } else returnString(httpExchange, "Invalid request", 404);
        } else if (components.size() == 2 && components.get(1).equals("logout")) {
            CookieManager.setCookie(httpExchange, "UserID", null);
            redirect(httpExchange, "/login");
        }

        returnString(httpExchange, "Invalid request", 404);
    }
}
