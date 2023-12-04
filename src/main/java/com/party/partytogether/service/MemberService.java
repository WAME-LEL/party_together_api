package com.party.partytogether.service;


import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.guild.Guild;
import com.party.partytogether.domain.Member;
import com.party.partytogether.domain.MemberGame;
import com.party.partytogether.repository.guild.GuildRepository;
import com.party.partytogether.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final GuildRepository guildRepository;

    //회원 등록
    @Transactional
    public Long memberRegistration(String nickname, String username, String password, Integer birthYear){
        Member member = Member.createMember(nickname, username, password, birthYear);
        Long memberId = memberRepository.save(member);
        return memberId;

    }

    //회원 삭제
    @Transactional
    public void memberDelete(Long id){
        memberRepository.delete(id);
    }


    //하고 있는 게임 등록
    @Transactional
    public void playingGameRegistration(Long memberId, List<Game> gameList){
        Member member = memberRepository.findOne(memberId);

        for (Game game: gameList) {
            MemberGame memberGame = MemberGame.creatMemberGame(member, game);
            member.addGame(memberGame);
        }
    }

    //하고 있는 게임 조회
    public List<MemberGame> playingGameList(Long memberId){
        Member member = memberRepository.findOne(memberId);

        return member.getMemberGames();
    }

    //길드 가입
    @Transactional
    public void guildJoin(Long memberId, Long guildId){
        Member member = memberRepository.findOne(memberId);
        Guild guild = guildRepository.findOne(guildId);

        member.setGuild(guild);
    }

    //회원 위치 설정
    public void memberLocation(Long memberId, String latitude, String longitude){
        Member member = memberRepository.findOne(memberId);

        member.setLatitude(latitude);
        member.setLongitude(longitude);
    }

    //로그인
    public Member singIn(String username, String password){
        return memberRepository.signIn(username, password);
    }

    //회원 닉네임 중복 검사
    public boolean duplicateNickname(String nickname){
        return memberRepository.duplicateNickname(nickname);
    }

    //회원 아이디 중복 검사
    public boolean duplicateUsername(String username){
        return memberRepository.duplicateUsername(username);
    }


    //회원 하나 조회
    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }

    //회원 전체 조회
    public List<Member> findAll(){
        return memberRepository.findAll();
    }


}
