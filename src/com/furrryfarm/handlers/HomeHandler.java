package com.furrryfarm.handlers;

import com.furrryfarm.db.AccountTable;
import com.furrryfarm.db.DealerTable;
import com.furrryfarm.db.FarmerTable;
import com.furrryfarm.db.Table;
import com.furrryfarm.db.entity.Account;
import com.furrryfarm.db.entity.DBEntity;
import com.furrryfarm.db.entity.Dealer;
import com.furrryfarm.db.entity.Farmer;
import com.furrryfarm.notifications.Message;
import com.furrryfarm.utils.auth.UserRoleHelper;
import com.furrryfarm.utils.cookies.CookieManager;
import com.furrryfarm.utils.html.TemplateLoader;
import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class HomeHandler extends GetHttpHandler {
    private final String tableRow = TemplateLoader.read("farmer/farmer_middle_table.html");
    private final String templateHeader = TemplateLoader.read("farmer/farmer_header.html");
    private final String templateFooter = TemplateLoader.read("farmer/farmer_footer.html");

    @Override
    void handleGETRequest(HttpExchange httpExchange,
                          List<String> components,
                          Map<String, String> parameters) throws IOException {
        HttpCookie idCookie = CookieManager.getCookieByName(httpExchange,"UserID");

        if (components.size() == 1) {
            String content;

            assert idCookie != null;
            UserRoleHelper.UserRole userRole = UserRoleHelper.getUserRole(Integer.parseInt(idCookie.getValue()));
            int subID = Integer.parseInt(idCookie.getValue());

            Account acc = (Account) getUserEntry(new AccountTable(), subID);


            if (userRole == UserRoleHelper.UserRole.DEALER) {
                content = TemplateLoader.read( "dealer.html");
                System.out.println("dealer");

                Dealer dealer = (Dealer) getUserEntry(new DealerTable(), acc.id());
                content = placeName(dealer.name(), content);
            } else {
                content = composeFarmerPage();
                System.out.println("farmer");

                Farmer farmer = (Farmer) getUserEntry(new FarmerTable(), acc.id());
                content = placeName(farmer.name(), content);
            }
            assert content != null;
            returnString(httpExchange, content, 200);
            return;
        }
        returnString(httpExchange, "Invalid request", 404);
    }

    DBEntity getUserEntry(Table table, int id)
    {
        LinkedList<DBEntity> entity = null;
        try {
            entity = table.getByID(id);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException exception) {
            System.out.println(exception);
        }

        assert entity != null;
        return entity.getFirst();
    }

    String composeFarmerPage() {
        StringBuilder content;

        List<DBEntity> dealers = null;
        try {
            dealers = (new DealerTable()).all();
        } catch (SQLException | ClassNotFoundException exception) {
            System.out.println("[DB Error]: kek " + exception);
        }

        content = new StringBuilder();

        content.append(templateHeader);

        if (dealers != null) {
            for (Object dealer : dealers) {
                content.append(composeTableRow((Dealer) dealer));
            }
        }
        content.append(templateFooter);
        return content.toString();
    }

    String composeTableRow(Dealer dealer) {
        Jinjava jinjava = new Jinjava();
        Map<String, Object> dealerInfo = Maps.newHashMap();
        dealerInfo.put("dealer_id", dealer.id());
        dealerInfo.put("name", dealer.name());
        return jinjava.render(tableRow, dealerInfo);
    }

    String placeName(String name, String address)
    {
        Jinjava jinjava = new Jinjava();
        Map<String, Object> info = Maps.newHashMap();
        info.put("name", name);
        return jinjava.render(address, info);
    }
}

