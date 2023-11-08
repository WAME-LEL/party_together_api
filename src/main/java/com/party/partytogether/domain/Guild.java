package com.party.partytogether.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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



}
