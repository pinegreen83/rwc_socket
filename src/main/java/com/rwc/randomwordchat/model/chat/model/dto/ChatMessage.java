package com.rwc.randomwordchat.model.chat.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "댓글 DTO")
public class ChatMessage {
    @Schema(description = "댓글 타입")
    private String type;

    @Schema(description = "댓글이 작성되는 방 아이디")
    private String roomId;

    @Schema(description = "댓글 작성자")
    private String sender;

    @Schema(description = "댓글 내용")
    private String message;

    public void newConnect() {
        this.type = "new";
    }

    public void talk() {
        this.type = "talk";
    }

    public void closeConnect() {
        this.type = "close";
    }
}
