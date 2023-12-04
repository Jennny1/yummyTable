package com.example.yummytable.service;

import com.example.yummytable.domain.Member;
import com.example.yummytable.dto.member.CreateMember.Request;
import com.example.yummytable.dto.member.DeleteMember;
import com.example.yummytable.dto.member.MemberDto;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.BoardRepository;
import com.example.yummytable.repository.MemberRepository;
import com.example.yummytable.repository.StoreRepository;
import com.example.yummytable.type.ErrorCode;
import com.example.yummytable.type.Status;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

  private final BoardRepository boardRepository;
  private final StoreRepository storeRepository;
  private final MemberRepository memberRepository;

  /*회원 등록*/
  public MemberDto createMember(Request request) {
    // 이메일 확인
    Optional<Member> member = memberRepository.findByEmail(request.getEmail());
    if (!member.isEmpty()) {
      throw new yummyException(ErrorCode.EMAIL_ALREADY_EXIST);
    }

    return MemberDto.fromEntity(
        memberRepository.save(
            Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .memberStatus(Status.EXISTENT)
                .registeredAt(LocalDateTime.now()).build()));
  }

  public MemberDto deleteMember(DeleteMember.Request request) {
    // 이메일 확인
    Optional<Member> member = memberRepository.findByEmail(request.getEmail());
    if (member.isEmpty()) {
      throw new yummyException(ErrorCode.FAIL_TO_FIND_EMAIL);
    }

    // 비밀번호 확인
    if (!member.get().getPassword().equals(request.getPassword())) {
      throw new yummyException(ErrorCode.PASSWORD_NOT_MATCH);
    }

    // 삭제 여부 확인
    if (member.get().getMemberStatus().equals(Status.DELETE)) {
      throw new yummyException(ErrorCode.MEMBER_ALREADY_DELETE);
    }

    member.get().setMemberStatus(Status.DELETE);
    member.get().setUnregisteredAt(LocalDateTime.now());
    memberRepository.save(member.get());

    return MemberDto.fromEntity(member.get());

  }

  /*회원 탈퇴*/


  /*회원 수정*/

  /*회원 정보 보기*/
}
