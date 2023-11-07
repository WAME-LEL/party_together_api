package com.party.partytogether.controller;

import com.party.partytogether.domain.Game;
import com.party.partytogether.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping("/game/regist")
    public String gameRegist(@RequestParam ("gameTitle") String gameTitle){
        Game game = Game.createGame(gameTitle);

        gameService.saveGame(game);

        return "redirect:/";
    }

}
