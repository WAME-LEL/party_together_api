package com.party.partytogether.controller;


import com.party.partytogether.domain.Game;
import com.party.partytogether.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final GameService gameService;

    @GetMapping("/")
    public String home(Model model){

        List<Game> gameList = gameService.findAll();
        model.addAttribute("gameList", gameList);

        return "addGames";
    }


}

