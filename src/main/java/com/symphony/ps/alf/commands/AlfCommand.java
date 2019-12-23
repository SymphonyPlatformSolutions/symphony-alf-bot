package com.symphony.ps.alf.commands;

import clients.SymBotClient;
import com.symphony.ps.alf.AlfBot;
import model.InboundMessage;

public abstract class AlfCommand {
    public static final String commandName = "command";
    private InboundMessage msg;
    private String parameters;
    private SymBotClient botClient;

    public AlfCommand() {}

    public AlfCommand(InboundMessage msg) {
        this.msg = msg;
        this.botClient = AlfBot.getBotClient();
        this.parameters = msg.getMessageText().trim();
        if (this.parameters.contains(" ")) {
            this.parameters = this.parameters.substring(commandName.length() + 1);
        }
    }

    public static String getCommandName() {
        return commandName;
    }

    public InboundMessage getMsg() {
        return msg;
    }

    public String getParameters() {
        return parameters;
    }

    protected SymBotClient getBotClient() {
        return botClient;
    }

    public abstract void execute();
}
