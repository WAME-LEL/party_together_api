package com.party.partytogether.repository;


import com.party.partytogether.domain.Event;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRepository {
    private final EntityManager em;

    // 이벤트 저장
    public void save(Event event) {
        em.persist(event);
    }

    // 이벤트 하나 조회
    public Event findOne(Long eventId){
        return em.find(Event.class, eventId);
    }

    // 이벤트 전체 조회
    public List<Event> findAll() {
        return em.createQuery("select e from Event e", Event.class)
                .getResultList();
    }
}
