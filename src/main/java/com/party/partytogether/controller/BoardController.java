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

    @GetMapping("/board")
    public String boardList(@RequestParam(value = "keyword", required = false) String keyword, Model model){
        List<Board> boardList;
        if(keyword != null && !keyword.isEmpty()){
            boardList = boardService.findSearchWord(keyword);
        }
        else{
            boardList = boardService.findAll();
        }

        model.addAttribute("boardList", boardList);

        return "boardList";
    }

    @GetMapping("/board/add")
    public String boardForm(){
        return "addBoard";
    }

    @PostMapping("/board/regist")
    public String registBoard(@RequestParam ("boardTitle") String boardTitle,
                              @RequestParam ("boardContent") String boardContent,
                              @RequestParam ("opentalk") String opentalk,
                              @RequestParam ("type") String type){

        boardService.post(boardTitle, boardContent, opentalk, type);

        return "redirect:/board";
    }

    @PostMapping("/board/{boardId}/delete")
    public String deleteBoard(@PathVariable ("boardId") Long boardId){
        boardService.delete(boardId);

        return "redirect:/board";
    }


}
