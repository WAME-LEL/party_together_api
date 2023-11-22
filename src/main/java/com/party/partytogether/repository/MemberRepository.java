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

    public void save(Member member){
        em.persist(member);
    }

    public void delete(Long id){
        em.remove(em.find(Member.class, id));
    }

    public Member signIn(String username, String password){
        return em.createQuery("select m from Member m WHERE m.username LIKE :username and m.password like :password", Member.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
    }



    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
