package com.symphony.ps.alf.test;

import clients.SymBotClient;
import clients.symphony.api.MessagesClient;
import com.symphony.ps.alf.listeners.IMListenerImpl;
import io.cucumber.java8.En;
import model.InboundMessage;
import model.OutboundMessage;
import model.Stream;
import org.mockito.ArgumentCaptor;
import utils.SymMessageParser;
import utils.TagBuilder;
import static org.mockito.Mockito.*;

public class AlfStepDefinitions implements En {
    SymBotClient botClient = mock(SymBotClient.class);
    MessagesClient messagesClient = mock(MessagesClient.class);
    ArgumentCaptor<String> streamIdCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<OutboundMessage> msgCaptor = ArgumentCaptor.forClass(OutboundMessage.class);
    IMListenerImpl imListener;

    private String mlTemplate = TagBuilder.builder("div")
        .addField("data-format", "PresentationML")
        .addField("data-version", "2.0")
        .addField("class", "wysiwyg")
        .setContents("<p>%s</p>")
        .build();

    public AlfStepDefinitions() {
        SymMessageParser.createInstance(botClient);
        imListener = new IMListenerImpl(botClient);
        when(botClient.getMessagesClient()).thenReturn(messagesClient);
    }

    public InboundMessage getInboundMessage(String streamId, String text) {
        InboundMessage inMsg = new InboundMessage();
        Stream stream = new Stream();
        stream.setStreamId(streamId);
        inMsg.setStream(stream);
        inMsg.setMessage(String.format(mlTemplate, text));
        return inMsg;
    }
}
