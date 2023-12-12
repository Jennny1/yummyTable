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


public class GetMember {


  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {

    private Long memberId;
    private String email;
    private Status memberStatus;

    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private LocalDateTime unregisteredAt;

    public static Response from(MemberDto memberDto) {
      return Response.builder()
          .memberId(memberDto.getMemberId())
          .email(memberDto.getEmail())
          .memberStatus(Status.EXISTENT)
          .registeredAt(memberDto.getRegisteredAt())
          .build();
    }
  }
}
