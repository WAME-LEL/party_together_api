package com.party.partytogether.service.chat;


import com.party.partytogether.domain.chat.ChatMessage;
import com.party.partytogether.domain.chat.ChatRoom;
import com.party.partytogether.repository.chat.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    // 채팅 메시지 저장
    public void save(Long senderId, Long receiverId, String content, LocalDateTime timestamp, ChatRoom chatRoom){
        ChatMessage chatMessage = ChatMessage.createChatMessage(senderId, receiverId, content, timestamp, chatRoom);

        chatMessageRepository.save(chatMessage);
    }

    // 채팅 메시지 조회
    public List<ChatMessage> findAllByRoomId(Long roomId){
        return chatMessageRepository.findAllByRoomId(roomId);
    }

}
