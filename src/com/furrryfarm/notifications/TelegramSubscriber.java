package com.furrryfarm.notifications;

import com.furrryfarm.utils.requests.GetJsonRequestBuilder;
import com.furrryfarm.utils.requests.Request;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.LinkedList;

public class TelegramSubscriber implements Subscriber {
    private final String token;

    public TelegramSubscriber(String token) {
        this.token = token;
    }

    private String buildSendMessageUrl(int chatID, String text) {
        return "https://api.telegram.org/bot" + token + "/sendMessage?chat_id=" +
                chatID + "&parse_mode=Markdown&text=" + text;
    }

    private String buildGetUpdatesUrl() {
        return "https://api.telegram.org/bot" + token + "/getUpdates";
    }

    // TODO: memorization for username -> chat_id (not important)
    private int getChatID(String userName) throws IOException {
        Request updatesRequest = new Request(new GetJsonRequestBuilder());
        JsonObject response = (JsonObject) updatesRequest.make(buildGetUpdatesUrl(), null);
        JsonArray updates = response.getAsJsonArray("result");

        for (int i = 0; i < updates.size(); i++) {
            JsonObject update = updates.get(i).getAsJsonObject();
            JsonObject message = update.get("message").getAsJsonObject();
            JsonObject from = message.get("from").getAsJsonObject();

            if (from.get("username").getAsString().equals(userName)) {
                JsonObject chat = message.get("chat").getAsJsonObject();
                return chat.get("id").getAsInt();
            }
        }

        return -1; // TODO: throw an exception
    }

    @Override
    public void sendMessages(LinkedList<Message> messages) throws IOException {
        for (Message msg : messages) {
            int chatID = getChatID(msg.username);

            Request sendRequest = new Request(new GetJsonRequestBuilder());
            JsonObject response = (JsonObject) sendRequest.make(buildSendMessageUrl(chatID, msg.text), null);
            // TODO: check that response is OK
        }
    }
}
