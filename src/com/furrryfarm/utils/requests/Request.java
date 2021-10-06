package com.furrryfarm.utils.requests;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;

public class Request {
    private RequestBuilder requestBuilder;

    public Request(RequestBuilder builder) {
        requestBuilder = builder;
    }

    public void changeBuilder(RequestBuilder builder) {
        requestBuilder = builder;
    }

    public Object make(String url, HashMap<String, String> params) throws IOException {
        HttpURLConnection connection = requestBuilder.connect(url);
        requestBuilder.setMethod(connection);
        requestBuilder.setHeaders(connection);
        requestBuilder.configureTimeouts(connection);
        requestBuilder.handleRedirects(connection);
        requestBuilder.writeRequest(connection, params);
        String response = requestBuilder.readResponse(connection);
        return requestBuilder.parseResponse(response);
    }
}
