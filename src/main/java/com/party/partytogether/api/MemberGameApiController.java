package com.party.partytogether.api;


import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.Member;
import com.party.partytogether.domain.MemberGame;
import com.party.partytogether.service.GameService;
import com.party.partytogether.service.MemberGameService;
import com.party.partytogether.service.MemberService;
import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberGameApiController {
    private final MemberGameService memberGameService;
    private final MemberService memberService;
    private final GameService gameService;

    @GetMapping("/api/memberGame")
    public Result findAllSameMember(@ModelAttribute SameMemberRequest request){
        Map<Long, List<Game>> allSameMember = memberGameService.findAllSameMember(request.memberId);
        Member user = memberService.findOne(request.memberId);
        int total = gameService.findAll().size();
        List<SameMemberResponse> response = new ArrayList<>();

        for(Long memberId : allSameMember.keySet()){
            Member member = memberService.findOne(memberId);
            List<Game> sameGameList = allSameMember.get(memberId);

            Double similarity = (double)sameGameList.size() / total * 100;

            Double distance;

            if (user.getLatitude() != null && user.getLongitude() != null &&
                    member.getLatitude() != null && member.getLongitude() != null) {
                distance = memberService.calculateDistance(
                        Double.valueOf(user.getLatitude()),
                        Double.valueOf(user.getLongitude()),
                        Double.valueOf(member.getLatitude()),
                        Double.valueOf(member.getLongitude()));
            } else {
                distance = 0.0;
            }


            response.add(new SameMemberResponse(member.getId(), member.getNickname(), sameGameList, similarity, distance));
        }

        response = response.stream()
                .sorted(Comparator.comparingDouble(SameMemberResponse::getDistance))
                .collect(Collectors.toList());

        return new Result(response);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    static class SameMemberRequest{
        private Long memberId;
    }

    @Data
    @AllArgsConstructor
    static class SameMemberResponse {
        private Long memberId;
        private String nickname;
        private List<Game> sameGameList;
        private Double similarity;
        private Double distance;
    }


}
