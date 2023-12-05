package com.example.yummytable.service;

import static com.example.yummytable.type.ErrorCode.STORE_IS_NOT_EXIST;

import com.example.yummytable.domain.Favorit;
import com.example.yummytable.domain.Member;
import com.example.yummytable.domain.Store;
import com.example.yummytable.dto.favorit.FavoritDto;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.FavoritRepository;
import com.example.yummytable.repository.MemberRepository;
import com.example.yummytable.repository.StoreRepository;
import com.example.yummytable.type.ErrorCode;
import com.example.yummytable.type.Status;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoritService {

  private final FavoritRepository favoritRepository;
  private final StoreRepository storeRepository;
  private final MemberRepository memberRepository;

  /*찜하기 등록*/
  public FavoritDto createFavorit (Long storeId, Long memberId) {
    // 상점 존재 확인
    Store store = storeRepository.findByStoreIdAndStoreStatus(storeId, Status.EXISTENT)
        .orElseThrow(() -> new yummyException(STORE_IS_NOT_EXIST));

    // 아이디 존재 확인
    Member member = memberRepository.findByMemberIdAndMemberStatus(memberId, Status.EXISTENT)
        .orElseThrow(() -> new yummyException(ErrorCode.MEMBER_IS_NOT_EXIST));

    // 찜하기 여부 확인
    Optional<Favorit> favorit =
        favoritRepository.findByStoreStoreIdAndMemberMemberId(storeId, memberId);

    if (!favorit.isEmpty()) {
      throw new yummyException(ErrorCode.FAVORIT_ALREADY_EXIST);
    }

    return FavoritDto.formEntity(
        favoritRepository.save(Favorit.builder()
            .store(Store.builder().storeId(storeId).build())
            .favoritStatus(Status.EXISTENT)
            .build()));
  }


  /*좋아요 취소*/
  /*좋아요 리스트 보기*/

}
