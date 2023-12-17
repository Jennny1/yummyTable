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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoritService {

  private final FavoritRepository favoritRepository;
  private final StoreRepository storeRepository;
  private final MemberRepository memberRepository;


  /**
   * 찜하기 등록
   * @param storeId
   * @param memberId
   * @return
   */
  public FavoritDto createFavorit(Long storeId, Long memberId) {
    validFavoritInfo(storeId, memberId);

    // 로그인 여부 확인
    validLogin(memberId);

    // 찜하기 여부 확인
    Optional<Favorit> favorit =
        favoritRepository.findByStoreStoreIdAndMemberMemberId(storeId, memberId);

    Favorit build = new Favorit();

    if (!favorit.isEmpty() && favorit.get().getFavoritStatus().equals(Status.EXISTENT)) {
      throw new yummyException(ErrorCode.FAVORIT_ALREADY_EXIST);

    } else if (!favorit.isEmpty() && favorit.get().getFavoritStatus().equals(Status.DELETE)) {
      build = favorit.get();
      build.setFavoritStatus(Status.EXISTENT);
      build.setUpdatedAt(LocalDateTime.now());

    } else {
      build = Favorit.builder()
          .store(Store.builder().storeId(storeId).build())
          .member(Member.builder().memberId(memberId).build())
          .registeredAt(LocalDateTime.now())
          .favoritStatus(Status.EXISTENT)
          .build();
    }

    return FavoritDto.formEntity(favoritRepository.save(build));
  }




  /**
   * 좋아요 취소
   * @param storeId
   * @param memberId
   * @return
   */
  public FavoritDto deleteFavorit(Long storeId, Long memberId) {

    validFavoritInfo(storeId, memberId);

    // 로그인 여부 확인
    validLogin(memberId);

    // 찜하기 여부 확인
    Optional<Favorit> favorit =
        favoritRepository.findByStoreStoreIdAndMemberMemberId(storeId, memberId);

    if (favorit.isEmpty() || favorit.get().getFavoritStatus().equals(Status.DELETE)) {
      throw new yummyException(ErrorCode.FAVORIT_NOT_EXIST);
    }

    favorit.get().setFavoritStatus(Status.DELETE);
    favorit.get().setUnregisteredAt(LocalDateTime.now());

    return FavoritDto.formEntity(favoritRepository.save(favorit.get()));

  }


  /**
   * 좋아요 리스트 보기
   * storeId 기준
   * @param storeId
   * @return
   */
  public List<FavoritDto> getFavofit(Long storeId, Long memberId) {
    // 로그인 여부 확인
    validLogin(memberId);

    // 상점 존재 확인
    Store store = storeRepository.findByStoreIdAndStoreStatus(storeId, Status.EXISTENT)
        .orElseThrow(() -> new yummyException(STORE_IS_NOT_EXIST));

    List<Favorit> favorits = favoritRepository.findAllByStoreStoreIdAndFavoritStatus(
        storeId, Status.EXISTENT);

    if (favorits.isEmpty()) {
      throw new yummyException(ErrorCode.FAVORITS_NOT_EXIST);
    }

    return favorits.stream().map(FavoritDto::formEntity).collect(Collectors.toList());
  }


  private void validFavoritInfo(Long storeId, Long memberId) {
    // 상점 존재 확인
    Store store = storeRepository.findByStoreIdAndStoreStatus(storeId, Status.EXISTENT)
        .orElseThrow(() -> new yummyException(STORE_IS_NOT_EXIST));

    // 아이디 존재 확인
    Member member = memberRepository.findByMemberIdAndMemberStatus(memberId, Status.EXISTENT)
        .orElseThrow(() -> new yummyException(ErrorCode.MEMBER_IS_NOT_EXIST));
  }


  private void validLogin(Long memberId) {
    // 로그인 여부 확인
    Member byMemberId = memberRepository.findByMemberIdAndMemberStatus(memberId, Status.EXISTENT)
        .orElseThrow(() -> new yummyException(ErrorCode.MEMBER_IS_NOT_EXIST));

    if (byMemberId.getToken() == null || byMemberId.getToken().isEmpty()) {
      throw new yummyException(ErrorCode.NOT_LOGGED_IN);
    }
  }
}
