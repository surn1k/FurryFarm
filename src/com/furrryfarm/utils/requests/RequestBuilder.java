package com.furrryfarm.utils.requests;

import com.furrryfarm.utils.ParameterStringBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

abstract class RequestBuilder {
    public abstract void setMethod(HttpURLConnection connection) throws ProtocolException;

    // This methods could be skipped (headers are not necessary)
    // So they shouldn't be abstract
    public void setHeaders(HttpURLConnection connection) {};
    public void configureTimeouts(HttpURLConnection connection) {};
    public void handleRedirects(HttpURLConnection connection) {};
    public Object parseResponse(String response) { return response; };

    public String readResponse(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        String line;
        StringBuilder content = new StringBuilder();
        while ((line = in.readLine()) != null) content.append(line);

        in.close();
        return content.toString();
    }

    public void writeRequest(HttpURLConnection connection,
                                     HashMap<String, String> params) throws IOException {
        connection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(params));
        out.flush();
        out.close();
    }

    public HttpURLConnection connect(String spec) throws IOException {
        URL url = new URL(spec);
        return (HttpURLConnection) url.openConnection();
    }
}
