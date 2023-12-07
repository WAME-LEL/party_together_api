package com.party.partytogether.api;


import com.party.partytogether.service.NotificationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class NotificationApiController {

    private final NotificationService notificationService;

    // 알림 전송
    @PostMapping("/api/notification/send")
    public ResponseEntity<?> sendNotification(@RequestBody NotificationRequest request) {
        notificationService.sendNotification(request.getMemberId(), request.getMessage());
        return ResponseEntity.ok().build();
    }


    //==DTO==//
    @Data
    static class NotificationRequest {
        private Long memberId;
        private String message;
    }
}
