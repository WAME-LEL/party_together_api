package com.party.partytogether.service;


import com.party.partytogether.domain.Event;
import com.party.partytogether.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    private final EventRepository eventRepository;

    // 이벤트 저장
    public void save(String name, String url, String period){
        Event event = Event.createEvent(name, url, period);

        eventRepository.save(event);
    }

    // 이벤트 하나 조회
    public Event findOne(Long eventId){
        return eventRepository.findOne(eventId);
    }

    // 이벤트 전체 조회
    public List<Event> findAll(){
        return eventRepository.findAll();
    }


}
