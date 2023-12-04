package com.party.partytogether.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(Long memberId, String message) {
        String destination = "/topic/member." + memberId;
        System.out.println("destination = " + destination);
        messagingTemplate.convertAndSend(destination, message);
    }
}
