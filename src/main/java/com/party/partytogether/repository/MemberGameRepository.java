package com.party.partytogether.repository;

import com.party.partytogether.domain.Member;
import com.party.partytogether.domain.MemberGame;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberGameRepository {
    private final EntityManager em;


    //MemberGame 하나 조회
    public MemberGame findOne(Long memberGameId){
        return em.find(MemberGame.class, memberGameId);
    }

    //MemberGame 전체 조회
    public List<MemberGame> findAll(){
        return em.createQuery("select mg from MemberGame mg", MemberGame.class).getResultList();
    }

    //같은 게임을 하는 멤버와 게임 조회
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

    public List<MemberGame> findAllGameByMember(Long memberId){
        List<MemberGame> results = em.createQuery(
                        "select mg" +
                                " from MemberGame mg " +
                                "where mg.member.id = :memberId", MemberGame.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return results;
    }

}
