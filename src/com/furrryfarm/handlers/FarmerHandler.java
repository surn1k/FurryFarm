package com.furrryfarm.handlers;

import com.furrryfarm.db.SubscriptionTable;
import com.furrryfarm.db.entity.Subscription;
import com.sun.net.httpserver.HttpExchange;
import com.furrryfarm.utils.cookies.CookieManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FarmerHandler extends GetHttpHandler {
    @Override
    void handleGETRequest(HttpExchange httpExchange,
                          List<String> components,
                          Map<String, String> parameters) throws IOException {
        int userID = Integer.parseInt(Objects.requireNonNull(
                CookieManager.getCookieByName(httpExchange, "UserID")).getValue());

        SubscriptionTable subTable = new SubscriptionTable();

        for (String dealerID: parameters.keySet()) {
            Subscription newSub = new Subscription(Integer.parseInt(dealerID), userID);

            try {
                subTable.insert(newSub);
            } catch (SQLException | ClassNotFoundException exception) {
                System.out.println("[DB Error]: " + exception);
            }
        }

        returnString(httpExchange, "Subscribed!", 200);
    }
}
