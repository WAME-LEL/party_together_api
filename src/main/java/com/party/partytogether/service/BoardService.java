package com.party.partytogether.service;


import com.party.partytogether.domain.Board;
import com.party.partytogether.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;


    public void post(String title, String content, String opentalk, String type){
        Board board = Board.createBoard(title, content, opentalk, type);

        boardRepository.save(board);
    }

    public void delete(Long id){
        boardRepository.delete(id);
    }

    public Board findOne(Long id){
        return boardRepository.findOne(id);
    }

    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    public List<Board> findSearchWord(String keyword){
        return boardRepository.findSearchWord(keyword);
    }

}

