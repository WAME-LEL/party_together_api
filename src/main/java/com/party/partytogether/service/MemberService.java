package com.party.partytogether.service;


import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.Guild;
import com.party.partytogether.domain.Member;
import com.party.partytogether.domain.MemberGame;
import com.party.partytogether.repository.GuildRepository;
import com.party.partytogether.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final GuildRepository guildRepository;

    public void memberRegistration(String nickname, String username, String password){
        Member member = Member.createMember(nickname, username, password);
        memberRepository.save(member);
    }

    public void memberDelete(Long id){
        memberRepository.delete(id);
    }


    public void playingGameRegistration(Long memberId, List<Game> gameList){
        Member member = memberRepository.findOne(memberId);

        for (Game game: gameList) {
            MemberGame memberGame = MemberGame.creatMemberGame(member, game);
            member.addGame(memberGame);
        }
    }

    public List<MemberGame> playingGameList(Long memberId){
        Member member = memberRepository.findOne(memberId);

        return member.getMemberGames();
    }

    public void guildJoin(Long memberId, Long guildId){
        Member member = memberRepository.findOne(memberId);
        Guild guild = guildRepository.findOne(guildId);

        member.setGuild(guild);
    }

    public void memberLocation(Long memberId, String latitude, String longitude){
        Member member = memberRepository.findOne(memberId);

        member.setLatitude(latitude);
        member.setLongitude(longitude);
    }

    public Member singIn(String username, String password){
        return memberRepository.signIn(username, password);
    }

    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }


}
