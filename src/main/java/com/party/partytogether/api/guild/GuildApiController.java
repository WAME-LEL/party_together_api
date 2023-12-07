package com.party.partytogether.api.guild;


import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.guild.Guild;
import com.party.partytogether.domain.member.Member;
import com.party.partytogether.service.guild.GuildService;
import com.party.partytogether.service.member.MemberService;
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
    private final MemberService memberService;

    // 길드 정보 조회
    @GetMapping("/api/guild")
    public Result guildInfo(@ModelAttribute GuildInfoRequest request){
        List<Guild> guildList = guildService.findAllDESC();
        updateRanking(guildList);
        Member member = memberService.findOne(request.memberId);

        Guild guild = guildService.findOneJoinLeaderAndGame(member.getGuild().getId());
        GuildDto guildDto = new GuildDto(guild);
        return new Result(guildDto);
    }

    // 길드 리스트 조회
    @GetMapping("/api/guilds")
    public Result guildList(){
        List<Guild> guildList = guildService.findAllDESC();
        AtomicInteger rankCounter = new AtomicInteger(1);       // 길드 랭킹을 위한 카운터

        // 길드 리스트를 DTO로 변환
        List<GuildListDto> collect = guildList.stream()
                .map(g ->
                {GuildListDto dto = new GuildListDto(g.getId() ,g.getName(), g.getOpentalk(), g.getPoint() ,rankCounter.get(), g.getGame(), g.getMember().size());
                    guildService.updateGuildRanking(g.getId(), rankCounter.getAndIncrement());
                    return dto;
                })
                .collect(Collectors.toList());

        return new Result(collect);
    }

    // 길드 멤버 리스트 조회
    @GetMapping("/api/guild/members")
    public Result memberList(@ModelAttribute GuildListRequest request){
        Long guildId = memberService.findOne(request.memberId).getGuild().getId();
        List<Member> guildMembers = guildService.findAllMembers(guildId);


        return new Result(guildMembers);
    }

    // 길드 등록
    @PostMapping("/api/guild/registration")
    public ResponseEntity<?> guildRegistration(@RequestBody GuildRegistrationRequest request){

        guildService.guildRegistration(request.guildName, request.guildIntroduce, request. opentalk, request.guildGame, request.guildLeader);

        return ResponseEntity.ok("guild Registration successfully");
    }

    // 길드 포인트 추가
    @PostMapping("/api/guild/point/add")
    public ResponseEntity<?> addPoint(@RequestBody AddGuildPointRequest request){
        guildService.addPoint(request.guildId, request.point);

        List<Guild> guildList = guildService.findAllDESC();
        updateRanking(guildList);

        return ResponseEntity.ok(request.point + "point add");
    }

    //==DTO==//

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
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
        private String opentalk;
    }

    @Data
    static class MemberListRequest {
        private Long guildId;
    }

    @Data
    static class GuildListRequest {
        private Long memberId;
    }

    @Data
    static class GuildInfoRequest{
        private Long memberId;
    }

    @Data
    @AllArgsConstructor
    static class GuildDto{
        private Guild guild;
    }

    @Data
    @AllArgsConstructor
    static class GuildListDto{
        private Long guildId;
        private String guildName;
        private String opentalk;
        private int point;
        private int guildRanking;
        private Game game;
        private int memberCount;

    }

    //길드 랭킹 업데이트 메서드
    private void updateRanking(List<Guild> guildList) {
        AtomicInteger rankCounter = new AtomicInteger(1);
        guildList.forEach(g -> {
            guildService.updateGuildRanking(g.getId(), rankCounter.getAndIncrement());
        });
    }

}

