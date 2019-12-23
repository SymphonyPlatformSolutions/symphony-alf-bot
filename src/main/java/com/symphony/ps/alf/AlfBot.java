package com.symphony.ps.alf;

import clients.SymBotClient;
import com.symphony.ps.alf.listeners.IMListenerImpl;
import com.symphony.ps.alf.listeners.RoomListenerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlfBot {
    private static SymBotClient botClient;
    private static final Logger log = LoggerFactory.getLogger(AlfBot.class);

    public static void main(String[] args) {
        try {
            botClient = SymBotClient.initBotRsa("config.json");
            botClient.getDatafeedEventsService().addListeners(
                new IMListenerImpl(),
                new RoomListenerImpl()
            );
        } catch (Exception e) {
            log.error("Error", e);
        }
    }

    public static SymBotClient getBotClient() {
        return botClient;
    }

    public static void setBotClient(SymBotClient client) {
        botClient = client;
    }
}
