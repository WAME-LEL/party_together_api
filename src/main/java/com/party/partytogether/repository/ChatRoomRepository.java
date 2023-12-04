package com.party.partytogether.repository;


import com.party.partytogether.domain.ChatRoom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {
    private final EntityManager em;

    public void save(ChatRoom chatRoom){
        em.persist(chatRoom);
    }

    public ChatRoom findOne(Long chatRoomId){
        return em.find(ChatRoom.class, chatRoomId);
    }

    public ChatRoom findOneByMember(Long memberId){
        return em.createQuery("select cr from ChatRoom cr where cr.one.id = :memberId", ChatRoom.class)
                .setParameter("memberId", memberId)
                .getResultList()
                .get(0);
    }

    public List<ChatRoom> findAll(){
        return em.createQuery("select cr from ChatRoom cr", ChatRoom.class)
                .getResultList();
    }


}
