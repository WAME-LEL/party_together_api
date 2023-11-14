package com.party.partytogether.controller;


import com.party.partytogether.domain.Member;
import com.party.partytogether.service.MemberService;
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
public class MemberController {
    private final MemberService memberService;


    @GetMapping("/member")
    public String memberList(Model model){
        List<Member> memberList = memberService.findAll();
        model.addAttribute("memberList", memberList);

        return "memberList";
    }

    @GetMapping("/member/add")
    public String memberForm(){

        return "addMember";
    }

    @PostMapping("/member/regist")
    public String registMember(@RequestParam ("nickname") String nickname,
                               @RequestParam ("username") String username,
                               @RequestParam ("password") String password){

        memberService.memberRegist(nickname, username, password);

        return "redirect:/member";

    }

    @PostMapping("member/{id}/delete")
    public String deleteMember(@PathVariable("id") Long id){
        memberService.memberDelete(id);

        return "redirect:/member";
    }


}
