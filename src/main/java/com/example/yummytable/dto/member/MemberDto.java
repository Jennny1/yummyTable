package com.example.yummytable.dto.member;

import com.example.yummytable.domain.Member;
import com.example.yummytable.type.Status;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto implements UserDetails {

  @Id
  @GeneratedValue
  private Long memberId;
  private String email;
  private String username;
  private String password;
  private Status memberStatus;
  private String token;
  private String role;

  private LocalDateTime registeredAt;
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;

  public static MemberDto fromEntity(Member member) {
    return MemberDto.builder()
        .memberId(member.getMemberId())
        .email(member.getEmail())
        .username(member.getUsername())
        .password(member.getPassword())
        .token(member.getToken())
        .role(member.getRole())
        .memberStatus(member.getMemberStatus())
        .registeredAt(member.getRegisteredAt())
        .updatedAt(member.getUpdatedAt())
        .unregisteredAt(member.getUnregisteredAt())
        .build();

  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
