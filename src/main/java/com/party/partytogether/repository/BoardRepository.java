package com.party.partytogether.repository;


import com.party.partytogether.domain.Board;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    public void save(Board board){
        em.persist(board);
    }

    public void delete(Long id){
        em.remove(em.find(Board.class, id));
    }

    public Board findOne(Long id){
        return em.find(Board.class, id);
    }

    public List<Board> findAll(){
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    public List<Board> findSearchWord(String keyword){
        return em.createQuery("select b from Board b WHERE b.type LIKE :keyword", Board.class)
                .setParameter("keyword", keyword)
                .getResultList();

    }
}
