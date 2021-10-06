package com.furrryfarm.utils.cookies;

import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;

public class CookieManager {
    public static HttpCookie getCookieByName(HttpExchange httpExchange, String name) {
        String sessionCookie = httpExchange.getRequestHeaders().getFirst("Cookie");
        if (sessionCookie == null) return null;
        HttpCookie specCookie = HttpCookie.parse(sessionCookie)
                .stream()
                .filter(item -> item.getName().equals(name))
                .findFirst()
                .orElse(null);
        return specCookie;
    }

    public static void setCookie(HttpExchange httpExchange, String name, String value) {
        HttpCookie cookie = new HttpCookie(name, value);
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie + "; Path=/");
    }
}
