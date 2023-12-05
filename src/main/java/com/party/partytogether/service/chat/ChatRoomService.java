package com.party.partytogether.service.chat;


import com.party.partytogether.domain.chat.ChatRoom;
import com.party.partytogether.domain.Member;
import com.party.partytogether.repository.chat.ChatRoomRepository;
import com.party.partytogether.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    public Long createChatRoom(String name, Long oneId, Long otherId){
        Member one = memberRepository.findOne(oneId);
        Member other = memberRepository.findOne(otherId);

        ChatRoom chatRoom = ChatRoom.createChatRoom(name, one, other);
        ChatRoom save = chatRoomRepository.save(chatRoom);
        return save.getId();
    }

    public ChatRoom findOne(Long roomId){
        return chatRoomRepository.findOne(roomId);
    }

    public List<ChatRoom> findOneByMemberId(Long memberId){
        return chatRoomRepository.findOneByMember(memberId);
    }

    public ChatRoom findOneByOneOrOther(Long oneId, Long otherId){
        return chatRoomRepository.findOneByOneOrOther(oneId, otherId);

    }
    public List<ChatRoom> findAll(){
        return chatRoomRepository.findAll();
    }
}
