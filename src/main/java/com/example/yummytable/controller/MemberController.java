package com.example.yummytable.controller;

import com.example.yummytable.dto.member.DeleteMember;
import com.example.yummytable.dto.member.GetMember;
import com.example.yummytable.dto.member.UpdateMember;
import com.example.yummytable.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;


  /*회원 탈퇴*/
  @DeleteMapping("/member")
  public DeleteMember.Response deleteMember(@Valid @RequestBody DeleteMember.Request request) {

    return DeleteMember.Response.from(memberService.deleteMember(request));
  }

  /*회원 수정*/
  @PatchMapping("/member")
  public UpdateMember.Response updateMember(@Valid @RequestBody UpdateMember.Request request) {

    return UpdateMember.Response.from(memberService.updateMember(request));

  }

  /*회원 정보 보기*/
  @GetMapping("/member")
  public GetMember.Response getMember(@RequestParam Long memberId) {

    return GetMember.Response.from(memberService.getMember(memberId));
  }
}

