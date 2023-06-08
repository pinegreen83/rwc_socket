package com.rwc.randomwordchat.model.chat.controller;

import com.rwc.randomwordchat.model.chat.model.dto.ChatRoom;
import com.rwc.randomwordchat.model.chat.model.service.ChatService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin
@Log4j2
public class ChatController {

    private final ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ChatRoom createRoom(@RequestParam String name) {
        ChatRoom chatRoom = service.createRoom(name);
        log.info("정상적으로 생성됨. {}", chatRoom);
        return chatRoom;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChatRoom>> findAllRoom() {
        List<ChatRoom> list = service.findAllRoom();
        HttpStatus status = HttpStatus.ACCEPTED;
        log.info("정상적으로 찾았음. {}", list.toString());
        return new ResponseEntity<>(list, status);
    }
}
