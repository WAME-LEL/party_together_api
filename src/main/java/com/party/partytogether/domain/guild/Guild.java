package com.party.partytogether.domain.guild;


import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.member.Member;
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
    private String name;    // 길드 이름

    @Column(columnDefinition = "TEXT")
    private String introduce;      // 길드 소개

    private int point;      // 길드 포인트
    private int ranking;        // 길드 랭킹

    //다대일 관계
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;      // 게임

    //일대일 관계
    @OneToOne
    @JoinColumn(name = "leader_id")
    private Member leader;      // 길드장

    //일대다 관계
    @OneToMany(mappedBy = "guild")
    private List<Member> member = new ArrayList<>();    // 길드원

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
