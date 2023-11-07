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

    public void saveGame(Game game){
        gameRepository.save(game);
    }

    public List<Game> findAll(){
        return gameRepository.findAll();
    }


}
