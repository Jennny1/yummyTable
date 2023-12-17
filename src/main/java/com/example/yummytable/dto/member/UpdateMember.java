package com.example.yummytable.dto.member;

import com.example.yummytable.type.Status;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;


public class UpdateMember {

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
    @NotNull
    private String newPassword;

  }


  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {

    private Long memberId;
    private String email;
    private String username;
    private Status memberStatus;

    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private LocalDateTime unregisteredAt;

    public static Response from(MemberDto memberDto) {
      return Response.builder()
          .memberId(memberDto.getMemberId())
          .email(memberDto.getEmail())
          .username(memberDto.getUsername())
          .memberStatus(Status.EXISTENT)
          .registeredAt(memberDto.getRegisteredAt())
          .build();
    }
  }
}
