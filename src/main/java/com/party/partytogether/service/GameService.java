package com.party.partytogether.service;

import com.party.partytogether.domain.Game;
import com.party.partytogether.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;

    //게임 등록
    @Transactional
    public void gameRegistration(Game game){
        gameRepository.save(game);
    }

    //게임 삭제
    @Transactional
    public void delete(Long gameId){
        gameRepository.delete(gameId);
    }

    //게임 하나 조회
    public Game findOne(Long gameId){
        return gameRepository.findOne(gameId);
    }

    //게임 전체 조회
    public List<Game> findAll(){
        return gameRepository.findAll();
    }


}
