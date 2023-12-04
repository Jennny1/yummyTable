package com.example.yummytable.controller;

import com.example.yummytable.dto.member.CreateMember;
import com.example.yummytable.dto.member.DeleteMember;
import com.example.yummytable.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  /*회원 등록*/
  @PostMapping("/member")
  public CreateMember.Response createMember(@RequestBody CreateMember.Request request) {

    return CreateMember.Response.from(memberService.createMember(request));
  }

  /*회원 탈퇴*/
  @DeleteMapping("/member")
  public CreateMember.Response deleteMember(@RequestBody DeleteMember.Request request) {

    return CreateMember.Response.from(memberService.deleteMember(request));
  }

  /*회원 수정*/

  /*회원 정보 보기*/

}

