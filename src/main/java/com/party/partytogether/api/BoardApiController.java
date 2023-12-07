package com.party.partytogether.api;


import com.party.partytogether.domain.Board;
import com.party.partytogether.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class BoardApiController {
    private final BoardService boardService;

    // 게시판 타입에 따른 게시글 검색
    @GetMapping("/api/board")
    public Result boardList(@RequestParam(value = "keyword", required = false) String keyword){
        List<Board> boardList;

        // 검색어가 있으면 검색어로 검색, 없으면 전체 검색
        if(keyword != null && !keyword.isEmpty()){
            boardList = boardService.findAllBySearchWord(keyword);
        }
        else{
            boardList = boardService.findAll();
        }

        // 게시글 리스트를 DTO로 변환
        List<BoardDto> collect = boardList.stream()
                .map(b -> new BoardDto(b.getId(), b.getType(), b.getTitle(), b.getContent(), b.getOpentalk(), b.getTime(), b.getMember().getId(), b.getMember().getNickname()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/board/member")
    public Result boardListByMemberId(@RequestParam("memberId") Long memberId){
        List<Board> boardList = boardService.findAllByMemberId(memberId);

        // 게시글 리스트를 DTO로 변환
        List<BoardDto> collect = boardList.stream()
                .map(b -> new BoardDto(b.getId(), b.getType(), b.getTitle(), b.getContent(), b.getOpentalk(), b.getTime(), b.getMember().getId(), b.getMember().getNickname()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    // 게시글 저장
    @PostMapping("/api/board/save")
    public ResponseEntity<?> boardSave(@RequestBody BoardSaveRequest request){
        boardService.post(request.title, request.content, request.opentalk, request.type, request.memberId);

        return ResponseEntity.ok("Board save successfully");
    }

    // 게시글 삭제
    @PostMapping("/api/board/delete")
    public ResponseEntity<?> boardDelete(@RequestBody BoardDeleteRequest request){
        boardService.delete(request.boardId);

        return ResponseEntity.ok("delete successfully");
    }

    //==DTO==//

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class BoardDto{
        private Long boardId;
        private String type;
        private String title;
        private String content;
        private String opentalk;
        private LocalDateTime time;

        private Long memberId;
        private String nickname;

    }

    @Data
    static class BoardListRequest{
        private String keyword;
    }

    @Data
    static class BoardSaveRequest{
        private Long memberId;
        private String type;
        private String title;
        private String content;
        private String opentalk;
    }

    @Data
    static class BoardDeleteRequest{
        private Long boardId;
    }
}

