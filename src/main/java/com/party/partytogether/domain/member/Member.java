package com.party.partytogether.domain.member;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.party.partytogether.domain.guild.Guild;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//회원 도메인
@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String nickname;    // 닉네임
    private String username;    // 아이디
    private String password;    // 비밀번호
    private Integer birthYear;  // 생년

    private String latitude;    // 위도
    private String longitude;   // 경도

    //일대다 관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberGame> memberGames = new ArrayList<>();   // 게임

    //다대일 관계
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "guild_id")
    private Guild guild;       // 길드

    //== 생성 메서드 ==//
    public static Member createMember(String nickname, String username, String password, Integer birthYear){
        Member member = new Member();
        member.setNickname(nickname);
        member.setUsername(username);
        member.setPassword(password);
        member.setBirthYear(birthYear);

        return member;
    }

    //==연관 관계 메서드==//

    //게임 추가
    public void addGame(MemberGame memberGame){
        memberGames.add(memberGame);
    }

    //멤버 끼리 거리 계산
    public static Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        final double EARTH_RADIUS = 6371.0; // 지구 반경 (km)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c; // 결과는 킬로미터 단위
    }

}
