package com.rwc.randomwordchat.model.message.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MessageUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private MessageUtil() {}

    public static Message getObject(final String message) throws Exception {
        log.info("messageutil : {}", message);
        return objectMapper.readValue(message, Message.class);
    }

    public static String getString(final Message message) throws Exception {
        return objectMapper.writeValueAsString(message);
    }
}
