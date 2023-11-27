package com.party.partytogether.service;


import com.party.partytogether.domain.Game;
import com.party.partytogether.repository.GameRepository;
import com.party.partytogether.repository.MemberGameRepository;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberGameService {
    private final MemberGameRepository memberGameRepository;
    private final GameRepository gameRepository;

    //같은 게임을 하는 멤버와 게임 조회
    public Map<Long, List<Game>> findAllSameMember(Long memberId){
        List<Tuple> results = memberGameRepository.findAllSameMember(memberId);

        Map<Long, List<Game>> sameMemberMap= new HashMap<>();       //key: memberId, value: gameList

        for (Tuple result: results) {
            Long otherMemberId = result.get(0, Long.class);
            Long gameId = result.get(1, Long.class);
            Game game = gameRepository.findOne(gameId);

            sameMemberMap.computeIfAbsent(otherMemberId, k -> new ArrayList<>()).add(game); //sameMemberMap에 key(memberId)가 없으면 새로운 ArrayList 생성
        }

        return sameMemberMap;
    }


}
