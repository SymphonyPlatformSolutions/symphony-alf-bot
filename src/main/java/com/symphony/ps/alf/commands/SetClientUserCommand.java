package com.symphony.ps.alf.commands;

import clients.SymBotClient;
import com.symphony.ps.alf.services.PermissionsService;
import model.InboundMessage;
import model.OutboundMessage;

public class SetClientUserCommand extends AlfCommand {
    public static final String commandName = "setclientuser";
    private static final String UNAUTHORISED_MSG = "You are not authorised to use this command";
    private static final String USAGE_MSG = "Usage: ?setclientuser @mention-the-user";
    private static final String SET_MSG = "<mention uid=\"%d\" /> has been set as an authorised Client user";

    public SetClientUserCommand() {}

    public SetClientUserCommand(SymBotClient botClient, InboundMessage msg) {
        super(botClient, msg);
    }

    public void execute() {
        String streamId = getMsg().getStream().getStreamId();
        long userId = getMsg().getUser().getUserId();

        if (!PermissionsService.isOwner(getBotClient(), streamId, userId)) {
            getBotClient().getMessagesClient().sendMessage(streamId, new OutboundMessage(UNAUTHORISED_MSG));
            return;
        } else if (getMsg().getMentions().isEmpty()) {
            getBotClient().getMessagesClient().sendMessage(streamId, new OutboundMessage(USAGE_MSG));
            return;
        }

        long targetUser = getMsg().getMentions().get(0);
        PermissionsService.setClient(streamId, targetUser);
        getBotClient().getMessagesClient().sendMessage(streamId, new OutboundMessage(String.format(SET_MSG, targetUser)));
    }
}
