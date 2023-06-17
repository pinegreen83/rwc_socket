package com.rwc.randomwordchat.model.chat.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "채팅 DTO")
public class Chat {

    @Schema(description = "채팅 타입")
    public enum MessageType {
        ENTER, TALK, LEAVE;
    }

    @Schema(description = "채팅 타입")
    private MessageType type;
    @Schema(description = "채팅이 작성되는 방 아이디")
    private String roomId;
    @Schema(description = "채팅 작성자")
    private String sender;
    @Schema(description = "채팅 내용")
    private String message;
    @Schema(description = "채팅 발송 시간")
    private String time;
}
