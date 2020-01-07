package com.symphony.ps.alf;

import clients.SymBotClient;
import com.symphony.ps.alf.listeners.IMListenerImpl;
import com.symphony.ps.alf.listeners.RoomListenerImpl;
import model.SymStaticMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlfBot implements SymStaticMain {
    private static final Logger log = LoggerFactory.getLogger(AlfBot.class);
    private static SymBotClient botClient;

    public static void main(String[] args) {
        new AlfBot();
    }

    public AlfBot() {
        try {
            SymBotClient botClient = SymBotClient.initBotRsa("config.json");
            botClient.getDatafeedEventsService().addListeners(
                new IMListenerImpl(),
                new RoomListenerImpl()
            );
        } catch (Exception e) {
            log.error("Error", e);
        }
    }

    public AlfBot(SymBotClient bot) {
        botClient = bot;
    }

    public static SymBotClient getBotClient() {
        return botClient;
    }
}
