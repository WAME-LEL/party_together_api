package com.party.partytogether.repository.chat;


import com.party.partytogether.domain.chat.UserChatRoom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserChatRoomRepository {
    private final EntityManager em;

    public void save(UserChatRoom userChatRoom){
        em.persist(userChatRoom);
    }

    public UserChatRoom findOne(Long userChatRoomId){
        return em.find(UserChatRoom.class, userChatRoomId);
    }

    public List<UserChatRoom> findAll(){
        return em.createQuery("select ucr from UserChatRoom ucr", UserChatRoom.class)
                .getResultList();
    }
}
