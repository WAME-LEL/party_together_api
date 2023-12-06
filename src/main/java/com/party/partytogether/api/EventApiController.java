package com.party.partytogether.api;


import com.party.partytogether.domain.Event;
import com.party.partytogether.service.EventService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class EventApiController {
    private final EventService eventService;

    // 이벤트 리스트 조회
    @GetMapping("/api/event")
    public Result eventList(){
        List<Event> eventList = eventService.findAll();

        List<EventListResponse> collect = eventList.stream()
                .map(e -> new EventListResponse(e.getId(), e.getName(), e.getUrl(), e.getPeriod()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    // 이벤트 저장
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
