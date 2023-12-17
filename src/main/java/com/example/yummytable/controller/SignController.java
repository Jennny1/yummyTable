package com.example.yummytable.controller;

import com.example.yummytable.dto.sign.CreateSignUp;
import com.example.yummytable.dto.sign.CreateSignIn;
import com.example.yummytable.service.SignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignController {

  private final SignService signService;

  /*회원 등록*/
  @PostMapping("/signup")
  public CreateSignUp.Response createMember(@Valid @RequestBody CreateSignUp.Request request) {

    return CreateSignUp.Response.from(
        signService.signup(request));
  }

  /*로그인*/
  @PatchMapping("/signin")
  public CreateSignIn.Response signin(@Valid @RequestBody CreateSignIn.Request request) {

    return CreateSignIn.Response.from(signService.signin(request));
  }

}

