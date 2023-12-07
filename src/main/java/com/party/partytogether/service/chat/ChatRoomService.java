package com.party.partytogether.service.chat;


import com.party.partytogether.domain.chat.ChatRoom;
import com.party.partytogether.domain.member.Member;
import com.party.partytogether.repository.chat.ChatRoomRepository;
import com.party.partytogether.repository.member.MemberRepository;
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

    // 채팅방 생성
    public Long createChatRoom(String name, Long oneId, Long otherId){
        Member one = memberRepository.findOne(oneId);
        Member other = memberRepository.findOne(otherId);

        ChatRoom chatRoom = ChatRoom.createChatRoom(name, one, other);
        ChatRoom save = chatRoomRepository.save(chatRoom);
        return save.getId();
    }

    // 채팅방 하나 조회
    public ChatRoom findOne(Long roomId){
        return chatRoomRepository.findOne(roomId);
    }

    // 회원ID로 채팅방 조회
    public List<ChatRoom> findOneByMemberId(Long memberId){
        return chatRoomRepository.findOneByMember(memberId);
    }

    // 자신의ID와 상대방ID 로 채팅방 조회
    public ChatRoom findOneByOneOrOther(Long oneId, Long otherId){
        return chatRoomRepository.findOneByOneOrOther(oneId, otherId);

    }

    // 채팅방 전체 조회
    public List<ChatRoom> findAll(){
        return chatRoomRepository.findAll();
    }
}
