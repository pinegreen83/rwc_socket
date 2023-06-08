package com.rwc.randomwordchat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rwc.randomwordchat.model.chat.model.dto.ChatMessage;
import com.rwc.randomwordchat.model.chat.model.dto.ChatRoom;
import com.rwc.randomwordchat.model.chat.model.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Log4j2
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService service;

    //양방향 데이터 통신
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : {}", payload);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = service.findRoomById(chatMessage.getRoomId());
        room.handleActions(session, chatMessage, service);
    }

}
