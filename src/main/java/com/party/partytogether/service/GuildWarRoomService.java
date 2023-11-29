package com.party.partytogether.service;

import com.party.partytogether.domain.Guild;
import com.party.partytogether.domain.GuildWarRoom;
import com.party.partytogether.domain.Member;
import com.party.partytogether.repository.GuildRepository;
import com.party.partytogether.repository.GuildWarRoomRepository;
import com.party.partytogether.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuildWarRoomService {
    private final GuildWarRoomRepository guildWarRoomRepository;
    private final GuildRepository guildRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Integer save(Long guildId, Long memberId){
        Guild guild = guildRepository.findOne(guildId);
        Member member = memberRepository.findOne(memberId);
        GuildWarRoom room = GuildWarRoom.createRoom(guild, member);

        return guildWarRoomRepository.save(room);


    }

    public GuildWarRoom findOne(Long id){
        return guildWarRoomRepository.findOne(id);
    }

    public List<GuildWarRoom> findAll(){
        return guildWarRoomRepository.findAll();
    }

    public GuildWarRoom findOneByRoomNumber(Integer roomNumber){
        return guildWarRoomRepository.findOneByRoomNumber(roomNumber);
    }



}
