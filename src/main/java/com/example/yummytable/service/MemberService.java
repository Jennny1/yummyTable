package com.example.yummytable.service;

import static com.example.yummytable.type.ErrorCode.EMAIL_ALREADY_EXIST;
import static com.example.yummytable.type.ErrorCode.MEMBER_ALREADY_DELETE;
import static com.example.yummytable.type.ErrorCode.NOT_LOGGED_IN;
import static com.example.yummytable.type.ErrorCode.PASSWORD_NOT_MATCH;

import com.example.yummytable.domain.Member;
import com.example.yummytable.dto.member.DeleteMember;
import com.example.yummytable.dto.member.MemberDto;
import com.example.yummytable.dto.member.UpdateMember;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.MemberRepository;
import com.example.yummytable.type.ErrorCode;
import com.example.yummytable.type.Status;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
public class MemberService {

  private final MemberRepository memberRepository;

  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  /*회원 탈퇴*/
  public MemberDto deleteMember(DeleteMember.Request request) {

    // 로그인 여부 확인
    if (loginStatus(request.getEmail()) == false) {
      throw new yummyException(NOT_LOGGED_IN);
    }

    // 이메일 확인
    Member member = memberRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new yummyException(EMAIL_ALREADY_EXIST));

    // 비밀번호 확인
    if (!encoder.matches(request.getPassword(), member.getPassword())) {
      throw new yummyException(PASSWORD_NOT_MATCH);
    }

    // 삭제 여부 확인
    if (member.getMemberStatus().equals(Status.DELETE)) {
      throw new yummyException(MEMBER_ALREADY_DELETE);
    }

    member.setMemberStatus(Status.DELETE);
    member.setUnregisteredAt(LocalDateTime.now());
    memberRepository.save(member);

    return MemberDto.fromEntity(member);

  }


  /*
  회원 수정
  - 비밀번호 수정 가능
  */
  public MemberDto updateMember(UpdateMember.@Valid Request request) {

    // 로그인 여부 확인
    if (loginStatus(request.getEmail()) == false) {
      throw new yummyException(NOT_LOGGED_IN);
    }

    // 이메일 확인
    Member member = memberRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new yummyException(EMAIL_ALREADY_EXIST));

    // 변경할 비밀번호 확인
    if (encoder.matches(request.getNewPassword(), member.getPassword())) {
      throw new yummyException(ErrorCode.PLEASE_INPUT_ANOTHER_PASSWORD);
    }

    member.setPassword(encoder.encode(request.getNewPassword()));
    member.setUpdatedAt(LocalDateTime.now());
    memberRepository.save(member);
    return MemberDto.fromEntity(member);

  }


  /*
  회원 정보 보기
  - 탈퇴하지 않은 회원만 확인 가능
  */
  public MemberDto getMember(Long memberId) {
    Optional<Member> member = memberRepository.findByMemberId(memberId);

    // 로그인 여부 확인
    if (loginStatus(member.get().getEmail()) == false) {
      throw new yummyException(NOT_LOGGED_IN);
    }

    // 탈퇴한 아이디 확인
    if (member.get().getMemberStatus().equals(Status.DELETE)) {
      throw new yummyException(MEMBER_ALREADY_DELETE);
    }

    return MemberDto.fromEntity(member.get());
  }


  // 로그인 여부 확인
  private boolean loginStatus(String email) {
    // 이메일 확인
    Member mem = memberRepository.findByEmailAndMemberStatus(email, Status.EXISTENT)
        .orElseThrow(() -> new yummyException(ErrorCode.FAIL_TO_FIND_EMAIL));

    // 확인
    if (mem.getToken() == null || mem.getToken().isEmpty()) {
      return false;
    }

    return true;
  }
}
