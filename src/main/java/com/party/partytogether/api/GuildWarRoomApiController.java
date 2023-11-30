package com.party.partytogether.api;


import com.party.partytogether.domain.GuildWarRoom;
import com.party.partytogether.service.GuildWarRoomService;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GuildWarRoomApiController {
    private final GuildWarRoomService guildWarRoomService;

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
        GuildWarRoom room = guildWarRoomService.findOneByRoomNumber(request.roomNumber);
        List<GuildWarRoom> roomMemberList = guildWarRoomService.findAllByRoomNumber(request.roomNumber);
        List<RoomInfoResponse> collect = roomMemberList
                .stream()
                .map(r -> new RoomInfoResponse(r.getGuild().getId(), r.getGuild().getName(), r.getMember().getNickname()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new Result(collect));

    }



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


}
