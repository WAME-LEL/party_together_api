package com.party.partytogether.service.guild;

import com.party.partytogether.domain.guild.Guild;
import com.party.partytogether.domain.guild.GuildWarRoom;
import com.party.partytogether.domain.Member;
import com.party.partytogether.repository.guild.GuildRepository;
import com.party.partytogether.repository.guild.GuildWarRoomRepository;
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

    //길드전 방 생성
    @Transactional
    public Integer save(Long guildId, Long memberId){
        Guild guild = guildRepository.findOne(guildId);
        Member member = memberRepository.findOne(memberId);
        try{
            Integer lastRoomNumber = guildWarRoomRepository.findLastRoomNumber();
            System.out.println("lastRoomNumber = " + lastRoomNumber);
            GuildWarRoom room = GuildWarRoom.createRoom(guild, member, ++lastRoomNumber);
            System.out.println("lastRoomNumber = " + lastRoomNumber);
            return guildWarRoomRepository.save(room);
        }catch (Exception e){
            GuildWarRoom room = GuildWarRoom.createRoom(guild, member, 1);
            return guildWarRoomRepository.save(room);
        }


    }

    //길드전 방 퇴장
    @Transactional
    public void roomExit(Long memberId){
        memberRepository.findOne(memberId);
        GuildWarRoom room = guildWarRoomRepository.findOneByMemberId(memberId);
        guildWarRoomRepository.delete(room.getId());
    }

    //길드전 방 삭제
    @Transactional
    public void roomDelete(Integer roomNumber){
        guildWarRoomRepository.deleteAllByRoomNumber(roomNumber);
    }

    //길드전 방 참가
    @Transactional
    public void joinRoom(Integer roomNumber, Long memberId){
        Member member = memberRepository.findOne(memberId);
        Guild guild = member.getGuild();

        GuildWarRoom room = GuildWarRoom.joinRoom(guild, member, roomNumber);
        guildWarRoomRepository.save(room);
    }

    //길드전 방 하나 조회
    public GuildWarRoom findOne(Long id){
        return guildWarRoomRepository.findOne(id);
    }

    //길드전 방 번호로 조회
    public GuildWarRoom findOneByRoomNumber(Integer roomNumber){
        return guildWarRoomRepository.findOneByRoomNumber(roomNumber);
    }

    //길드전 방 전체 조회
    public List<GuildWarRoom> findAll(){
        return guildWarRoomRepository.findAll();
    }

    //길드전 방 번호로 조회
    public List<GuildWarRoom> findAllByRoomNumber(Integer roomNumber){
        return guildWarRoomRepository.findAllByRoomNumber(roomNumber);
    }





}
