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

//    @OneToMany(mappedBy = "member")
//    private List<Game> gameList;


}
