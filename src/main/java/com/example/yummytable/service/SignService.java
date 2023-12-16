package com.example.yummytable.service;

import static com.example.yummytable.type.ErrorCode.EMAIL_ALREADY_EXIST;
import static com.example.yummytable.type.ErrorCode.FAIL_TO_FIND_EMAIL;
import static com.example.yummytable.type.ErrorCode.PASSWORD_NOT_MATCH;

import com.example.yummytable.domain.Member;
import com.example.yummytable.dto.member.CreateMember;
import com.example.yummytable.dto.member.MemberDto;
import com.example.yummytable.dto.signin.CreateSignIn;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.MemberRepository;
import com.example.yummytable.security.SecurityService;
import com.example.yummytable.type.Status;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SignService {

  private final MemberRepository memberRepository;
  private final SecurityService securityService;

  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  /*회원 등록*/
  public MemberDto signup(CreateMember.Request request) {
    // 이메일 확인
    Optional<Member> member = memberRepository.findByEmail(request.getEmail());
    if (!member.isEmpty()) {
      throw new yummyException(EMAIL_ALREADY_EXIST);
    }

    String passEncode = encoder.encode(request.getPassword());

    return MemberDto.fromEntity(memberRepository.save(
        Member.builder()
            .email(request.getEmail())
            .password(passEncode)
            .username(request.getUserName())
            .role("ROLE_LOGOUT")
            .memberStatus(Status.EXISTENT)
            .registeredAt(LocalDateTime.now())
            .build()));
  }

  /*로그인*/
  public MemberDto signin(CreateSignIn.Request request) {
    // 이메일 확인
    Optional<Member> member = memberRepository.findByEmail(request.getEmail());
    if (member.isEmpty()) {
      throw new yummyException(FAIL_TO_FIND_EMAIL);
    }

    String passEncode = encoder.encode(request.getPassword());

    // 비밀번호 확인
    if (!encoder.matches(request.getPassword(), member.get().getPassword())) {
      throw new yummyException(PASSWORD_NOT_MATCH);
    }

    String token = securityService.createToken(passEncode);

    member.get().setToken(token);
    member.get().setRole("ROLE_LOGIN");

    return MemberDto.fromEntity(member.get());

  }


}
