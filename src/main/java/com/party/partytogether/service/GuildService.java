package com.party.partytogether.service;


import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.Guild;
import com.party.partytogether.domain.Member;
import com.party.partytogether.repository.GameRepository;
import com.party.partytogether.repository.GuildRepository;
import com.party.partytogether.repository.MemberRepository;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.party.partytogether.domain.Guild.createGuild;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuildService {
    private final GuildRepository guildRepository;
    private final GameRepository gameRepository;
    private final MemberRepository memberRepository;

    //길드 등록
    @Transactional
    public void guildRegistration(String name, String introduce, Long gameId, Long memberId){
        Game game = gameRepository.findOne(gameId);
        Member member = memberRepository.findOne(memberId);
        Guild guild = createGuild(name, introduce, game, member);
        guildRepository.save(guild);

        member.setGuild(guild);
    }

    //길드 삭제
    @Transactional
    public void delete(Long id ){
        guildRepository.delete(id);
    }

    //길드 하나 조회
    public Guild findOne(Long id){
        return guildRepository.findOne(id);
    }

    //게임 종류를 포함한 길드 하나 조회
    public Guild findOneJoinLeaderAndGame(Long guildId){
        return guildRepository.findOneJoinLeaderAndGame(guildId);
    }

    //오름차순 길드 전체 조회
    public List<Guild> findAllDESC(){
        return guildRepository.findAllDESC();
    }

    //게임 종류를 포함한 길드 전체 조회
    public List<Tuple> findAllJoinLeaderAndGame(){
        return guildRepository.findAllJoinLeaderAndGame();
    }

    //길드 멤버 조회
    public List<Member> findAllMembers(Long guildId){
        return guildRepository.findAllGuildMembers(guildId);
    }

    //길드 랭킹 업데이트
    @Transactional
    public void updateGuildRanking(Long guildId, int ranking){
        Guild guild = guildRepository.findOne(guildId);
        guild.setRanking(ranking);
    }

    //길드 포인트 추가
    @Transactional
    public void addPoint(Long guildId, int point){
        Guild guild = guildRepository.findOne(guildId);
        guild.setPoint(point);
    }




}
