package com.furrryfarm.handlers;

import com.furrryfarm.utils.html.TemplateLoader;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LandHandler extends GetHttpHandler {

    @Override
    void handleGETRequest(HttpExchange httpExchange, List<String> components, Map<String, String> parameters) throws IOException {
        String content = TemplateLoader.read( "land.html");
        System.out.println(content);
        assert content != null;
        returnString(httpExchange, content, 200);
    }
}
