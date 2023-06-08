package com.rwc.randomwordchat.model.chat.model.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String type;
    private String roomId;
    private String sender;
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
