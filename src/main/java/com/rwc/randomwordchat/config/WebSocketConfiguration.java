package com.rwc.randomwordchat.config;

import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//@RequiredArgsConstructor
//@Configuration
//@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

//    private final WebSocketHandler webSocketHandler;
//
//    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry
//                .addHandler(webSocketHandler, "/room")
//                .setAllowedOrigins("*");
    }

}
