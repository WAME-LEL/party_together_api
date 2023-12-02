package com.party.partytogether.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // ChatMessage와의 양방향 관계 (선택적)
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ChatMessage> messages = new HashSet<>();

    // 생성자, 게터, 세터 등
}