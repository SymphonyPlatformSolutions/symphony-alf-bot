package com.symphony.ps.alf.commands;

import com.symphony.ps.alf.services.AlfService;
import com.symphony.ps.alf.services.PermissionsService;
import model.InboundMessage;

public class WhoamiCommand extends AlfCommand {
    public static final String commandName = "whoami";
    private static final String ADMIN_MSG =
        "The room administrator - which allows you to set the client users who can interact with this bot.<br/>" +
        "To set their status please use ?setclientuser @mention-the-user";
    private static final String STRANGER_MSG = "Please speak to the room admin to enable you to interact with me";
    private static final String CLIENT_MSG = "Client user - please type ?help to see the commands available to you";

    public WhoamiCommand() {}

    public WhoamiCommand(InboundMessage msg) {
        super(msg);
    }

    public void execute() {
        String streamId = getMsg().getStream().getStreamId();
        long userId = getMsg().getUser().getUserId();

        AlfService.sendMessage(streamId, getMessage(streamId, userId));
    }

    private String getMessage(String streamId, long userId) {
        if (PermissionsService.isOwner(streamId, userId)) {
            return ADMIN_MSG;
        } else if (PermissionsService.isClient(streamId, userId)) {
            return CLIENT_MSG;
        }
        return STRANGER_MSG;
    }
}
