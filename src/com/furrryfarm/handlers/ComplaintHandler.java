package com.furrryfarm.handlers;

import com.furrryfarm.db.AccountTable;
import com.furrryfarm.db.DealerTable;
import com.furrryfarm.db.entity.Account;
import com.furrryfarm.db.entity.DBEntity;
import com.furrryfarm.db.entity.Dealer;
import com.furrryfarm.notifications.*;
import com.furrryfarm.utils.html.TemplateLoader;
import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ComplaintHandler extends GetHttpHandler {
    private final NotificationsPublisher notificationsPublisher;
    private final String complaintPage = TemplateLoader.read("farmer/complaint.html");

    public ComplaintHandler() {
        LinkedList<Subscriber> subs = new LinkedList<Subscriber>(Arrays.asList(new TelegramSubscriber(), new ConsoleSubscriber()));
        this.notificationsPublisher = new NotificationsPublisher(subs);
    }

    @Override
    void handleGETRequest(HttpExchange httpExchange,
                          List<String> components,
                          Map<String, String> parameters) throws IOException {
        if (components.size() == 1) {
            String content = composeComplaintPage();
            assert content != null;
            returnString(httpExchange, content, 200);
        } else if (components.size() == 2 && components.get(1).equals("sent"))
        {
            int id = Integer.parseInt(parameters.get("dest"));
            LinkedList<DBEntity> entity = null;
            try
            {
                entity = (new AccountTable()).getByID(id);
            } catch (Exception exception)
            {
                System.out.println(exception);
            }

            LinkedList<Message> msgs = new LinkedList<>();
            if (entity != null && entity.size() == 1) {
                Account dest = (Account) entity.get(0);
                msgs.add(new Message(parameters.get("ad"), dest.login()));
            }

            notificationsPublisher.notifySubscribers(msgs);
            returnString(httpExchange, "Complaint is sent", 200);
        }

        returnString(httpExchange, "Invalid request", 404);
    }

    String composeComplaintPage() {
        List<DBEntity> dealers = null;
        try {
            dealers = (new DealerTable()).all();
        } catch (SQLException | ClassNotFoundException exception) {
            System.out.println("[DB Error]: kek " + exception);
        }

        Jinjava jinjava = new Jinjava();
        Map<String, Object> dealerInfo = Maps.newHashMap();
        dealerInfo.put("dealers", composeSelect(dealers));
        return jinjava.render(complaintPage, dealerInfo);
    }

    String composeOption(Dealer dealer) {
        return "<option value=" + dealer.id() + ">" + dealer.name() + "</option>";
    }

    String composeSelect(List<DBEntity> dealers)
    {
        StringBuilder content = new StringBuilder();
        if (dealers != null) {
            for (Object dealer : dealers) {
                content.append(composeOption((Dealer) dealer));
            }
        }
        return content.toString();
    }
}
