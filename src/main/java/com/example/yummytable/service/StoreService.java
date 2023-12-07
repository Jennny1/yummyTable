package com.example.yummytable.service;

import com.example.yummytable.domain.Booking;
import com.example.yummytable.domain.Member;
import com.example.yummytable.domain.Store;
import com.example.yummytable.dto.store.CreateStore;
import com.example.yummytable.dto.store.StoreDto;
import com.example.yummytable.dto.store.UpdateStore.Request;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.BookingRepository;
import com.example.yummytable.repository.MemberRepository;
import com.example.yummytable.repository.StoreRepository;
import com.example.yummytable.type.ErrorCode;
import com.example.yummytable.type.Status;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {

  private final StoreRepository storeRepository;
  private final BookingRepository bookingRepository;
  private final MemberRepository memberRepository;

  /*상점 등록*/
  public StoreDto createStore(CreateStore.Request request) {
    // 아이디 확인
    Optional<Member> member = memberRepository.findByMemberId(request.getMemberId());
    if (member.isEmpty()) {
      throw new yummyException(ErrorCode.MEMBER_IS_NOT_EXIST);
    }

    // 상점 이름 검색
    List<Store> storeNames = storeRepository.findAllByStoreName(request.getStoreName());
    if (!storeNames.isEmpty()) {
      throw new yummyException(ErrorCode.STORE_NAME_IS_ALREADY_EXIST);
    }

    return StoreDto.formEntity(
        storeRepository.save(
            Store.builder()
                .member(Member.builder().memberId(request.getMemberId()).build())
                .storeName(request.getStoreName())
                .keyword(request.getKeyword())
                .locationX(request.getLocationX())
                .locationY(request.getLocationY())
                .menu(request.getMenu())
                .storeStatus(Status.EXISTENT)
                .capacity(request.getCapacity())
                .numberOfApplicants(request.getNumberOfApplicants())
                .registeredAt(LocalDateTime.now())
                .build()));
  }


  /*
  상점 삭제
  - input : storeId
  - output : StoreStatus.DELETE로 변경
  - 예약된 이력이 있을때 삭제 불가
  */
  public StoreDto deleteStore(Long storeId, Long memberId) {
    // 아이디 확인
    Optional<Member> member = memberRepository.findByMemberId(memberId);
    if (member.isEmpty()) {
      throw new yummyException(ErrorCode.MEMBER_IS_NOT_EXIST);
    }

    // 작성자 아이디 가져오기
    Optional<Store> byStoreId = storeRepository.findByStoreId(storeId);

    // 작성자와 일치여부 확인
    if (byStoreId.get().getMember().getMemberId() != memberId) {
      throw new yummyException(ErrorCode.MEMBER_NOT_MATCH);
    }

    Optional<Store> store = storeRepository.findByStoreId(storeId);

    // 상점 id 등록 여부, 삭제 여부 검색
    if (store.isEmpty() || store.get().getStoreStatus().equals(Status.DELETE)) {
      throw new yummyException(ErrorCode.STORE_IS_NOT_EXIST);
    }

    // 예약 등록 여부 검색
    List<Booking> bookings = bookingRepository.findAllByStoreStoreIdAndBookingStatusAndBookingDateAfter(
        storeId, Status.EXISTENT, LocalDate.now());

    if (!bookings.isEmpty()) {
      throw new yummyException(ErrorCode.FAIL_TO_DELETE_STORE);
    }

    // 상점 삭제
    store.get().setStoreStatus(Status.DELETE);
    store.get().setUnregisteredAt(LocalDateTime.now());

    return StoreDto.formEntity(storeRepository.save(store.get()));

  }

  /*
  상점 수정
  - 예약된 이력이 있을때 수정 불가
  */
  public StoreDto updateStore(@Valid Request request) {

    // 상점 기존 정보 가져오기
    Optional<Store> store = storeRepository.findByStoreId(request.getStoreId());

    // 상점 id 등록 여부, 삭제 여부 검색
    if (store.isEmpty() || store.get().getStoreStatus().equals(Status.DELETE)) {
      throw new yummyException(ErrorCode.STORE_IS_NOT_EXIST);
    }

    // 작성자 아이디 가져오기
    Optional<Store> byStoreId = storeRepository.findByStoreId(request.getStoreId());

    // 작성자와 일치여부 확인
    if (byStoreId.get().getMember().getMemberId() != request.getMemberId()) {
      throw new yummyException(ErrorCode.MEMBER_NOT_MATCH);
    }

    // 예약 등록 여부 검색
    List<Booking> bookings = bookingRepository.findAllByStoreStoreIdAndBookingStatusAndBookingDateAfter(
        request.getStoreId(), Status.EXISTENT, LocalDate.now());

    if (!bookings.isEmpty()) {
      throw new yummyException(ErrorCode.FAIL_TO_DELETE_STORE);
    }

    // 수정
    if (!store.get().getStoreName().equals(request.getStoreName())) {
      store.get().setStoreName(request.getStoreName());
    }

    if (!store.get().getKeyword().equals(request.getKeyword())) {
      store.get().setKeyword(request.getKeyword());
    }

    if (!store.get().getLocationX().equals(request.getLocationX())) {
      store.get().setLocationX(request.getLocationX());
    }

    if (!store.get().getLocationY().equals(request.getLocationY())) {
      store.get().setLocationY(request.getLocationY());
    }

    if (!store.get().getMenu().equals(request.getMenu())) {
      store.get().setMenu(request.getMenu());
    }

    if (store.get().getCapacity() != request.getCapacity()) {
      store.get().setCapacity(request.getCapacity());
    }

    return StoreDto.formEntity(storeRepository.save(store.get()));
  }

}
