package com.party.partytogether.api;


import com.party.partytogether.domain.GuildWarRoom;
import com.party.partytogether.service.GuildWarRoomService;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GuildWarRoomApiController {
    private final GuildWarRoomService guildWarRoomService;

    @PostMapping("/api/guildWar/add")
    public ResponseEntity<?> AddGuildWarRoom(@ModelAttribute AddGuildWarRoomRequest request){
        Integer roomNumber = guildWarRoomService.save(request.guildId, request.memberId);

        return ResponseEntity.ok(new Result(new AddGuildWarRoomResponse(roomNumber)));
    }

    @GetMapping("/api/guildWar")
    public ResponseEntity<?> entranceRoom(@ModelAttribute EntranceRoomRequest request){
        try{
            GuildWarRoom room = guildWarRoomService.findOneByRoomNumber(request.roomNumber);
        }catch(NoResultException exception){
            return ResponseEntity.ok("failed");
        }
        return ResponseEntity.ok("success");


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
    }


}
