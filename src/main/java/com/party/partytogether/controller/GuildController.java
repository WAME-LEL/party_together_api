package com.party.partytogether.controller;


import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.Guild;
import com.party.partytogether.service.GuildService;
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

    @GetMapping("/guild")
    public String guildList(Model model){
//        List<Guild> guildList = guildService.findAll();
        List<Tuple> guildList = guildService.findAllJoinLeaderAndGame();

//        for (Tuple result : guildList) {
//            Guild guild = result.get(0, Guild.class);
//            Member member = result.get(1, Member.class);
//            Game game = result.get(2, Game.class);
//
//            System.out.println("guild.getName() = " + guild.getName());
//            System.out.println("member.getNickname() = " + member.getNickname());
//            System.out.println("game.getTitle() = " + game.getTitle());
//            // 여기서 guild와 member를 사용하여 필요한 작업 수행
//        }
        List<GuildGameDto> collect = guildList.stream()
                .map(d -> new GuildGameDto(d.get(0, Guild.class), d.get(1, Game.class)))
                .collect(Collectors.toList());


        model.addAttribute("guildList", collect);

        return "guild/guildList";
    }
    @GetMapping("/guild/joinForm")
    public String guildJoinForm(){

        return "/guild/guildJoin";
    }



    @GetMapping("/guild/add")
    public String guildForm(){
        return "/guild/addGuild";
    }

    @Data
    @AllArgsConstructor
    static class GuildGameDto{
        private Guild guild;
        private Game game;
    }


    @PostMapping("/guild/{guildId}/delete")
    public String guildDelete(@PathVariable ("guildId") Long guildId){
        guildService.delete(guildId);

        return "redirect:/guild";

    }

    @PostMapping("/guild/regist")
    public String guildRegistration(
            @RequestParam("guildName") String guildName,
            @RequestParam("guildIntroduce") String guildIntroduce ,
            @RequestParam("guildGame") Long guildGame,
            @RequestParam("guildLeader") Long guildLeader){

        guildService.guildRegistration(guildName, guildIntroduce, guildGame, guildLeader);

        return "redirect:/guild";
    }



}
