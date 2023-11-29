package com.party.partytogether.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Getter
@Setter
public class GuildWarRoom {

    @Id
    @GeneratedValue
    @Column(name = "guildWarRoom_id")
    private Long Id;

    private Integer roomNumber;

    @OneToOne
    @JoinColumn(name = "guild_id")
    private Guild guild;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;


    //==생성 메서드==//
    public static GuildWarRoom createRoom(Guild guild, Member member){
        AtomicInteger counter = new AtomicInteger(1);

        GuildWarRoom room = new GuildWarRoom();
        room.setRoomNumber(counter.getAndIncrement());
        room.setGuild(guild);
        room.setMember(member);

        return room;
    }
}
