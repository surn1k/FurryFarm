package com.furrryfarm.handlers;

import com.furrryfarm.db.AccountTable;
import com.furrryfarm.db.FarmerTable;
import com.furrryfarm.db.SubscriptionTable;
import com.furrryfarm.db.entity.Account;
import com.furrryfarm.db.entity.DBEntity;
import com.furrryfarm.db.entity.Farmer;
import com.furrryfarm.db.entity.Subscription;
import com.furrryfarm.notifications.*;
import com.furrryfarm.utils.auth.UserRoleHelper;
import com.furrryfarm.utils.cookies.CookieManager;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DealerHandler extends GetHttpHandler {
    private final NotificationsPublisher notificationsPublisher;

    public DealerHandler() {
        LinkedList<Subscriber> subs = new LinkedList<Subscriber>(Arrays.asList(new TelegramSubscriber(), new ConsoleSubscriber()));
        this.notificationsPublisher = new NotificationsPublisher(subs);
    }

    @Override
    void handleGETRequest(HttpExchange httpExchange, List<String> components, Map<String, String> parameters) throws IOException {
        if (components.size() == 1
                && components.get(0).equals("dealer"))
        {
            HttpCookie idCookie = CookieManager.getCookieByName(httpExchange,"UserID");
            assert idCookie != null;
            int intID = Integer.parseInt(idCookie.getValue());
            UserRoleHelper.UserRole type = UserRoleHelper.getUserRole(intID);

            if (type == UserRoleHelper.UserRole.DEALER)
            {
                if (parameters.containsKey("ad"))
                {
                    String ad = parameters.get("ad");

                    try {
                        LinkedList<Subscription> subs = (new SubscriptionTable()).getSubscribers(intID);
                        LinkedList<Message> msgs = new LinkedList<>();
                        for (Subscription sub :
                                subs) {
                            String[] values = sub.getValues();
                            int subID = Integer.parseInt(values[1]);

                            LinkedList<DBEntity> entity = (new AccountTable()).getByID(subID);
                            if (entity.size() == 1) {
                                Account farmer = (Account) entity.get(0);
                                System.out.println("Login: " + farmer.login());
                                msgs.add(new Message(ad, farmer.login()));
                            }
                        }
                        notificationsPublisher.notifySubscribers(msgs);
                        returnString(httpExchange, "Message sent to all subscribers", 200);
                    } catch (Exception exception) {
                        System.out.println(exception);
                        returnString(httpExchange, "Something went wrong", 500);
                    }

                } else returnString(httpExchange, "Invalid request", 404);
            } else returnString(httpExchange, "You are not a dealer", 403);
        }

    }
}
