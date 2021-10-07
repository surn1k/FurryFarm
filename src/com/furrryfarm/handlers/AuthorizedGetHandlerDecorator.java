package com.furrryfarm.handlers;

import com.furrryfarm.utils.auth.UserRoleHelper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import com.furrryfarm.utils.cookies.CookieManager;
import java.net.HttpCookie;
import java.util.List;
import java.util.Map;


public class AuthorizedGetHandlerDecorator extends GetHttpHandlerDecorator {
    public AuthorizedGetHandlerDecorator(GetHttpHandler handler, UserRoleHelper.UserRole role) {
        super(handler);
        this.userRole = role;
    }
    final private UserRoleHelper.UserRole userRole;

    @Override
    protected void handleGETRequest(HttpExchange httpExchange,
                                    List<String> components,
                                    Map<String, String> parameters) throws IOException {
        HttpCookie idCookie = CookieManager.getCookieByName(httpExchange, "userID");
        if (idCookie != null) {
            UserRoleHelper.UserRole idUserRole = UserRoleHelper.getUserRole(Integer.parseInt(idCookie.getValue()));

            if (idUserRole == UserRoleHelper.UserRole.FARMER && this.userRole == UserRoleHelper.UserRole.FARMER
                    || idUserRole == UserRoleHelper.UserRole.DEALER && this.userRole == UserRoleHelper.UserRole.DEALER
                    || this.userRole == UserRoleHelper.UserRole.AUTHORISED && idUserRole != UserRoleHelper.UserRole.OTHER)
                handler.handleGETRequest(httpExchange, components, parameters);
            else if (idUserRole == UserRoleHelper.UserRole.OTHER)
                handleUnauthorizedRequest(httpExchange);
            else {
                String errorMessage = "You are not allowed to do that";
                returnString(httpExchange, errorMessage, 403);
            }
        } else handleUnauthorizedRequest(httpExchange);
    }

    void handleUnauthorizedRequest(HttpExchange httpExchange) throws IOException {
        redirect(httpExchange, "/login");
    }
}
