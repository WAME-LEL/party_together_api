package com.party.partytogether.api.member;


import com.party.partytogether.domain.Game;
import com.party.partytogether.domain.member.Member;
import com.party.partytogether.domain.member.MemberGame;
import com.party.partytogether.service.member.MemberService;
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

    // 로그인
    @GetMapping("/api/member/signIn")
    public ResponseEntity<?> signIn(@ModelAttribute SignInRequest request, BindingResult bindingResult){
        Member member = memberService.singIn(request.username, request.password);

        // 로그인 실패
        if (bindingResult.hasErrors()){
            return ResponseEntity.internalServerError().body("signIn failed");
        }

        return ResponseEntity.ok(new Result(new SignInResponse(member)));

    }

    // 회원가입
    @PostMapping("/api/member/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request){
        Long memberId = memberService.memberRegistration(request.nickname, request.username, request.password, request.birthYear);

        return ResponseEntity.ok(new Result(new SignUpResponse(memberId)));
    }

    @GetMapping("/api/member/nickname/duplicate")
    public ResponseEntity<?> duplicateNickname(@ModelAttribute duplicateNicknameRequest request){
        if(memberService.duplicateNickname(request.nickname)){
            return ResponseEntity.ok("duplicate");
        }
        else{
            return ResponseEntity.ok("usable");
        }
    }

    @GetMapping("/api/member/username/duplicate")
    public ResponseEntity<?> duplicateUsername(@ModelAttribute duplicateUsernameRequest request){
        if(memberService.duplicateUsername(request.username)){
            return ResponseEntity.ok("duplicate");
        }
        else{
            return ResponseEntity.ok("usable");
        }
    }

    // 길드 가입
    @PostMapping("/api/member/guild/join")
    public ResponseEntity<?> guildJoin(@RequestBody GuildJoinRequest request){
        memberService.guildJoin(request.memberId, request.guildId);

        return ResponseEntity.ok("guild join successfully");

    }

    //하고 있는 게임 조회
    @GetMapping("/api/member/game")
    public Result playingGame(@ModelAttribute PlayingGameRequest request){
        List<MemberGame> gameList = memberService.playingGameList(request.memberId);

        return new Result(gameList);
    }

    //하고 있는 게임 등록
    @PostMapping("/api/member/game")
    public ResponseEntity<?> playingGameRegistration(@RequestBody GameRegistrationRequest request){
        memberService.playingGameRegistration(request.getMemberId(),request.getGameList());

        return ResponseEntity.ok("playing game registration successfully");
    }

    //회원 위치 설정
    @PostMapping("/api/member/location")
    public ResponseEntity<?> memberLocationSave(@RequestBody memberLocationRequest request){
        memberService.memberLocation(request.memberId, request.latitude, request.longitude);

        return ResponseEntity.ok("member location save successfully");
    }

    //==DTO==//
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
        private Integer birthYear;
    }

    @Data
    @AllArgsConstructor
    static class SignUpResponse{
        private Long memberId;
    }

    @Data
    static class duplicateNicknameRequest{
        private String nickname;
    }

    @Data
    static class duplicateUsernameRequest{
        private String username;
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
