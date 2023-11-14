package com.party.partytogether.service;


import com.party.partytogether.domain.Guild;
import com.party.partytogether.repository.GuildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.party.partytogether.domain.Guild.createGuild;

@Service
@RequiredArgsConstructor
@Transactional
public class GuildService {
    private final GuildRepository guildRepository;

    public void guildRegist(String name, String introduce){
        Guild guild = createGuild(name, introduce);
        guildRepository.save(guild);

    }



}
