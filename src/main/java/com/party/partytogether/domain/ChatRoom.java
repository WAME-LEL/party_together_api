package com.party.partytogether.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private String name;

    @ManyToOne
    @JoinColumn(name = "one_id")
    private Member one;

    @ManyToOne
    @JoinColumn(name = "other_id")
    private Member other;


    @OneToMany(mappedBy = "chatRoom")
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