package com.party.partytogether.api;


import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.Member;
import com.party.partytogether.service.GameService;
import com.party.partytogether.service.MemberGameService;
import com.party.partytogether.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class MemberGameApiController {
    private final MemberGameService memberGameService;
    private final MemberService memberService;
    private final GameService gameService;

    // 같은 게임을 하고 있는 멤버 조회
    @GetMapping("/api/memberGame")
    public Result findAllSameMember(@ModelAttribute SameMemberRequest request){
        Map<Long, List<Game>> allSameMember = memberGameService.findAllSameMember(request.memberId);

        Member user = memberService.findOne(request.memberId);
        int total = memberGameService.findAllGameByMember(request.memberId).size();

        List<SameMemberResponse> response = new ArrayList<>();

        // 같은 게임을 하고 있는 회원들의 정보를 DTO로 변환
        for(Long memberId : allSameMember.keySet()){
            Member member = memberService.findOne(memberId);
            List<Game> sameGameList = allSameMember.get(memberId);

            Double similarity = (double)sameGameList.size() / total * 100;
            double distance;

            Integer age = member.getBirthYear() - LocalDate.now().getYear();

            // 좌표가 있으면 거리 계산
            if (user.getLatitude() != null && user.getLongitude() != null &&
                    member.getLatitude() != null && member.getLongitude() != null) {
                distance = Member.calculateDistance(
                        Double.valueOf(user.getLatitude()),
                        Double.valueOf(user.getLongitude()),
                        Double.valueOf(member.getLatitude()),
                        Double.valueOf(member.getLongitude()));
            } else {
                distance = 42000;     // 좌표가 없으면 거리 0
            }


            // DTO에 추가
            response.add(new SameMemberResponse(member.getId(), member.getNickname(), age, sameGameList, similarity, distance));
        }

        // 거리 순으로 정렬
        response = response.stream()
                .sorted(Comparator.comparingDouble(SameMemberResponse::getDistance))
                .collect(Collectors.toList());

        return new Result(response);
    }

    //==DTO==//

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
        private Integer age;
        private List<Game> sameGameList;
        private Double similarity;
        private Double distance;
    }

}
