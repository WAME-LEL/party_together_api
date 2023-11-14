package com.party.partytogether.repository;


import com.party.partytogether.domain.Guild;
import jakarta.persistence.EntityManager;
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
    public Guild findOne(Long id){
        return em.find(Guild.class, id);
    }

    public List<Guild> findAll(){
        return em.createQuery("select g from Guild g", Guild.class)
                .getResultList();
    }
}
