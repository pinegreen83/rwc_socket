package com.rwc.randomwordchat.model.chat.model.service;

import com.rwc.randomwordchat.model.chat.model.dto.ChatRoom;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface ChatService {

    List<ChatRoom> findAllRoom();

    ChatRoom findRoomById(String roomId);

    ChatRoom createRoom(String roomName);

    <T> void sendMessage(WebSocketSession session, T message);
}
