package com.party.partytogether.repository;

import com.party.partytogether.domain.Game;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GameRepository {

    private final EntityManager em;

    //게임 저장
    public void save(Game game){
        em.persist(game);
    }

    //게임 삭제
    public void delete(Long gameId){
        Game game = findOne(gameId);
        em.remove(game);
    }

    //게임 하나 조회
    public Game findOne(Long gameId){
        return em.find(Game.class, gameId);
    }

    //게임 전체 조회
    public List<Game> findAll(){
        return em.createQuery("select g from Game g", Game.class)
                .getResultList();
    }


}
