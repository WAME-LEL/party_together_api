package com.party.partytogether.api;


import com.party.partytogether.domain.Event;
import com.party.partytogether.service.EventService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class EventApiController {
    private final EventService eventService;

    @GetMapping("/api/event")
    public Result eventList(){
        List<Event> eventList = eventService.findAll();

        List<EventListResponse> collect = eventList.stream()
                .map(e -> new EventListResponse(e.getId(), e.getName(), e.getUrl(), e.getPeriod()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PostMapping("/api/event/add")
    public ResponseEntity<?> addEvent(@RequestBody AddEventRequest request){
        eventService.save(request.name, request.url, request.period);

        return ResponseEntity.ok("Add event successfully");
    }


    //==DTO==//

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class EventListResponse{
        private Long eventId;
        private String name;
        private String url;
        private String period;
    }

    @Data
    static class AddEventRequest{
        private String name;
        private String url;
        private String period;
    }
}
