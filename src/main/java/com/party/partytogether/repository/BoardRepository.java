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

    //게시글 저장
    public void save(Board board){
        em.persist(board);
    }

    //게시글 삭제
    public void delete(Long id){
        em.remove(em.find(Board.class, id));
    }

    //게시글 하나 조회
    public Board findOne(Long id){
        return em.find(Board.class, id);
    }

    //게시글 전체 조회
    public List<Board> findAll(){
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    //게시판 타입에 따른 게시글 검색
    public List<Board> findSearchWord(String keyword){
        return em.createQuery("select b from Board b WHERE b.type LIKE :keyword", Board.class)
                .setParameter("keyword", keyword)
                .getResultList();

    }
}
