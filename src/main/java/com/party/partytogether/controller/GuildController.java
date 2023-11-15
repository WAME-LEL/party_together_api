package com.party.partytogether.controller;


import com.party.partytogether.domain.Guild;
import com.party.partytogether.service.GuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GuildController {
    private final GuildService guildService;

    @GetMapping("/guild")
    public String guildList(Model model){
        List<Guild> guildList = guildService.findAll();

        model.addAttribute("guildList", guildList);

        return "guildList";
    }

    @GetMapping("/guild/add")
    public String guildForm(){
        return "addGuild";
    }


    @PostMapping("/guild/{guildId}/delete")
    public String guildDelete(@PathVariable ("guildId") Long guildId){
        guildService.delete(guildId);

        return "redirect:/guild";

    }

    @PostMapping("/guild/regist")
    public String guildRegist(@RequestParam("guildName") String guildName, @RequestParam("guildIntroduce") String guildIntroduce){

        guildService.guildRegist(guildName, guildIntroduce, 1L, 1L);

        return "redirect:/guild";
    }



}
