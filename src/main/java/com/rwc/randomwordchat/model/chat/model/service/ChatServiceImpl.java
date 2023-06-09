package com.rwc.randomwordchat.model.chat.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rwc.randomwordchat.model.chat.model.dto.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatServiceImpl implements ChatService{

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    @Override
    public List<ChatRoom> findAllRoom() {
        List<ChatRoom> list = new ArrayList<>(chatRooms.values());
        log.info("찾은 모든 방들 : {}", list.toString());
        return list;
    }

    @Override
    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    @Override
    public ChatRoom createRoom(String roomName) {
        log.info("roomName : {}", roomName);
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .roomName(roomName)
                .build();
        log.info("방이 생성되었습니다. {}", chatRoom.toString());
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }

    @Override
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            log.info("정상적으로 메시지를 전송했습니다.");
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
