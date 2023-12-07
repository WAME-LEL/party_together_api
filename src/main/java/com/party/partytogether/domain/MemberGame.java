package com.party.partytogether.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


//회원이 하고 있는 게임 도메인
@Entity
@Getter
@Setter
public class MemberGame {
    @Id
    @GeneratedValue
    @Column(name = "member_game_id")
    @JsonBackReference
    private Long id;

    //다대일 관계
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 회원

    //다대일 관계
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;  // 게임

    //==생성 메서드==//
    public static MemberGame creatMemberGame(Member member, Game game){
        MemberGame memberGame = new MemberGame();
        memberGame.setGame(game);
        memberGame.setMember(member);

        return memberGame;
    }
}
