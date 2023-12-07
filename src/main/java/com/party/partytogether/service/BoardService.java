package com.party.partytogether.service;


import com.party.partytogether.domain.Board;
import com.party.partytogether.domain.member.Member;
import com.party.partytogether.repository.BoardRepository;
import com.party.partytogether.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    //게시글 저장
    @Transactional
    public void post(String title, String content, String opentalk, String type, Long memberId){
        Member member = memberRepository.findOne(memberId);
        Board board = Board.createBoard(title, content, opentalk, type, member);

        boardRepository.save(board);
    }

    //게시글 삭제
    @Transactional
    public void delete(Long id){
        boardRepository.delete(id);
    }

    //게시글 하나 조회
    public Board findOne(Long id){
        return boardRepository.findOne(id);
    }

    //게시글 전체 조회
    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    //게시판 타입에 따른 게시글 검색
    public List<Board> findAllBySearchWord(String keyword){
        return boardRepository.findAllBySearchWord(keyword);
    }

    public List<Board> findAllByMemberId(Long memberId){
        return boardRepository.findAllByMemberId(memberId);
    }

}

