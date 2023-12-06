package com.party.partytogether.domain.chat;

import com.party.partytogether.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue
    @Column(name = "chat_room_id")
    private Long id;

    private String name;    // 채팅방 이름

    @ManyToOne
    @JoinColumn(name = "one_id")
    private Member one; // 채팅방 생성자

    @ManyToOne
    @JoinColumn(name = "other_id")
    private Member other;   // 채팅방 상대방

    // 채팅방에 속한 메시지들
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> messages = new ArrayList<>();

    //==생성 메서드==//
    public static ChatRoom createChatRoom(String name, Member one, Member other) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        chatRoom.setOne(one);
        chatRoom.setOther(other);

        return chatRoom;
    }

    //==연관 관계 메서드==//
    public void addChatMessage(ChatMessage chatMessage){
        messages.add(chatMessage);
    }


}