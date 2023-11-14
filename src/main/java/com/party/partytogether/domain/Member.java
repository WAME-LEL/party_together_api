package com.party.partytogether.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String username;

    private String password;


    @OneToMany
    private List<Game> gameList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guild_id")
    private Guild guild;

    //== 생성 메서드 ==//
    public static Member createMember(String nickname, String username, String password){
        Member member = new Member();
        member.setNickname(nickname);
        member.setUsername(username);
        member.setPassword(password);

        return member;
    }


}
