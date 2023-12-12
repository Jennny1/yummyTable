package com.example.yummytable.service;

import static com.example.yummytable.type.ErrorCode.EMAIL_ALREADY_EXIST;
import static com.example.yummytable.type.ErrorCode.FAIL_TO_FIND_EMAIL;
import static com.example.yummytable.type.ErrorCode.MEMBER_ALREADY_DELETE;
import static com.example.yummytable.type.ErrorCode.PASSWORD_NOT_MATCH;

import com.example.yummytable.domain.Member;
import com.example.yummytable.dto.member.CreateMember.Request;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;
  @Autowired
  BCryptPasswordEncoder encoder;

  /*회원 등록*/
  public MemberDto createMember(Request request) {
    // 이메일 확인
    Optional<Member> member = memberRepository.findByEmail(request.getEmail());
    if (!member.isEmpty()) {
      throw new yummyException(EMAIL_ALREADY_EXIST);
    }

    String passEncode = encoder.encode(request.getPassword());

    return MemberDto.fromEntity(
        memberRepository.save(
            Member.builder()
                .email(request.getEmail())
                .password(passEncode)
                .memberStatus(Status.EXISTENT)
                .registeredAt(LocalDateTime.now()).build()));
  }

  /*회원 탈퇴*/
  public MemberDto deleteMember(DeleteMember.Request request) {
    // 이메일 확인
    Member member = memberRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new yummyException(FAIL_TO_FIND_EMAIL));

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

    // 이메일 확인
    Member member = memberRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new yummyException(ErrorCode.EMAIL_NOT_MATCH));

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

    // 탈퇴한 아이디 확인
    if (member.get().getMemberStatus().equals(Status.DELETE)) {
      throw new yummyException(MEMBER_ALREADY_DELETE);
    }

    return MemberDto.fromEntity(member.get());
  }
}
