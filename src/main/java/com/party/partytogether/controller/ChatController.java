package com.party.partytogether.controller;

import com.party.partytogether.domain.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "http://localhost:8081")
public class ChatController {

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        // 메시지 처리 로직
        return chatMessage;
    }

    @GetMapping("/chat")
    public String index() {
        return "chat/chat"; // chat.html을 반환
    }
}