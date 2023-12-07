package com.party.partytogether.repository.guild;


import com.party.partytogether.domain.guild.Guild;
import com.party.partytogether.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GuildRepository {
    private final EntityManager em;

    //길드 저장
    public void save(Guild guild){
        em.persist(guild);
    }

    //길드 삭제
    public void delete(Long id){
        em.remove(em.find(Guild.class, id));
    }

    //길드 하나 조회
    public Guild findOne(Long id){
        return em.find(Guild.class, id);
    }

    //게임 종류를 포함한 길드 하나 조회
    public Guild findOneJoinLeaderAndGame(Long guildId){
        return em.createQuery("select g from Guild g where g.id = :guildId", Guild.class)
                .setParameter("guildId", guildId)
                .getSingleResult();
    }

    //오름차순으로 길드 전체 조회
    public List<Guild> findAllDESC(){
        return em.createQuery("select g from Guild g order by g.point desc", Guild.class)
                .getResultList();
    }

    //게임 종류를 포함한 길드 전체 조회
    public List<Tuple> findAllJoinLeaderAndGame(){
        return em.createQuery("select g, ga from Guild g inner join Game ga on g.game = ga", Tuple.class)
                .getResultList();
    }

    //길드 멤버 조회
    public List<Member> findAllGuildMembers(Long guildId){
        return em.createQuery("select g.member from Guild g where g.id = :guildId", Member.class)
                .setParameter("guildId", guildId)
                .getResultList();
    }

}
