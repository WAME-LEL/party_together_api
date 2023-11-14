package com.party.partytogether.service;


import com.party.partytogether.domain.Member;
import com.party.partytogether.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public void memberRegist(String nickname, String username, String password){
        Member member = Member.createMember(nickname, username, password);
        memberRepository.save(member);
    }
    public void memberDelete(Long id){
        memberRepository.delete(id);
    }

    public Member singIn(String username, String password){
        return memberRepository.signIn(username, password);
    }

    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }


}
