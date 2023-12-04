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

    public void save(String name, String url, String period){
        Event event = Event.createEvent(name, url, period);

        eventRepository.save(event);
    }

    public Event findOne(Long eventId){
        return eventRepository.findOne(eventId);
    }

    public List<Event> findAll(){
        return eventRepository.findAll();
    }


}
