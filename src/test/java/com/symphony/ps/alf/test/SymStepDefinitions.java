package com.symphony.ps.alf.test;

import clients.SymBotClient;
import clients.symphony.api.MessagesClient;
import clients.symphony.api.StreamsClient;
import io.cucumber.java8.En;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import listeners.ConnectionListener;
import listeners.ElementsListener;
import listeners.IMListener;
import listeners.RoomListener;
import model.*;
import org.mockito.ArgumentCaptor;
import org.reflections.Reflections;
import utils.SymMessageParser;
import utils.TagBuilder;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SymStepDefinitions implements En {
    private Reflections reflections = new Reflections("");

    // Mocks
    private SymBotClient botClient = mock(SymBotClient.class);
    private MessagesClient messagesClient = mock(MessagesClient.class);
    private StreamsClient streamsClient = mock(StreamsClient.class);

    // Listeners
    private IMListener imListener = locateImplementation(IMListener.class);
    private RoomListener roomListener = locateImplementation(RoomListener.class);
    private ConnectionListener connectionListener = locateImplementation(ConnectionListener.class);
    private ElementsListener elementsListener = locateImplementation(ElementsListener.class);

    // Captors
    private ArgumentCaptor<String> streamIdCaptor = ArgumentCaptor.forClass(String.class);
    private ArgumentCaptor<OutboundMessage> msgCaptor = ArgumentCaptor.forClass(OutboundMessage.class);

    // Data
    private User user = getSampleUser();
    private String streamId = "x";
    private String messageText;
    private List<Attachment> attachments;
    private OutboundMessage outMsg;
    private static final String mlTemplate = getMLTemplate();

    public SymStepDefinitions() throws Exception {
        SymMessageParser.createInstance(botClient);
        when(botClient.getMessagesClient()).thenReturn(messagesClient);
        when(botClient.getStreamsClient()).thenReturn(streamsClient);

        Given("the stream id is {string}", (String streamId) -> this.streamId = streamId);

        Given("a Symphony user types {string}", (String messageText) -> this.messageText = messageText);

        Given("a Symphony user attaches {string}", (String fileName) -> {
            if (attachments == null) {
                attachments = new ArrayList<>();
            }
            Attachment attachment = new Attachment();
            attachment.setName(fileName);
            attachments.add(attachment);
        });

        Given("the user is an owner of the room", () -> {
            throw new cucumber.api.PendingException();
        });

        Given("the user is not an owner of the room", () -> {
            throw new cucumber.api.PendingException();
        });

        When("a Symphony user sends the message in an IM", () -> {
            imListener.onIMMessage(getInboundMessage());
            verify(getMessagesClient()).sendMessage(streamIdCaptor.capture(), msgCaptor.capture());
            outMsg = msgCaptor.getValue();
        });

        When("a Symphony user sends the message in a room", () -> {
            roomListener.onRoomMessage(getInboundMessage());
            verify(getMessagesClient()).sendMessage(streamIdCaptor.capture(), msgCaptor.capture());
            outMsg = msgCaptor.getValue();
        });

        Then("The bot should display the following response",
            (String expected) -> assertEquals(expected, outMsg.getMessage()));

        Then("The bot should send this data {string}",
            (String expected) -> assertEquals(expected, outMsg.getData()));

        Then("The bot should send this attachment {string}",
            (String expected) -> assertEquals(expected, outMsg.getAttachment()[0].getName()));
    }

    public InboundMessage getInboundMessage() {
        InboundMessage inMsg = new InboundMessage();
        Stream stream = new Stream();
        stream.setStreamId(streamId);
        inMsg.setStream(stream);
        inMsg.setUser(user);
        inMsg.setMessage(String.format(mlTemplate, messageText));
        inMsg.setAttachments(attachments);
        return inMsg;
    }

    public IMListener getImListener() {
        return imListener;
    }

    public RoomListener getRoomListener() {
        return roomListener;
    }

    public ConnectionListener getConnectionListener() {
        return connectionListener;
    }

    public ElementsListener getElementsListener() {
        return elementsListener;
    }

    public MessagesClient getMessagesClient() {
        return messagesClient;
    }

    public StreamsClient getStreamsClient() {
        return streamsClient;
    }

    private User getSampleUser() {
        User user = new User();
        user.setDisplayName("John Doe");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserId(123456789L);
        user.setEmail("john@doe.com");
        user.setUsername("john.doe");
        return user;
    }

    private static String getMLTemplate() {
        return TagBuilder.builder("div")
            .addField("data-format", "PresentationML")
            .addField("data-version", "2.0")
            .addField("class", "wysiwyg")
            .setContents("<p>%s</p>")
            .build();
    }

    private <T> T locateImplementation(Class<T> clazz) {
        Set<Class<? extends T>> impls = reflections.getSubTypesOf(clazz);
        if (impls.isEmpty()) {
            return null;
        }
        Class<? extends T> impl = impls.iterator().next();
        try {
            try {
                return impl.getConstructor(SymBotClient.class).newInstance(botClient);
            } catch (NoSuchMethodException e) {
                return impl.getConstructor().newInstance();
            }
        } catch (Exception e) {
            return null;
        }
    }
}
