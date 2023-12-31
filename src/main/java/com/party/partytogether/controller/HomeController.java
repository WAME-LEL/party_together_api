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

    // 게임 등록 폼으로 이동
    @GetMapping("/")
    public String home(Model model){

        List<Game> gameList = gameService.findAll();
        model.addAttribute("gameList", gameList);

        return "game/addGames";
    }

    // 게임 등록
    @PostMapping("/game/regist")
    public String gameRegist(@RequestParam("gameTitle") String gameTitle){
        Game game = Game.createGame(gameTitle);

        gameService.gameRegistration(game);

        return "redirect:/";
    }

    // 게임 삭제
    @PostMapping("/game/{gameId}/delete")
    public String delete(@PathVariable("gameId") Long gameId){
        gameService.delete(gameId);

        return "redirect:/";
    }


}

