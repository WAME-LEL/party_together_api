package com.party.partytogether.domain.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ChatMessage {
    @Id
    @GeneratedValue
    @Column(name = "chat_message_id")
    private Long id;

    private Long senderId; // 보낸 사람 ID
    private Long receiverId; // 받는 사람 ID (선택적, 1대1 채팅의 경우 사용)
    private String content; // 메시지 내용
    private LocalDateTime timestamp;    // 메시지 보낸 시간


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "chat_room_id") // 외래키
    private ChatRoom chatRoom;  // 채팅방


    //==생성 메서드==//
    public static ChatMessage createChatMessage(Long senderId, Long receiverId, String content, LocalDateTime timestamp, ChatRoom chatRoom){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderId(senderId);
        chatMessage.setReceiverId(receiverId);
        chatMessage.setContent(content);
        chatMessage.setTimestamp(timestamp);
        chatMessage.setChatRoom(chatRoom);

        return chatMessage;
    }

}
