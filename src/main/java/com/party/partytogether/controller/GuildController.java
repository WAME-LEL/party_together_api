package com.party.partytogether.controller;


import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.guild.Guild;
import com.party.partytogether.service.guild.GuildService;
import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class GuildController {
    private final GuildService guildService;

    // 길드 리스트 조회
    @GetMapping("/guild")
    public String guildList(Model model){
        List<Tuple> guildList = guildService.findAllJoinLeaderAndGame();

        // 길드 리스트를 DTO로 변환
        List<GuildGameDto> collect = guildList.stream()
                .map(d -> new GuildGameDto(d.get(0, Guild.class), d.get(1, Game.class)))
                .collect(Collectors.toList());


        model.addAttribute("guildList", collect);

        return "guild/guildList";
    }

    //길드 가입 폼으로 이동
    @GetMapping("/guild/joinForm")
    public String guildJoinForm(){

        return "/guild/guildJoin";
    }

    // 길드 등록 폼으로 이동
    @GetMapping("/guild/add")
    public String guildForm(){
        return "/guild/addGuild";
    }


    //길드 삭제
    @PostMapping("/guild/{guildId}/delete")
    public String guildDelete(@PathVariable ("guildId") Long guildId){
        guildService.delete(guildId);

        return "redirect:/guild";

    }

    // 길드 등록
    @PostMapping("/guild/regist")
    public String guildRegistration(
            @RequestParam("guildName") String guildName,
            @RequestParam("guildIntroduce") String guildIntroduce ,
            @RequestParam("guildGame") Long guildGame,
            @RequestParam("guildLeader") Long guildLeader){

        guildService.guildRegistration(guildName, guildIntroduce, guildGame, guildLeader);

        return "redirect:/guild";
    }

    //==DTO==//

    @Data
    @AllArgsConstructor
    static class GuildGameDto{
        private Guild guild;
        private Game game;
    }


}
