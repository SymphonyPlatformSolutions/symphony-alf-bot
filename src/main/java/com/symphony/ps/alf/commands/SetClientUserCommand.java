package com.symphony.ps.alf.commands;

import com.symphony.ps.alf.services.AlfService;
import com.symphony.ps.alf.services.PermissionsService;
import model.InboundMessage;

public class SetClientUserCommand extends AlfCommand {
    public static final String commandName = "setclientuser";
    private static final String UNAUTHORISED_MSG = "You are not authorised to use this command";
    private static final String USAGE_MSG = "Usage: ?setclientuser @mention-the-user";
    private static final String SET_MSG = "<mention uid=\"%d\" /> has been set as an authorised Client user";

    public SetClientUserCommand() {}

    public SetClientUserCommand(InboundMessage msg) {
        super(msg);
    }

    public void execute() {
        String streamId = getMsg().getStream().getStreamId();
        long userId = getMsg().getUser().getUserId();

        if (!PermissionsService.isOwner(streamId, userId)) {
            AlfService.sendMessage(streamId, UNAUTHORISED_MSG);
            return;
        } else if (getMsg().getMentions().isEmpty()) {
            AlfService.sendMessage(streamId, USAGE_MSG);
            return;
        }

        long targetUser = getMsg().getMentions().get(0);
        PermissionsService.setClient(streamId, targetUser);
        AlfService.sendMessage(streamId, String.format(SET_MSG, targetUser));
    }
}
