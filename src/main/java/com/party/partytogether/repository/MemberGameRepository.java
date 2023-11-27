package com.party.partytogether.repository;


import com.party.partytogether.domain.MemberGame;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MemberGameRepository {
    private final EntityManager em;

    public MemberGame findOne(Long memberGameId){
        return em.find(MemberGame.class, memberGameId);
    }

    public List<MemberGame> findAll(){
        return em.createQuery("select mg from MemberGame mg", MemberGame.class).getResultList();
    }

    public List<Tuple> findAllSameMember(Long memberId){
        List<Tuple> results = em.createQuery(
                        "SELECT mg2.member.id, ga.id" +
                                " from MemberGame mg1" +
                                " inner join MemberGame mg2 on mg1.member.id != mg2.member.id AND mg1.game.id = mg2.game.id" +
                                " inner join Game ga on ga.id = mg1.game.id " +
                                "where mg1.member.id = :memberId", Tuple.class)
                .setParameter("memberId", memberId)
                .getResultList();


        return results;
    }




}
