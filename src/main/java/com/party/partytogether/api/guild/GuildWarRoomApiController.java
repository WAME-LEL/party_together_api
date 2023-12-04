package com.party.partytogether.api.guild;


import com.party.partytogether.domain.guild.GuildWarRoom;
import com.party.partytogether.service.guild.GuildWarRoomService;
import com.party.partytogether.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class GuildWarRoomApiController {
    private final GuildWarRoomService guildWarRoomService;
    private final MemberService memberService;

    @PostMapping("/api/guildWar/add")
    public ResponseEntity<?> AddGuildWarRoom(@RequestBody AddGuildWarRoomRequest request){
        System.out.println("request = " + request);
        Integer roomNumber = guildWarRoomService.save(request.guildId, request.memberId);

        return ResponseEntity.ok(new Result(new AddGuildWarRoomResponse(roomNumber)));
    }

    @PostMapping("/api/guildWar")
    public ResponseEntity<?> entranceRoom(@RequestBody EntranceRoomRequest request){
        try{
            GuildWarRoom room = guildWarRoomService.findOneByRoomNumber(request.roomNumber);
            guildWarRoomService.joinRoom(request.roomNumber, request.memberId);

        }catch(Exception exception){
            return ResponseEntity.ok("failed");
        }
        return ResponseEntity.ok("success");

    }

    @GetMapping("/api/guildWar")
    public ResponseEntity<?> roomInfo(@ModelAttribute RoomInfoRequest request){
        try{
            GuildWarRoom room = guildWarRoomService.findOneByRoomNumber(request.roomNumber);
            List<GuildWarRoom> roomMemberList = guildWarRoomService.findAllByRoomNumber(request.roomNumber);
            Map<Long, List<RoomInfoResponse>> collect = roomMemberList
                    .stream()
                    .map(r -> new RoomInfoResponse(r.getGuild().getId(), r.getGuild().getName(), r.getMember().getNickname()))
                    .collect(Collectors.groupingBy(RoomInfoResponse::getGuildId));

            Iterator<Long> iterator = collect.keySet().iterator();

            List<RoomInfoResponse> FirstRoomInfoResponse = collect.getOrDefault(iterator.next(), Collections.emptyList());
            //같은 길드전 방에 2개의 길드가 있다면 실행
            if(iterator.hasNext()){
                List<RoomInfoResponse> SecondRoomInfoResponse = collect.getOrDefault(iterator.next(), Collections.emptyList());
                return ResponseEntity.ok(new Result(new TeamDivide(FirstRoomInfoResponse, SecondRoomInfoResponse)));
            }

            return ResponseEntity.ok(new Result(new TeamDivide(FirstRoomInfoResponse, Collections.emptyList())));


        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            return ResponseEntity.ok("failed");
        }

    }

    @PostMapping("/api/guildWar/exit")
    public ResponseEntity<?> roomExit(@RequestBody RoomExitRequest request){
        guildWarRoomService.roomExit(request.memberId);

        return ResponseEntity.ok("exit");
    }

//    @PostMapping("/api/guildWar/delete")
//    public ResponseEntity<?> roomDelete(@RequestBody RoomDeleteRequest request){
//
//    }


    //==DTO==//

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    static class AddGuildWarRoomRequest{
        private Long guildId;
        private Long memberId;
    }

    @Data
    @AllArgsConstructor
    static class AddGuildWarRoomResponse{
        private Integer roomNumber;
    }

    @Data
    static class EntranceRoomRequest{
        private Integer roomNumber;
        private Long memberId;
    }

    @Data
    static class RoomInfoRequest{
        private Integer roomNumber;
    }

    @Data
    @AllArgsConstructor
    static class RoomInfoResponse{
        private Long guildId;
        private String guildName;
        private String memberName;
    }

    @Data
    @AllArgsConstructor
    static class TeamDivide{
        private List<RoomInfoResponse> first;
        private List<RoomInfoResponse> second;
    }

    @Data
    static class RoomExitRequest{
        private Long memberId;
    }

//    @Data
//    static class RoomDeleteRequest{
//        private Long memberId;
//    }

}
