package com.party.partytogether.controller;


import com.party.partytogether.domain.Game;
import com.party.partytogether.service.GameService;
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
public class HomeController {
    private final GameService gameService;

    @GetMapping("/")
    public String home(Model model){

        List<Game> gameList = gameService.findAll();
        model.addAttribute("gameList", gameList);

        return "addGames";
    }
    @PostMapping("/game/regist")
    public String gameRegist(@RequestParam("gameTitle") String gameTitle){
        Game game = Game.createGame(gameTitle);

        gameService.gameRegist(game);

        return "redirect:/";
    }

    @PostMapping("/game/{gameId}/delete")
    public String delete(@PathVariable("gameId") Long gameId){
        gameService.delete(gameId);

        return "redirect:/";
    }


}

