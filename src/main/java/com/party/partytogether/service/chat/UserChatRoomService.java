package com.party.partytogether.service.chat;


import com.party.partytogether.repository.chat.UserChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserChatRoomService {
    private final UserChatRoomRepository userChatRoomRepository;

    public void entranceChatRoom(){

    }


}
