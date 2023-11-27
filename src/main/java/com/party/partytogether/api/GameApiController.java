package com.party.partytogether.api;

import com.party.partytogether.domain.Game;
import com.party.partytogether.service.GameService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class GameApiController {
    private final GameService gameService;

    //게임 전체 조회
    @GetMapping("/api/game")
    public Result gameList(){
        List<Game> gameList = gameService.findAll();

        //게임 리스트를 DTO로 변환
        List<GameDto> collect = gameList.stream()
                .map(g -> new GameDto(g.getId(), g.getTitle()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    //==DTO==//

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
