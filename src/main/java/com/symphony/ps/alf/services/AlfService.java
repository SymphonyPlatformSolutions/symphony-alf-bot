package com.symphony.ps.alf.services;

import com.symphony.ps.alf.AlfBot;
import com.symphony.ps.alf.commands.AlfCommand;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import model.InboundMessage;
import model.OutboundMessage;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlfService {
    private static final Logger log = LoggerFactory.getLogger(AlfService.class);
    private static Reflections reflections = new Reflections("");
    private static Map<String, Class<? extends AlfCommand>> commands = new HashMap<>();

    public static void processIncoming(InboundMessage msg) {
        AlfCommand command = null;
        try {
            command = Objects.requireNonNull(getCommand(msg.getMessageText()))
                .getConstructor(InboundMessage.class)
                .newInstance(msg);
        } catch (Exception e) {
            log.error("Error occurred deriving command from message", e);
        }
        if (command != null) {
            command.execute();
        }
    }

    public static InboundMessage sendMessage(String streamId, String msgText) {
        return AlfBot.getBotClient().getMessagesClient()
            .sendMessage(streamId, new OutboundMessage(msgText));
    }

    public static Class<? extends AlfCommand> getCommand(String messageText) {
        if (commands.entrySet().isEmpty()) {
            init();
        }
        messageText = messageText.trim();
        if (!messageText.startsWith("?")) {
            return null;
        }
        int spaceIndex = messageText.contains(" ") ? messageText.indexOf(" ") : messageText.length();
        String commandText = messageText.substring(1, spaceIndex);
        return commands.get(commandText);
    }

    private static void init() {
        for (Class<? extends AlfCommand> commandClass : locateImplementations(AlfCommand.class)) {
            try {
                String commandName = (String) commandClass.getDeclaredField("commandName")
                    .get(commandClass.getConstructor().newInstance());
                commands.put(commandName, commandClass);
            } catch (Exception e) {
                log.error("Cannot find command name", e);
            }
        }
    }

    public static <T> Set<Class<? extends T>> locateImplementations(Class<T> clazz) {
        return reflections.getSubTypesOf(clazz);
    }

    public static <T> T locateImplementation(Class<T> clazz) {
        Set<Class<? extends T>> impls = locateImplementations(clazz);
        if (impls.isEmpty()) {
            return null;
        }
        Class<? extends T> impl = impls.iterator().next();
        try {
            return impl.getConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
