package com.example.yummytable.repository;

import com.example.yummytable.domain.Member;
import com.example.yummytable.type.Status;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  Optional<Member> findByMemberId(Long memberId);

  Optional<Member> findByMemberIdAndMemberStatus(Long memberId, Status status);
}
