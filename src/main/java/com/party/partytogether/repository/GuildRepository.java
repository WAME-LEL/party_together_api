package com.party.partytogether.repository;


import com.party.partytogether.domain.Guild;
import com.party.partytogether.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GuildRepository {
    private final EntityManager em;

    public void save(Guild guild){
        em.persist(guild);
    }

    public void delete(Long id){
        em.remove(em.find(Guild.class, id));
    }

    public List<Guild> findAllDESC(){
        return em.createQuery("select g from Guild g order by g.point desc", Guild.class)
                .getResultList();
    }

    public List<Tuple> findAllJoinLeaderAndGame(){
        return em.createQuery("select g, ga from Guild g inner join Game ga on g.game = ga", Tuple.class)
                .getResultList();
    }

    public Guild findOne(Long id){
        return em.find(Guild.class, id);
    }


    public Guild findOneJoinLeaderAndGame(Long guildId){
//        return em.createQuery("select g, ga from Guild g inner join Game ga on g.game = ga where g.id = :guildId", Tuple.class)
//                .setParameter("guildId", guildId)
//                .getSingleResult();

        return em.createQuery("select g from Guild g where g.id = :guildId", Guild.class)
                .setParameter("guildId", guildId)
                .getSingleResult();
    }

    public List<Member> findAllMembers(Long guildId){
        return em.createQuery("select g.member from Guild g where g.id = :guildId", Member.class)
                .setParameter("guildId", guildId)
                .getResultList();
    }

}
