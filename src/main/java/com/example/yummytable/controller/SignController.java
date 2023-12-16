package com.example.yummytable.controller;

import com.example.yummytable.dto.member.CreateMember;
import com.example.yummytable.dto.member.DeleteMember;
import com.example.yummytable.dto.member.GetMember;
import com.example.yummytable.dto.member.UpdateMember;
import com.example.yummytable.dto.signin.CreateSignIn;
import com.example.yummytable.service.MemberService;
import com.example.yummytable.service.SignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignController {

  private final SignService signService;

  /*회원 등록*/
  @PostMapping("/signup")
  public CreateMember.Response createMember(@Valid @RequestBody CreateMember.Request request) {

    return CreateMember.Response.from(
        signService.signup(request));
  }

  /*로그인*/
  @PatchMapping("/signin")
  public CreateSignIn.Response signin(@Valid @RequestBody CreateSignIn.Request request) {

    return CreateSignIn.Response.from(signService.signin(request));
  }

}

