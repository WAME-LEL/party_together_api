package com.party.partytogether.api;


import com.party.partytogether.controller.GuildController;
import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.Guild;
import com.party.partytogether.domain.Member;
import com.party.partytogether.service.GuildService;
import jakarta.persistence.Tuple;
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
public class GuildApiController {
    private final GuildService guildService;

    @GetMapping("/api/guild")
    public Result guildInfo(@RequestParam Long guildId){
        Guild guild = guildService.findOneJoinLeaderAndGame(guildId);
        GuildDto guildDto = new GuildDto(guild);
        return new Result(guildDto);
    }

    @GetMapping("/api/guilds")
    public Result guildList(){
        List<Guild> guildList = guildService.findAll();
        List<GuildListDto> collect = guildList.stream()
                .map(g -> new GuildListDto(g.getName(), g.getGame(), g.getMember().size()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/guild/members")
    public Result memberList(@RequestParam Long guildId){
        List<Member> guildMembers = guildService.findAllMembers(guildId);


        return new Result(guildMembers);
    }

    @PostMapping("/api/guild/registration")
    public ResponseEntity<?> guildRegistration(@RequestBody GuildRegistrationRequest request){

        guildService.guildRegistration(request.guildName, request.guildIntroduce, request.guildGame, request.guildLeader);

        return ResponseEntity.ok("guild Registration successfully");
    }

    @Data
    static class GuildRegistrationRequest{
        private String guildName;
        private String guildIntroduce;
        private Long guildGame;
        private Long guildLeader;
    }

    @Data
    static class MemberListRequestDto{
        private Long guildId;
    }

    @Data
    static class GuildListRequestDto{
        private Long guildId;
    }

    @Data
    @AllArgsConstructor
    static class GuildDto{
        private Guild guild;
    }


    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class GuildListDto{
        private String guildName;
        private Game game;
        private int memberCount;
    }

//    @Data
//    @AllArgsConstructor
//    static class GuildDto{
//        private Long id;
//        private String name;
//        private String introduce;
//        private int point;
//        private Game game;
//        private List<Member> member;
//    }

}

