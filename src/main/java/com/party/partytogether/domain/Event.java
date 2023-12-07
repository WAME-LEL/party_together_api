package com.party.partytogether.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue
    @Column(name = "event_id")
    private Long id;

    private String name;    // 이벤트 이름
    private String url;    // 이벤트 링크
    private String period;  // 이벤트 기간


    //==생성 메서드==//
    public static Event createEvent(String name, String url, String period){
        Event event = new Event();
        event.setName(name);
        event.setUrl(url);
        event.setPeriod(period);

        return event;
    }
}
