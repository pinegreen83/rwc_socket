package com.rwc.randomwordchat.model.chat.controller;

import com.rwc.randomwordchat.model.chat.model.dto.Chat;
import com.rwc.randomwordchat.model.chatroom.model.mapper.ChatRoomRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository repository;

    @MessageMapping("/chat/enter")
    public void enter(@Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) {
        log.info("enter user : {}", chat);
        if(chat.getType().equals(Chat.MessageType.ENTER)) {
            String userUUID = repository.addUser(chat.getRoomId(), chat.getSender());
            if(userUUID != null) {
                repository.userCntPlus(chat.getRoomId());
                headerAccessor.getSessionAttributes().put("userUUID", userUUID);
                headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());
                chat.setMessage(chat.getSender() + "님이 입장하셨습니다.");
                messagingTemplate.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
                log.info(chat.getSender() + "님이 입장하셨습니다.");
            }
        }
    }

    @MessageMapping("/chat/message")
    public void sendMessage(@Payload Chat chat) {
        log.info("send message : {}", chat);
        if(chat.getType().equals(Chat.MessageType.TALK)) {
            chat.setMessage(chat.getMessage());
            messagingTemplate.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
            log.info(chat.getSender() + "님이 메시지 : " + chat.getMessage() + "를 전송했습니다.");
        }
    }

    @MessageMapping("/chat/leave")
    public void leave(@Payload Chat chat, Message message) {
        log.info("leave user : {}", chat);
        log.info("message : {}", message);

        if(chat.equals(Chat.MessageType.LEAVE)) {
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

            String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
            String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

            log.info("headAccessor : {}", headerAccessor);

            String username = repository.getUserName(roomId, userUUID);
            if(username != null) {
                repository.delUser(roomId, userUUID);
                repository.userCntMinus(roomId);

                log.info("User Disconnected : {}", username);
                Chat leave = Chat.builder()
                        .type(Chat.MessageType.LEAVE)
                        .sender(username)
                        .message(username + "님이 퇴장하셨습니다.")
                        .build();

                messagingTemplate.convertAndSend("/sub/chat/room/" + roomId, leave);
            }
        }
    }

    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("/api/chat/userlist")
    @ResponseBody
    public ArrayList<String> userList(String roomId) {
        return repository.getUserList(roomId);
    }

    // 채팅에 참여할 때 닉네임 중복 확인
    @GetMapping("/api/chat/duplicateName/{roomId}/{userName}")
    @ResponseBody
    public boolean isDuplicateName(@RequestParam String roomId, @RequestParam String userName) {
        return repository.isDuplicateName(roomId, userName);
    }
}
