package com.party.partytogether.controller;


import com.party.partytogether.domain.Board;
import com.party.partytogether.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    //게시판 타입에 따른 게시글 검색
    @GetMapping("/board")
    public String boardList(@RequestParam(value = "keyword", required = false) String keyword, Model model){
        List<Board> boardList;
        if(keyword != null && !keyword.isEmpty()){
            boardList = boardService.findAllBySearchWord(keyword);
        }
        else{
            boardList = boardService.findAll();
        }

        model.addAttribute("boardList", boardList);

        return "board/boardList";
    }

    // 게시글 폼으로 이동
    @GetMapping("/board/add")
    public String boardForm(){
        return "board/addBoard";
    }

    // 게시글 상세 조회
    @PostMapping("/board/registration")
    public String BoardRegistration(@RequestParam ("boardTitle") String boardTitle,
                                    @RequestParam ("boardContent") String boardContent,
                                    @RequestParam ("opentalk") String opentalk,
                                    @RequestParam ("type") String type,
                                    @RequestParam ("memberId") Long memberId
    ){

        boardService.post(boardTitle, boardContent, opentalk, type, memberId);

        return "redirect:/board";
    }

    // 게시글 삭제
    @PostMapping("/board/{boardId}/delete")
    public String deleteBoard(@PathVariable ("boardId") Long boardId){
        boardService.delete(boardId);

        return "redirect:/board";
    }
}
