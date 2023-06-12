package com.rwc.randomwordchat.model.chat.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "채팅방 DTO")
public class ChatRoom {

    @Schema(description = "채팅방 번호")
    private String roomId;

    @Schema(description = "채팅방 이름")
    private String roomName;

    public static ChatRoom create(String roomName) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = roomName;
        return chatRoom;
    }

}
