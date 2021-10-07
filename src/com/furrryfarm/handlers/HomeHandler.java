package com.furrryfarm.handlers;

import com.furrryfarm.utils.auth.UserRoleHelper;
import com.furrryfarm.utils.cookies.CookieManager;
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
        HttpCookie idCookie = CookieManager.getCookieByName(httpExchange,"UserID");

        if (components.size() == 1) {
            String content;

            assert idCookie != null;
            UserRoleHelper.UserRole userRole = UserRoleHelper.getUserRole(Integer.parseInt(idCookie.getValue()));

            if (userRole == UserRoleHelper.UserRole.DEALER) {
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
}