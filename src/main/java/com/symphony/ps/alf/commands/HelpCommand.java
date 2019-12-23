package com.symphony.ps.alf.commands;

import model.InboundMessage;
import model.OutboundMessage;

public class HelpCommand extends AlfCommand {
    public static final String commandName = "help";
    private static final String HELP_MSG = "Broker-Dealer Algo Bot - created using ALgo Framework (ALF) managed by Symphony" +
        "<br/><br/>COMMANDS<br/>" +
        "  ?help - baseline methods<br/>" +
        "  ?whoami - describes the roll you have in the room to the bot<br/>" +
        "  ?setclientuser - sets the user who can interact with the bot<br/>" +
        "  ?orders [mic_code] [ool|orders out of limit]<br/>" +
        "  ?orders [mic_code] [moc|market on close]<br/>" +
        "  ?ordstatus [order_id]<br/>" +
        "  ?ordstatus [mic_code] [instrument] [instrument_type]<br/>" +
        "  ?market [mic_code] [+] top 5 instruments with a positive movement on the day<br/>" +
        "  ?market [mic_code] [-] top 5 instruments with a negative movement on the day<br/>" +
        "  ?market [mic_code] [ss] top 5 largest short sell moves of the day<br/>" +
        "  ?market [mic_code] [vol] top 5 largest moves by volume on the day<br/><br/>" +
        "For more information please visit: http://github.com/....";

    public HelpCommand() {}

    public HelpCommand(InboundMessage msg) {
        super(msg);
    }

    public void execute() {
        String streamId = getMsg().getStream().getStreamId();
        getBotClient().getMessagesClient().sendMessage(streamId, new OutboundMessage(HELP_MSG));
    }
}
