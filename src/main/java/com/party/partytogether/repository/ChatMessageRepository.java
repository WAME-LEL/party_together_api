package com.party.partytogether.repository;


import com.party.partytogether.domain.ChatMessage;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {
    private final EntityManager em;


    public void save(ChatMessage chatMessage){
        em.persist(chatMessage);
    }

    public ChatMessage findOne(Long chatMessageId){
        return em.find(ChatMessage.class, chatMessageId);
    }

    public List<ChatMessage> findAll(){
        return em.createQuery("select cm from ChatMessage cm", ChatMessage.class)
                .getResultList();
    }

}
