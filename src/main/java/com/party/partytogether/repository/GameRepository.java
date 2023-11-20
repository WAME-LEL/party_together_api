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

    public void save(Game game){
        em.persist(game);
    }


    public void delete(Long gameId){
        Game game = findOne(gameId);
        em.remove(game);
    }

    public Game findOne(Long gameId){
        return em.find(Game.class, gameId);
    }

    public List<Game> findAll(){
        return em.createQuery("select g from Game g", Game.class)
                .getResultList();
    }


}
