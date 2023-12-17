package com.example.yummytable.dto.sign;

import com.example.yummytable.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInDto {

  private String email;
  private String password;
  private String token;
  private String role;

  public static SignInDto fromEntity(Member member) {
    return SignInDto.builder()
        .email(member.getEmail())
        .password(member.getPassword())
        .token(member.getToken())
        .role(member.getRole())
        .build();

  }
}
