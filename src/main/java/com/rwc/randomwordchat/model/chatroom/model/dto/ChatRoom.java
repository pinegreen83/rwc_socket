package com.rwc.randomwordchat.model.chatroom.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "채팅방 DTO")
public class ChatRoom {

    @Schema(description = "채팅방 번호")
    private String roomId;
    @Schema(description = "채팅방 이름")
    private String roomName;
    @Schema(description = "채팅방 인원수")
    private long userCount;
    @Schema(description = "사용자 리스트")
    private HashMap<String, String> userList = new HashMap<>();

    public static ChatRoom create(String roomName) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = roomName;
        return chatRoom;
    }

}
