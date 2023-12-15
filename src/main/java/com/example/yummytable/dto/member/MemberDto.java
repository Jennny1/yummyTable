package com.example.yummytable.dto.member;

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
public class MemberDto {

  @Id
  @GeneratedValue
  private Long memberId;
  private String email;
  private String userName;
  private String password;
  private String token;
  private Status memberStatus;

  private LocalDateTime registeredAt;
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;

  public static MemberDto fromEntity(Member member) {
    return MemberDto.builder()
        .memberId(member.getMemberId())
        .email(member.getEmail())
        .userName(member.getUserName())
        .password(member.getPassword())
        .token(member.getToken())
        .memberStatus(member.getMemberStatus())
        .registeredAt(member.getRegisteredAt())
        .updatedAt(member.getUpdatedAt())
        .unregisteredAt(member.getUnregisteredAt())
        .build();

  }
}
