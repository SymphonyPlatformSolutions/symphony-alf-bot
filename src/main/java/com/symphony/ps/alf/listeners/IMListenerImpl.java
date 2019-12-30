package com.symphony.ps.alf.listeners;

import clients.SymBotClient;
import com.symphony.ps.alf.services.AlfService;
import listeners.IMListener;
import model.InboundMessage;
import model.Stream;

public class IMListenerImpl implements IMListener {
    private SymBotClient botClient;

    public IMListenerImpl(SymBotClient botClient) {
        this.botClient = botClient;
    }

    public void onIMMessage(InboundMessage msg) {
        AlfService.processIncoming(botClient, msg);
    }

    public void onIMCreated(Stream stream) {}
}
