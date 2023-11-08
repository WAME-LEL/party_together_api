package com.party.partytogether.service;

import com.party.partytogether.domain.Game;
import com.party.partytogether.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GameService {

    private final GameRepository gameRepository;

    public void gameRegist(Game game){
        gameRepository.save(game);
    }

    public Game findOne(Long gameId){
        Game game = gameRepository.findOne(gameId);
        return game;
    }

    public void delete(Long gameId){
        gameRepository.delete(gameId);
    }

    public List<Game> findAll(){
        return gameRepository.findAll();
    }


}
