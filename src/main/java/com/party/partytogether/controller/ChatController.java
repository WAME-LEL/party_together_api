package com.party.partytogether.controller;

import com.party.partytogether.domain.chat.ChatMessage;
import com.party.partytogether.domain.chat.ChatRoom;
import com.party.partytogether.service.chat.ChatMessageService;
import com.party.partytogether.service.chat.ChatRoomService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Controller
@CrossOrigin(origins = "http://localhost:8081")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    // 공용 메시지 전송
    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        LocalDateTime timestamp = LocalDateTime.now().plusHours(9);
        chatMessage.setTimestamp(timestamp);

        return chatMessage;
    }

    // 채팅방으로 메시지 전송
    @MessageMapping("/chat/{roomId}/sendMessage")
    @SendTo("/topic/chat.{roomId}")
    public ChatMessage sendMessage(@DestinationVariable String roomId, ChatMessage chatMessage) {
        // 메시지 처리 로직
        LocalDateTime timestamp = LocalDateTime.now().plusHours(9);
        chatMessage.setTimestamp(timestamp);
        ChatRoom room = chatRoomService.findOne(Long.parseLong(roomId));
        chatMessageService.save(chatMessage.getSenderId(), chatMessage.getReceiverId(), chatMessage.getContent(), timestamp, room);


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

    // 채팅방 페이지로 이동
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