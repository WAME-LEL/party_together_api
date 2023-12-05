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

    public ChatRoom save(ChatRoom chatRoom){
        em.persist(chatRoom);
        return chatRoom;
    }

    public ChatRoom findOne(Long chatRoomId){
        return em.find(ChatRoom.class, chatRoomId);
    }

    public List<ChatRoom> findOneByMember(Long memberId){
        return em.createQuery("select cr from ChatRoom cr where cr.one.id = :memberId", ChatRoom.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public ChatRoom findOneByOneOrOther(Long oneId, Long otherId){
        return em.createQuery("select cr from ChatRoom cr where (cr.one.id =: oneId and cr.other.id = :otherId) or(cr.one.id =:otherId and cr.other.id = :oneId)", ChatRoom.class)
                .setParameter("oneId", oneId)
                .setParameter("otherId", otherId)
                .getSingleResult();
    }

    public List<ChatRoom> findAll(){
        return em.createQuery("select cr from ChatRoom cr", ChatRoom.class)
                .getResultList();
    }


}
