package com.furrryfarm.handlers;

import com.furrryfarm.db.DealerTable;
import com.furrryfarm.db.entity.Dealer;
import com.furrryfarm.utils.auth.UserRoleHelper;
import com.furrryfarm.utils.cookies.CookieManager;
import com.furrryfarm.utils.html.TemplateLoader;
import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class HomeHandler extends GetHttpHandler {
    @Override
    void handleGETRequest(HttpExchange httpExchange,
                          List<String> components,
                          Map<String, String> parameters) throws IOException {
        HttpCookie idCookie = CookieManager.getCookieByName(httpExchange,"UserID");

        if (true) {
            String content = composeFarmerPage();
            returnString(httpExchange, content, 200);
            return;
        }

        if (components.size() == 1) {
            String content;

            assert idCookie != null;
            UserRoleHelper.UserRole userRole = UserRoleHelper.getUserRole(Integer.parseInt(idCookie.getValue()));


            if (userRole == UserRoleHelper.UserRole.DEALER) {
                content = TemplateLoader.read( "dealer.html");
            } else {
                content = composeFarmerPage();
            }
            assert content != null;
            returnString(httpExchange, content, 200);
            return;
        }
        returnString(httpExchange, "Invalid request", 404);
    }

    String composeFarmerPage() throws IOException {
        String content;

//            List<Object> dealers = null;
//            try {
//                dealers = (new DealerTable()).all();
//            } catch (SQLException | ClassNotFoundException exception) {
//                System.out.println("[DB Error]: kek " + exception);
//            }

        content = TemplateLoader.read( "farmer_upper.html");

//        for (Object o : dealers) {
            for (int i=0; i<6; i++) {
//                Dealer dealer = (Dealer)o;
            String row = TemplateLoader.read("farmer_middle_table.html");
            Jinjava jinjava = new Jinjava();
            Map<String, Object> dealerInfo = Maps.newHashMap();
            dealerInfo.put("dealer_id", "id_cool");
            dealerInfo.put("name", "John Lemon");
            dealerInfo.put("interests", "Lemons, lemon juice, lemmas");
            dealerInfo.put("deals_done", "100");
            dealerInfo.put("current_ads", "10");

            content += jinjava.render(row, dealerInfo);
        }
        content += TemplateLoader.read("farmer_end.html");
        return content;
    }
}

