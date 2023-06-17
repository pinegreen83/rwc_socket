package com.rwc.randomwordchat.model.chat.controller;

import com.rwc.randomwordchat.model.chat.model.dto.Chat;
import com.rwc.randomwordchat.model.chatroom.model.mapper.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;

    @MessageMapping("/chat/message")
    public void message(Chat message) {
        if(message.getType().equals(Chat.MessageType.ENTER)) {
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
            log.info(message.getSender() + "님이 입장하셨습니다.");
        }
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        log.info(message.getSender() + "님이 메시지 : " + message.getMessage() + "를 전송했습니다.");
    }
}
