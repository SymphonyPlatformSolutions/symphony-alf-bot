package com.symphony.ps.alf.listeners;

import com.symphony.ps.alf.services.AlfService;
import listeners.IMListener;
import model.InboundMessage;
import model.Stream;

public class IMListenerImpl implements IMListener {
    public void onIMMessage(InboundMessage msg) {
        AlfService.processIncoming(msg);
    }

    public void onIMCreated(Stream stream) {}
}
