package com.party.partytogether.repository;


import com.party.partytogether.domain.GuildWarRoom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GuildWarRoomRepository {
    private final EntityManager em;

    public Integer save(GuildWarRoom room){
        em.persist(room);

        return room.getRoomNumber();
    }

    public void delete(Long roomId){
        em.remove(em.find(GuildWarRoom.class, roomId));
    }

    public GuildWarRoom findOne(Long roomId){
        return em.find(GuildWarRoom.class, roomId);
    }

    public GuildWarRoom findOneByRoomNumber(Integer roomNumber){
        return em.createQuery("select gwr from GuildWarRoom gwr where gwr.roomNumber = :roomNumber", GuildWarRoom.class)
                .setParameter("roomNumber", roomNumber)
                .getResultList().get(0);
    }

    public List<GuildWarRoom> findAll(){
        return em.createQuery("select gwr from GuildWarRoom gwr", GuildWarRoom.class)
                .getResultList();
    }

    public List<GuildWarRoom> findAllByRoomNumber(Integer roomNumber){
        return em.createQuery("select gwr from GuildWarRoom gwr where gwr.roomNumber = :roomNumber order by gwr.guild.id desc", GuildWarRoom.class)
                .setParameter("roomNumber", roomNumber)
                .getResultList();
    }




}
