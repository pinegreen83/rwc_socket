package com.rwc.randomwordchat.model.chat.controller;

import com.rwc.randomwordchat.model.chat.model.dto.ChatRoom;
import com.rwc.randomwordchat.model.chat.model.service.ChatService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        log.info("controller room name : {}", name);
        ChatRoom chatRoom = service.createRoom(name);
        log.info("방이 정상적으로 생성됐습니다. {}", chatRoom);
        return chatRoom;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChatRoom>> findAllRoom() {
        List<ChatRoom> list = service.findAllRoom();
        HttpStatus status = HttpStatus.ACCEPTED;
        log.info("존재하는 모든 방을 찾았습니다.. {}", list.toString());
        return new ResponseEntity<>(list, status);
    }

    @GetMapping("/find/{roomId}")
    public ResponseEntity<Map<String, Object>> findRoomById(@PathVariable String roomId) {
        Map<String, Object> map = new HashMap<>();
        ChatRoom chatRoom = service.findRoomById(roomId);
        HttpStatus status = HttpStatus.NO_CONTENT;
        if(chatRoom == null) {
            map.put("find_room", null);
            log.info("방이 존재하지 않습니다.");
        }
        else {
            map.put("find_room", chatRoom);
            status = HttpStatus.ACCEPTED;
            log.info("정상적으로 방이 존재함.");
        }
        return new ResponseEntity<>(map, status);
    }
}
