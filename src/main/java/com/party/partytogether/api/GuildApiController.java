package com.party.partytogether.api;


import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.Guild;
import com.party.partytogether.domain.Member;
import com.party.partytogether.service.GuildService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GuildApiController {
    private final GuildService guildService;

    @GetMapping("/api/guild")
    public Result guildList(){
        List<Guild> guildList = guildService.findAll();
        List<GuildDto> collect = guildList.stream()
                .map(g -> new GuildDto(g.getId(), g.getName(), g.getIntroduce(), g.getPoint(), g.getGame(), g.getMember()))
                .collect(Collectors.toList());

        return new Result(collect);
    }


    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class GuildDto{
        private Long id;
        private String name;
        private String introduce;
        private int point;
        private Game game;
        private List<Member> member;
    }

}

