package com.party.partytogether.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MemberGame {
    @Id
    @GeneratedValue
    @Column(name = "member_game_id")
    @JsonBackReference
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    //==생성 메서드==//
    public static MemberGame creatMemberGame(Member member, Game game){
        MemberGame memberGame = new MemberGame();
        memberGame.setGame(game);
        memberGame.setMember(member);

        return memberGame;
    }
}
