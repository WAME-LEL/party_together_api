package com.party.partytogether.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Guild {
    @Id
    @GeneratedValue
    @Column(name = "guild_id")
    private Long id;

    private String name;

    private String introduce;

    private int point;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "guild", cascade = CascadeType.ALL)
    private List<Member> member;

    //== 생성 메서드==//
    public static Guild createGuild(String name, String introduce){
        Guild guild = new Guild();
        guild.setName(name);
        guild.setIntroduce(introduce);
        guild.setPoint(0);

        return guild;
    }



}
