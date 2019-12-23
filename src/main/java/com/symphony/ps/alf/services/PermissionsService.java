package com.symphony.ps.alf.services;

import com.symphony.ps.alf.AlfBot;
import java.util.HashSet;
import java.util.Set;

public class PermissionsService {
    private static Set<String> clientList = new HashSet<>();

    public static boolean isOwner(String streamId, long userId) {
        return AlfBot.getBotClient().getStreamsClient()
            .getRoomMembers(streamId)
            .stream()
            .anyMatch(m -> m.getId() == userId && m.getOwner());
    }

    public static boolean isClient(String streamId, long userId) {
        return clientList.contains(streamId + userId);
    }

    public static boolean setClient(String streamId, long userId) {
        return clientList.add(streamId + userId);
    }

    public static boolean removeClient(String streamId, long userId) {
        return clientList.remove(streamId + userId);
    }
}
