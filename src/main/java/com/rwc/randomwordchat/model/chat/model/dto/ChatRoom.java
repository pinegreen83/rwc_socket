package com.rwc.randomwordchat.model.chat.model.dto;

import com.rwc.randomwordchat.model.chat.model.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Log4j2
@ToString
public class ChatRoom {

    private String roomId;
    private String roomName;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if(chatMessage.getType().equals("new")) {
            sessions.add(session);
            log.info("{} 님이 입장하셨습니다.", chatMessage.getSender());
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다.");
            log.info("sessions : {}", sessions.toString());
        }

        sendMessage(chatMessage, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}
