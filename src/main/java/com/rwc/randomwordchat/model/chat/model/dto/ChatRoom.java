package com.rwc.randomwordchat.model.chat.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import java.util.UUID;

@Getter
@Setter
@Log4j2
public class ChatRoom {

    private String roomId;
    private String roomName;

    public static ChatRoom create(String roomName) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = roomName;
        return chatRoom;
    }

}
