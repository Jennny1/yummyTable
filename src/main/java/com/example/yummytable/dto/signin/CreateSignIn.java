package com.example.yummytable.dto.signin;

import com.example.yummytable.dto.member.MemberDto;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;


public class CreateSignIn {
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "이메일 형태로 입력해주세요")
    private String email;
    @NotNull
    private String password;

  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {

    private String email;
    private String password;
    private String token;


    public static Response from(MemberDto memberDto) {
      return Response.builder()
          .email(memberDto.getEmail())
          .password(memberDto.getPassword())
          .token(memberDto.getToken())
          .build();

    }
  }
}
