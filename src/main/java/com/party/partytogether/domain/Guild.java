package com.party.partytogether.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


//길드 도메인
@Entity
@Getter @Setter
public class Guild {
    @Id
    @GeneratedValue
    @Column(name = "guild_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String introduce;

    private int point;
    private int ranking;

    //다대일 관계
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    //일대일 관계
    @OneToOne
    @JoinColumn(name = "leader_id")
    private Member leader;

    //일대다 관계
    @OneToMany(mappedBy = "guild")
    private List<Member> member = new ArrayList<>();

    //== 생성 메서드==//
    public static Guild createGuild(String name, String introduce, Game game, Member member){
        Guild guild = new Guild();
        guild.setName(name);
        guild.setIntroduce(introduce);
        guild.setPoint(0);
        guild.setGame(game);
        guild.setLeader(member);
        return guild;
    }



}
