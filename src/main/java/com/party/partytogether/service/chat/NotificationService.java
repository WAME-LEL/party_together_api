package com.party.partytogether.service.chat;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    // 특정 사용자에게 알림 보내기
    public void sendNotification(Long memberId, String message) {
        String destination = "/topic/member." + memberId;
        System.out.println("destination = " + destination);
        messagingTemplate.convertAndSend(destination, message);
    }
}
