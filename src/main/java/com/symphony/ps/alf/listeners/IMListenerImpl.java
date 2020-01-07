package com.symphony.ps.alf.listeners;

import com.symphony.ps.alf.AlfBot;
import com.symphony.ps.alf.services.AlfService;
import listeners.IMListener;
import model.InboundMessage;
import model.Stream;

public class IMListenerImpl implements IMListener {
    public void onIMMessage(InboundMessage msg) {
        AlfService.processIncoming(AlfBot.getBotClient(), msg);
    }

    public void onIMCreated(Stream stream) {}
}
