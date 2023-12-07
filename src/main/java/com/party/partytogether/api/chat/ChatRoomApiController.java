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
        String name = memberService.findOne(request.oneId).getNickname() +  " 와 " + memberService.findOne(request.otherId).getNickname() + " 의 방";
        Long roomId = chatRoomService.createChatRoom(name, request.oneId, request.otherId);
        return ResponseEntity.ok(roomId);
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
    public ResponseEntity<?> myChatRoomInfo(@ModelAttribute MyChatRoomInfoRequest request){
        List<ChatRoom> chatRoomList = chatRoomService.findOneByMemberId(request.memberId);

        List<MyChatRoomInfoResponse> collect = chatRoomList.stream()
                .map(cr -> new MyChatRoomInfoResponse(cr.getId(), cr.getName(), cr.getOne().getId(), cr.getOther().getId()))
                .collect(Collectors.toList());
        if(collect.isEmpty()){
            return ResponseEntity.ok("nothing");
        }

        return ResponseEntity.ok(new Result(collect));
    }

    @GetMapping("/api/chatRoom/info/us")
    public ResponseEntity<?> ourChatRoomInfo(@ModelAttribute OurChatInfoRequest request){
        try{
            ChatRoom chatRoom = chatRoomService.findOneByOneOrOther(request.oneId, request.otherId);
            
            return ResponseEntity.ok(chatRoom.getId());

        }catch(Exception e){
            return ResponseEntity.ok("nothing");
        }

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
        private Long oneId;
        private Long otherId;
    }

    @Data
    static class OurChatInfoRequest{
        private Long oneId;
        private Long otherId;
    }

}
