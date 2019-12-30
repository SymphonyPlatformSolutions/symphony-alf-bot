package com.symphony.ps.alf.commands;

import clients.SymBotClient;
import com.symphony.ps.alf.services.PermissionsService;
import model.InboundMessage;
import model.OutboundMessage;

public class WhoamiCommand extends AlfCommand {
    public static final String commandName = "whoami";
    private static final String ADMIN_MSG =
        "The room administrator - which allows you to set the client users who can interact with this bot.<br/>" +
        "To set their status please use ?setclientuser @mention-the-user";
    private static final String STRANGER_MSG = "Please speak to the room admin to enable you to interact with me";
    private static final String CLIENT_MSG = "Client user - please type ?help to see the commands available to you";

    public WhoamiCommand() {}

    public WhoamiCommand(SymBotClient botClient, InboundMessage msg) {
        super(botClient, msg);
    }

    public void execute() {
        String streamId = getMsg().getStream().getStreamId();
        long userId = getMsg().getUser().getUserId();

        getBotClient().getMessagesClient().sendMessage(streamId, new OutboundMessage(getMessage(streamId, userId)));
    }

    private String getMessage(String streamId, long userId) {
        if (PermissionsService.isOwner(getBotClient(), streamId, userId)) {
            return ADMIN_MSG;
        } else if (PermissionsService.isClient(streamId, userId)) {
            return CLIENT_MSG;
        }
        return STRANGER_MSG;
    }
}
