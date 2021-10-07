package com.furrryfarm.handlers;

import com.furrryfarm.utils.auth.CredentialsManager;
import com.furrryfarm.utils.html.TemplateLoader;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RegisterHandler extends GetHttpHandler {

    @Override
    void handleGETRequest(HttpExchange httpExchange,
                          List<String> components,
                          Map<String, String> parameters) throws IOException {
        if (components.size() == 1) {
            String content = TemplateLoader.read( "register.html");
            assert content != null;
            returnString(httpExchange, content, 200);
        } else if (components.size() == 2 && components.get(1).equals("new")) {
            if (parameters.containsKey("login") &&
                    parameters.containsKey("password") &&
                    parameters.containsKey("name") &&
                    parameters.containsKey("type")) {
                String login = parameters.get("login");
                String password = parameters.get("password");
                String name = parameters.get("name");
                String type = parameters.get("type");

                if (CredentialsManager.exists(login)) {
                    returnString(httpExchange, "User with this login already registered", 200);
                    return;
                }

                CredentialsManager.registerUser(login, password, name, type);

                String content = TemplateLoader.read("register_completed.html");
                assert content != null;
                returnString(httpExchange, content, 200);
            } else returnString(httpExchange, "Invalid request", 404);
        }
    }
}
