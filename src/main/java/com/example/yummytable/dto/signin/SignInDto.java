package com.example.yummytable.dto.signin;

import com.example.yummytable.domain.Member;
import com.example.yummytable.type.Status;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
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

  public static SignInDto fromEntity(Member member) {
    return SignInDto.builder()
        .email(member.getEmail())
        .password(member.getPassword())
        .token(member.getToken())
        .build();

  }
}
