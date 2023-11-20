package com.party.partytogether.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

    private String latitude;

    private String longitude;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberGame> memberGames = new ArrayList<>();

    @JsonBackReference
    @ManyToOne
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

    //==연관 관계 메서드==//
    public void addGame(MemberGame memberGame){
        memberGames.add(memberGame);
    }


}
