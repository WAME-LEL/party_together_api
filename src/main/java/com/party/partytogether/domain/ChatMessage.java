package com.party.partytogether.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private String content;
    private LocalDateTime timestamp;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "chat_room_id") // 외래키
    private ChatRoom chatRoom;

}
