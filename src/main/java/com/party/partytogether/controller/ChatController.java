package com.party.partytogether.controller;

import com.party.partytogether.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
@CrossOrigin(origins = "http://localhost:8081")
public class ChatController {

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
//        ChatMessageDto chatMessageDto = new ChatMessageDto(chatMessage.getSenderId(), chatMessage.getReceiverId(), chatMessage.getContent(), chatMessage.getTimestamp());
        // 메시지 처리 로직
        return chatMessage;
    }

    // 메시지 전송
    @MessageMapping("/chat/{roomId}/sendMessage")
    @SendTo("/topic/chat.{roomId}")
    public ChatMessage sendMessage(@DestinationVariable String roomId, ChatMessage chatMessage) {
        // 메시지 처리 로직
        chatMessage.setTimestamp(LocalDateTime.now());


        return chatMessage;
    }

    // 채팅방에 사용자 추가
    @MessageMapping("/chat/{roomId}/join")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage joinRoom(@DestinationVariable String roomId, ChatMessage chatMessage) {
        // 사용자 추가 로직
//        String entranceMessage = chatMessage.getSenderId() + "님이 입장하셨습니다";
        return chatMessage;
    }

    @GetMapping("/chat")
    public String index() {
        return "chat/chat"; // chat.html을 반환
    }

    //== DTO ==//

    @Data
    @AllArgsConstructor
    static class ChatMessageDto{
        private Long senderId;
        private Long receiverId;
        private String content;
        private LocalDateTime timestamp;

    }



}