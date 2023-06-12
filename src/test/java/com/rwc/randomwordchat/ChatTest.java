package com.rwc.randomwordchat;

import com.rwc.randomwordchat.model.chat.model.dto.ChatRoom;
import com.rwc.randomwordchat.model.chat.model.service.ChatRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Log4j2
public class ChatTest {

    private final ChatRepository repository;

    public ChatTest(ChatRepository repository) {
        this.repository = repository;
    }

    @Test
    public void chatRoomMakeTest() {
       ChatRoom room =  repository.createChatRoom("testroom");
        log.info("testroom : {}", room);
       assertNotNull(room.getRoomName().equals("testroom"));
    }
}
