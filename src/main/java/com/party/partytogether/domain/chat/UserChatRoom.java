package com.party.partytogether.domain.chat;


import com.party.partytogether.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserChatRoom {
    @Id
    @GeneratedValue
    @Column(name = "user_chat_room_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

//    @ManyToOne
//    @JoinColumn(name = "chat_room_id")
//    private ChatRoom chatRoom;

    //==생성 메서드==//
    public static void createUserChatRoom(Member member, ChatRoom chatRoom){
        UserChatRoom userChatRoom = new UserChatRoom();
        userChatRoom.setMember(member);
//        userChatRoom.setChatRoom(chatRoom);
    }

}
