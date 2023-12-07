package com.party.partytogether.repository.chat;


import com.party.partytogether.domain.chat.ChatRoom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {
    private final EntityManager em;

    // 채팅방 저장
    public ChatRoom save(ChatRoom chatRoom){
        em.persist(chatRoom);
        return chatRoom;
    }
    // 채팅방 삭제
    public ChatRoom findOne(Long chatRoomId){
        return em.find(ChatRoom.class, chatRoomId);
    }

    // 회원ID로 채팅방 조회
    public List<ChatRoom> findOneByMember(Long memberId){
        return em.createQuery("select cr from ChatRoom cr where cr.one.id = :memberId or cr.other.id = :memberId", ChatRoom.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    // 자신의ID와 상대방ID 로 채팅방 조회
    public ChatRoom findOneByOneOrOther(Long oneId, Long otherId){
        return em.createQuery("select cr from ChatRoom cr where (cr.one.id =: oneId and cr.other.id = :otherId) or(cr.one.id =:otherId and cr.other.id = :oneId)", ChatRoom.class)
                .setParameter("oneId", oneId)
                .setParameter("otherId", otherId)
                .getSingleResult();
    }

    // 채팅방 전체 조회
    public List<ChatRoom> findAll(){
        return em.createQuery("select cr from ChatRoom cr", ChatRoom.class)
                .getResultList();
    }


}
