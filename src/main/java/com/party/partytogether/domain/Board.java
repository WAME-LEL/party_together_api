package com.party.partytogether.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String type;

    @Column(columnDefinition = "TEXT")
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String opentalk;

    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //==생성 메서드==//
    public static Board createBoard(String title, String content, String opentalk, String type){
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setOpentalk(opentalk);
        board.setType(type);
        board.setTime(LocalDateTime.now());

        return board;
    }

}
