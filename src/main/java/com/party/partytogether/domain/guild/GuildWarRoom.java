package com.party.partytogether.domain.guild;


import com.party.partytogether.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GuildWarRoom {

    @Id
    @GeneratedValue
    @Column(name = "guildWarRoom_id")
    private Long Id;

    private Integer roomNumber;     // 방 번호

    @ManyToOne
    @JoinColumn(name = "guild_id")
    private Guild guild;        //  길드

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;  // 길드원


    //==생성 메서드==//
    public static GuildWarRoom createRoom(Guild guild, Member member, Integer roomNumber){
        GuildWarRoom room = new GuildWarRoom();
        room.setRoomNumber(roomNumber);
        room.setGuild(guild);
        room.setMember(member);

        return room;
    }

    //==연관 관계 메서드==//
    public static GuildWarRoom joinRoom(Guild guild, Member member, Integer roomNumber){
        GuildWarRoom room = new GuildWarRoom();
        room.setRoomNumber(roomNumber);
        room.setGuild(guild);
        room.setMember(member);

        return room;
    }
}
