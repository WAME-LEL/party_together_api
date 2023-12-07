package com.party.partytogether.api.chat;


import com.party.partytogether.domain.chat.ChatMessage;
import com.party.partytogether.service.chat.ChatMessageService;
import com.party.partytogether.service.chat.ChatRoomService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class ChatMessageApiController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/api/chatMessage")
    public Result chatMessageList(@ModelAttribute ChatMessageListRequest request){
        List<ChatMessage> chatMessageList = chatMessageService.findAllByRoomId(request.roomId);

        List<ChatMessageListResponse> collect = chatMessageList.stream()
                .map(c -> new ChatMessageListResponse(c.getChatRoom().getId(), c.getSenderId(), c.getReceiverId(), c.getContent() ,c.getTimestamp()))
                .collect(Collectors.toList());

        return new Result(collect);

    }



    //== DTO ==//

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    static class ChatMessageListRequest {
        private Long roomId;
    }

    @Data
    @AllArgsConstructor
    static class ChatMessageListResponse {
        private Long roomId;
        private Long senderId;
        private Long receiverId;
        private String content;
        private LocalDateTime timestamp;
    }

}
