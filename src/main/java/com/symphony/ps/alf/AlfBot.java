package com.symphony.ps.alf;

import clients.SymBotClient;
import com.symphony.ps.alf.listeners.IMListenerImpl;
import com.symphony.ps.alf.listeners.RoomListenerImpl;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlfBot {
    private static final Logger log = LoggerFactory.getLogger(AlfBot.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();

        try {
            SymBotClient botClient = SymBotClient.initBotRsa("config.json");

            botClient.getDatafeedEventsService().addListeners(
                new IMListenerImpl(botClient),
                new RoomListenerImpl(botClient)
            );
        } catch (Exception e) {
            log.error("Error", e);
        }
    }
}
