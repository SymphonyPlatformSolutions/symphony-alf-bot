package com.symphony.ps.alf.listeners;

import clients.SymBotClient;
import com.symphony.ps.alf.services.AlfService;
import listeners.RoomListener;
import model.InboundMessage;
import model.Stream;
import model.events.*;

public class RoomListenerImpl implements RoomListener {
    private SymBotClient botClient;

    public RoomListenerImpl(SymBotClient botClient) {
        this.botClient = botClient;
    }

    public void onRoomMessage(InboundMessage msg) {
        AlfService.processIncoming(botClient, msg);
    }

    public void onUserJoinedRoom(UserJoinedRoom userJoinedRoom) {}
    public void onRoomCreated(RoomCreated roomCreated) {}
    public void onRoomDeactivated(RoomDeactivated roomDeactivated) {}
    public void onRoomMemberDemotedFromOwner(RoomMemberDemotedFromOwner roomMemberDemotedFromOwner) {}
    public void onRoomMemberPromotedToOwner(RoomMemberPromotedToOwner roomMemberPromotedToOwner) {}
    public void onRoomReactivated(Stream stream) {}
    public void onRoomUpdated(RoomUpdated roomUpdated) {}
    public void onUserLeftRoom(UserLeftRoom userLeftRoom) {}
}
