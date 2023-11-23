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

    @Transactional
    public void guildRegistration(String name, String introduce, Long gameId, Long memberId){
        Game game = gameRepository.findOne(gameId);
        Member member = memberRepository.findOne(memberId);
        Guild guild = createGuild(name, introduce, game, member);
        guildRepository.save(guild);

        member.setGuild(guild);
    }

    @Transactional
    public void delete(Long id ){
        guildRepository.delete(id);
    }

    public Guild findOne(Long id){
        return guildRepository.findOne(id);
    }

    public List<Guild> findAllDESC(){
        return guildRepository.findAllDESC();
    }

    public List<Tuple> findAllJoinLeaderAndGame(){
        return guildRepository.findAllJoinLeaderAndGame();
    }

    public Guild findOneJoinLeaderAndGame(Long guildId){
        return guildRepository.findOneJoinLeaderAndGame(guildId);
    }

    public List<Member> findAllMembers(Long guildId){
        return guildRepository.findAllMembers(guildId);
    }

    @Transactional
    public void updateGuildRanking(Long guildId, int ranking){
        Guild guild = guildRepository.findOne(guildId);
        guild.setRanking(ranking);
    }




}
