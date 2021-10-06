package com.furrryfarm.utils.requests;
import java.net.*;
import com.google.gson.JsonParser;

public class GetJsonRequestBuilder extends RequestBuilder {
    @Override
    public void setMethod(HttpURLConnection connection) throws ProtocolException {
        connection.setRequestMethod("GET");
    }

    @Override
    public void setHeaders(HttpURLConnection connection) {
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
    }

    @Override
    public void configureTimeouts(HttpURLConnection connection) {
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
    }

    @Override
    public void handleRedirects(HttpURLConnection connection) {
        connection.setInstanceFollowRedirects(true); // for simplicity
    }

    @Override
    public Object parseResponse(String response) {
        return JsonParser.parseString(response).getAsJsonObject();
    }
}


