package com.party.partytogether.service;


import com.party.partytogether.domain.Board;
import com.party.partytogether.domain.Member;
import com.party.partytogether.repository.BoardRepository;
import com.party.partytogether.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void post(String title, String content, String opentalk, String type, Long memberId){
        Member member = memberRepository.findOne(memberId);
        Board board = Board.createBoard(title, content, opentalk, type, member);

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

