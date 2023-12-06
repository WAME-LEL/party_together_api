package com.party.partytogether.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@CrossOrigin(origins = "http://localhost:8081")
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // CORS 허용
    String[] origins = {"http://localhost:8080", "http://localhost:8081"};


    // 클라이언트에서 /chat으로 접속하면 웹소켓 연결을 하고, 메시지를 구독할 수 있도록 한다.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").setAllowedOriginPatterns(origins).withSockJS();
    }

    // 메시지 브로커로 RabbitMQ를 사용하고, 클라이언트에서 메시지를 구독할 endpoint는 /topic으로 시작한다.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }


}