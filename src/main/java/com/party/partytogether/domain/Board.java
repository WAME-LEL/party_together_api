package com.party.partytogether.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//게시판 도메인
@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String type;    // 게시판 종류

    @Column(columnDefinition = "TEXT")
    private String title;   // 게시판 제목
    @Column(columnDefinition = "TEXT")
    private String content; // 게시판 내용
    @Column(columnDefinition = "TEXT")
    private String opentalk;    // 오픈톡방 링크

    private LocalDateTime time;   // 게시판 작성 시간

    //다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 작성자

    //==생성 메서드==//
    public static Board createBoard(String title, String content, String opentalk, String type, Member member){
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setOpentalk(opentalk);
        board.setType(type);
        board.setTime(LocalDateTime.now());
        board.setMember(member);

        return board;
    }

}
