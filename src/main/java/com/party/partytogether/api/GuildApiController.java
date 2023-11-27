package com.party.partytogether.api;


import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.Guild;
import com.party.partytogether.domain.Member;
import com.party.partytogether.service.GuildService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class GuildApiController {
    private final GuildService guildService;

    @GetMapping("/api/guild")
    public Result guildInfo(@ModelAttribute GuildInfoRequest request){
        List<Guild> guildList = guildService.findAllDESC();
        updateSequence(guildList);

        Guild guild = guildService.findOneJoinLeaderAndGame(request.guildId);
        GuildDto guildDto = new GuildDto(guild);
        return new Result(guildDto);
    }



    @GetMapping("/api/guilds")
    public Result guildList(){
        List<Guild> guildList = guildService.findAllDESC();
        AtomicInteger rankCounter = new AtomicInteger(1);

        List<GuildListDto> collect = guildList.stream()
                .map(g ->
                {GuildListDto dto = new GuildListDto(g.getId() ,g.getName(), g.getPoint() ,rankCounter.get(), g.getGame(), g.getMember().size());
                    guildService.updateGuildRanking(g.getId(), rankCounter.getAndIncrement());
                    return dto;
                })
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/guild/members")
    public Result memberList(@ModelAttribute GuildListRequest request){
        List<Member> guildMembers = guildService.findAllMembers(request.guildId);


        return new Result(guildMembers);
    }

    @PostMapping("/api/guild/registration")
    public ResponseEntity<?> guildRegistration(@RequestBody GuildRegistrationRequest request){

        guildService.guildRegistration(request.guildName, request.guildIntroduce, request.guildGame, request.guildLeader);

        return ResponseEntity.ok("guild Registration successfully");
    }

    @PostMapping("/api/guild/point/add")
    public ResponseEntity<?> addPoint(@RequestBody AddGuildPointRequest request){
        guildService.addPoint(request.guildId, request.point);

        List<Guild> guildList = guildService.findAllDESC();
        updateSequence(guildList);


        return ResponseEntity.ok(request.point + "point add");
    }

    @Data
    static class AddGuildPointRequest{
        private Long guildId;
        private int point;
    }


    @Data
    static class GuildRegistrationRequest{
        private String guildName;
        private String guildIntroduce;
        private Long guildGame;
        private Long guildLeader;
    }

    @Data
    static class MemberListRequest {
        private Long guildId;
    }

    @Data
    static class GuildListRequest {
        private Long guildId;
    }

    @Data
    static class GuildInfoRequest{
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
        private Long guildId;
        private String guildName;
        private int point;
        private int guildRanking;
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

    private void updateSequence(List<Guild> guildList) {
        AtomicInteger rankCounter = new AtomicInteger(1);
        guildList.forEach(g -> {
            guildService.updateGuildRanking(g.getId(), rankCounter.getAndIncrement());
        });
    }

}

