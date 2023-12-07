package com.party.partytogether.repository.chat;


import com.party.partytogether.domain.chat.ChatMessage;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {
    private final EntityManager em;

    // 채팅 메시지 저장
    public void save(ChatMessage chatMessage){
        em.persist(chatMessage);
    }

    // 채팅 메시지 삭제
    public ChatMessage findOne(Long chatMessageId){
        return em.find(ChatMessage.class, chatMessageId);
    }

    //  채팅방에 속한 모든 메시지 조회
    public List<ChatMessage> findAll(){
        return em.createQuery("select cm from ChatMessage cm", ChatMessage.class)
                .getResultList();
    }

    // 채팅방에 속한 메시지 조회
    public List<ChatMessage> findAllByRoomId(Long roomId){
        return em.createQuery("select cm from ChatMessage cm where cm.chatRoom.id =:roomId order by cm.timestamp asc", ChatMessage.class)
                .setParameter("roomId", roomId)
                .getResultList();
    }

}
