package com.party.partytogether.service;


import com.party.partytogether.repository.UserChatRoomRepository;
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
