package com.party.partytogether.api;


import com.party.partytogether.domain.Board;
import com.party.partytogether.domain.Member;
import com.party.partytogether.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BoardApiController {
    private final BoardService boardService;

    @GetMapping("/api/board")
    public Result boardList(@RequestBody Optional<BoardListRequest> request){
        List<Board> boardList;
        String keyword = null;
        if(request.isPresent()){
            keyword= request.get().getKeyword();
            System.out.println("keyword = " + keyword);
        }

        if(keyword != null && !keyword.isEmpty()){
            boardList = boardService.findSearchWord(keyword);
        }
        else{
            boardList = boardService.findAll();
        }

        List<BoardDto> collect = boardList.stream()
                .map(b -> new BoardDto(b.getType(), b.getTitle(), b.getContent(), b.getOpentalk(), b.getTime(), b.getMember()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PostMapping("/api/board/save")
    public ResponseEntity<?> boardSave(@RequestBody BoardSaveRequest request){
        boardService.post(request.title, request.content, request.opentalk, request.type, request.memberId);

        return ResponseEntity.ok("Board save successfully");
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class BoardDto{
        private String type;
        private String title;
        private String content;
        private String opentalk;
        private LocalDateTime time;

        private Member member;

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
}

