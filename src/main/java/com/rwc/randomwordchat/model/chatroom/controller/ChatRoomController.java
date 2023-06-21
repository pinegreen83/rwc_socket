package com.rwc.randomwordchat.model.chatroom.controller;

import com.rwc.randomwordchat.model.chatroom.model.dto.ChatRoom;
import com.rwc.randomwordchat.model.chatroom.model.mapper.ChatRoomRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "채팅방", description = "채팅방 관련 api입니다.")
@RequestMapping("/api/chatroom")
@RestController
@Log4j2
public class ChatRoomController {

    private final ChatRoomRepository repository;

    public ChatRoomController(ChatRoomRepository repository) {
        this.repository = repository;
    }

    // 모든 채팅방 목록 반환
    @Operation(summary = "채팅방 목록 반환 메서드", description = "현재 존재하는 모든 채팅방을 출력해주는 메서드입니다.")
    @GetMapping("/all")
    public ResponseEntity<List<ChatRoom>> findAllRoom() {
        List<ChatRoom> list = repository.findAllRoom();
        HttpStatus status = HttpStatus.OK;
        log.info("존재하는 모든 방을 찾았습니다. {}", list.toString());
        return new ResponseEntity<>(list, status);
    }

    // 채팅방 생성
    @Operation(summary = "채팅방 생성 메서드", description = "채팅방을 생성하는 메서드입니다.")
    @PostMapping("/create")
    public ChatRoom createRoom(@RequestParam String name) {
        log.info("controller room name : {}", name);
        ChatRoom chatRoom = repository.createChatRoom(name);
        log.info("방이 정상적으로 생성됐습니다. {}", chatRoom);
        return chatRoom;
    }

    // 채팅방 제거
    @Operation(summary = "채팅방 제거 메서드", description = "채팅방을 제거하는 메서드입니다.")
    @GetMapping("/remove/{roomId}")
    public ResponseEntity<Map<String, Object>> removeRoomById(@PathVariable String roomId) {
        Map<String, Object> map = new HashMap<>();
        boolean isRemoved = repository.removeChatRoom(roomId);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(isRemoved) {
            map.put("remove_room", isRemoved);
            log.info("방이 성공적으로 삭제되었습니다.");
            status = HttpStatus.OK;
        }
        else {
            map.put("remove_room", isRemoved);
            log.info("방 제거에 실패하였습니다.");
        }
        return new ResponseEntity<>(map, status);
    }

    // 채팅방 입장
    @Operation(summary = "채팅방 입장", description = "입장하고자 하는 채팅방의 id를 입력하여 해당 채팅방으로 입장하는 메서드입니다.")
    @GetMapping("/enter/{roomId}")
    public ResponseEntity<Map<String, Object>> enterRoomById(@PathVariable String roomId) {
        Map<String, Object> map = new HashMap<>();
        ChatRoom chatRoom = repository.findRoomById(roomId);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(chatRoom == null) {
            map.put("enter_room", null);
            log.info("방이 없습니다.");
        }
        else {
            map.put("enter_room", chatRoom);
            status = HttpStatus.OK;
            log.info("정상적으로 방에 입장하였습니다.");
        }
        return new ResponseEntity<>(map, status);
    }

    // 특정 채팅방 조회
    @Operation(summary = "특정 채팅방 조회", description = "원하는 채팅방의 id를 입력하여 해당 채팅방을 조회하는 메서드입니다.")
    @GetMapping("/find/{roomId}")
    public ResponseEntity<Map<String, Object>> findRoomById(@PathVariable String roomId) {
        Map<String, Object> map = new HashMap<>();
        ChatRoom chatRoom = repository.findRoomById(roomId);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(chatRoom == null) {
            map.put("find_room", null);
            log.info("방이 존재하지 않습니다.");
        }
        else {
            map.put("find_room", chatRoom);
            status = HttpStatus.OK;
            log.info("정상적으로 방이 존재함.");
        }
        return new ResponseEntity<>(map, status);
    }
}
