package com.party.partytogether.api.chat;


import com.party.partytogether.domain.chat.ChatRoom;
import com.party.partytogether.service.MemberService;
import com.party.partytogether.service.chat.ChatRoomService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class ChatRoomApiController {
    private final ChatRoomService chatRoomService;
    private final MemberService memberService;

    @PostMapping("/api/chatRoom/create")
    public ResponseEntity<?> createChatRoom(@RequestBody CreateChatRoomRequest request){
        String name = memberService.findOne(request.oneId).getNickname() +  "와" + memberService.findOne(request.otherId).getNickname() + "의 방";
        chatRoomService.createChatRoom(name, request.oneId, request.otherId);
        return ResponseEntity.ok(name + "생성 완료");
    }

    @GetMapping("/api/chatRoom")
    public Result viewChatRoom(){
        List<ChatRoom> chatRoomList = chatRoomService.findAll();

        List<ChatRoomDto> collect = chatRoomList
                .stream()
                .map(c -> new ChatRoomDto(c.getId(), c.getName()))
                .collect(Collectors.toList());

        return new Result(new ViewChatRoomResponse(collect));

    }

    @GetMapping("/api/chatRoom/info")
    public Result myChatRoomInfo(@ModelAttribute MyChatRoomInfoRequest request){
        ChatRoom chatRoom = chatRoomService.findOneByMemberId(request.memberId);

        return new Result(new MyChatRoomInfoResponse(chatRoom.getId(), chatRoom.getName(), chatRoom.getOne().getId(), chatRoom.getOther().getId()));
    }



    //==DTO==//

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }


    @Data
    static class CreateChatRoomRequest{
        private Long oneId;
        private Long otherId;
    }


    @Data
    @AllArgsConstructor
    static class ViewChatRoomResponse{
        private List<ChatRoomDto> chatRoomList;
    }

    @Data
    @AllArgsConstructor
    static class ChatRoomDto{
        private Long id;
        private String name;
    }

    @Data
    static class MyChatRoomInfoRequest{
        private Long memberId;
    }

    @Data
    @AllArgsConstructor
    static class MyChatRoomInfoResponse{
        private Long roomId;
        private String name;
        private Long ownId;
        private Long otherId;
    }

}
