package com.party.partytogether.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderId; // 보낸 사람 ID
    private String receiverId; // 받는 사람 ID (선택적, 1대1 채팅의 경우 사용)
    private String content;
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id") // 외래키
    private ChatRoom chatRoom;

    // 생성자, 게터, 세터 등
}
