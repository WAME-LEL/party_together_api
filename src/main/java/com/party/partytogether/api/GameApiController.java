package com.party.partytogether.api;

import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.Member;
import com.party.partytogether.service.GameService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class GameApiController {
    private final GameService gameService;

    @GetMapping("/api/game")
    public Result gameList(){
        List<Game> gameList = gameService.findAll();

        List<GameDto> collect = gameList.stream()
                .map(g -> new GameDto(g.getId(), g.getTitle()))
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
    static class GameDto{
        private Long id;
        private String title;

    }

}
