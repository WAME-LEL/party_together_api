package com.party.partytogether.repository;


import com.party.partytogether.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    //회원 저장
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    //회원 삭제
    public void delete(Long id){
        em.remove(em.find(Member.class, id));
    }

    //회원 로그인
    public Member signIn(String username, String password){
        return em.createQuery("select m from Member m WHERE m.username LIKE :username and m.password like :password", Member.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
    }

    //회원 하나 조회
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    //회원 전체 조회
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
