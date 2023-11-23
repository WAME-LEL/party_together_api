package com.party.partytogether.api;


import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.Member;
import com.party.partytogether.domain.MemberGame;
import com.party.partytogether.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:8081")
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("/api/member/signIn")
    public ResponseEntity<?> signIn(@ModelAttribute SignInRequest request, BindingResult bindingResult){
        Member member = memberService.singIn(request.username, request.password);

        if (bindingResult.hasErrors()){
            return ResponseEntity.internalServerError().body("signIn failed");
        }

        return ResponseEntity.ok(new Result(new SignInResponse(member)));

    }

    @PostMapping("/api/member/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request){
        memberService.memberRegistration(request.nickname, request.username, request.password);

        return ResponseEntity.ok("signUp successfully");
    }



    @PostMapping("/api/member/guild/join")
    public ResponseEntity<?> guildJoin(@RequestBody GuildJoinRequest request){
        memberService.guildJoin(request.memberId, request.guildId);

        return ResponseEntity.ok("guild join successfully");

    }

    @GetMapping("/api/member/game")
    public Result playingGame(@ModelAttribute PlayingGameRequest request){
        List<MemberGame> gameList = memberService.playingGameList(request.memberId);

        return new Result(gameList);
    }

    @PostMapping("/api/member/game")
    public ResponseEntity<?> playingGameRegistration(@RequestBody GameRegistrationRequest request){
        memberService.playingGameRegistration(request.getMemberId(),request.getGameList());


        return ResponseEntity.ok("playing game registration successfully");
    }

    @PostMapping("/api/member/location")
    public ResponseEntity<?> memberLocationSave(@RequestBody memberLocationRequest request){
        memberService.memberLocation(request.memberId, request.latitude, request.longitude);

        return ResponseEntity.ok("member location save successfully");
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    static class SignInRequest {
        private String username;
        private String password;
    }

    @Data
    @AllArgsConstructor
    static class SignInResponse {
        private Member member;
    }
    @Data
    static class SignUpRequest {
        private String nickname;
        private String username;
        private String password;
    }

    @Data
    static class GuildJoinRequest {
        private Long memberId;
        private Long guildId;

    }

    @Data
    static class GameRegistrationRequest {
        private List<Game> gameList;
        private Long memberId;
    }

// DTO 말고 그냥 Long memberId로 받으면 {'memberId':153} 말고 { 153 } 이렇게 받아야 됨
    @Data
    static class PlayingGameRequest {
        private Long memberId;
    }

    @Data
    static class memberLocationRequest{
        private Long memberId;
        private String latitude;
        private String longitude;
    }

}
