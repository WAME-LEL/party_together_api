package com.party.partytogether.service;


import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.Guild;
import com.party.partytogether.domain.Member;
import com.party.partytogether.repository.GameRepository;
import com.party.partytogether.repository.GuildRepository;
import com.party.partytogether.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.party.partytogether.domain.Guild.createGuild;

@Service
@RequiredArgsConstructor
@Transactional
public class GuildService {
    private final GuildRepository guildRepository;
    private final GameRepository gameRepository;
    private final MemberRepository memberRepository;

    public void guildRegist(String name, String introduce, Long gameId, Long memberId){
        Game game = gameRepository.findOne(gameId);
        Member member = memberRepository.findOne(memberId);
        Guild guild = createGuild(name, introduce, game, member);
        guildRepository.save(guild);

    }

    public void delete(Long id ){
        guildRepository.delete(id);
    }

    public Guild findOne(Long id){
        return guildRepository.findOne(id);
    }

    public List<Guild> findAll(){
        return guildRepository.findAll();
    }



}
