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
                .station(request.getStation())
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
    Member member = memberRepository.findByMemberId(memberId)
        .orElseThrow(() -> new yummyException(ErrorCode.MEMBER_IS_NOT_EXIST));

    // 작성자 아이디 가져오기
    Store byStoreId = storeRepository.findByStoreId(storeId)
        .orElseThrow(() -> new yummyException(ErrorCode.FAIL_TO_FIND_MEMBERID));

    // 작성자와 일치여부 확인
    if (byStoreId.getMemberId(member) != memberId) {
      throw new yummyException(ErrorCode.MEMBER_NOT_MATCH);
    }

    Store store = storeRepository.findByStoreId(storeId)
        .orElseThrow(() -> new yummyException(ErrorCode.FAIL_TO_FIND_STORE))

    // 상점 id 등록 여부, 삭제 여부 검색
    if (store.getStoreStatus().equals(Status.DELETE)) {
      throw new yummyException(ErrorCode.STORE_IS_NOT_EXIST);
    }

    // 예약 등록 여부 검색
    List<Booking> bookings = bookingRepository.findAllByStoreStoreIdAndBookingStatusAndBookingDateAfter(
        storeId, Status.EXISTENT, LocalDate.now());

    if (!bookings.isEmpty()) {
      throw new yummyException(ErrorCode.FAIL_TO_DELETE_STORE_BOOKING);
    }

    // 상점 삭제
    store.setStoreStatus(Status.DELETE);
    store.setUnregisteredAt(LocalDateTime.now());

    return StoreDto.formEntity(storeRepository.save(store));

  }

  /*
  상점 수정
  - 예약된 이력이 있을때 수정 불가
  */
  public StoreDto updateStore(@Valid Request request) {

    // 상점 기존 정보 가져오기
    Store store = storeRepository.findByStoreId(request.getStoreId())
        .orElseThrow(() -> new yummyException(ErrorCode.FAIL_TO_FIND_STORE));

    // 상점 id 등록 여부, 삭제 여부 검색
    if (store.getStoreStatus().equals(Status.DELETE)) {
      throw new yummyException(ErrorCode.STORE_IS_NOT_EXIST);
    }

    // 작성자 아이디 가져오기
    Store byStoreId = storeRepository.findByStoreId(request.getStoreId())
        .orElseThrow(() -> new yummyException(ErrorCode.FAIL_TO_FIND_MEMBERID));

    // 작성자와 일치여부 확인
    if (byStoreId.getMember().getMemberId() != request.getMemberId()) {
      throw new yummyException(ErrorCode.MEMBER_NOT_MATCH);
    }

    // 예약 등록 여부 검색
    List<Booking> bookings = bookingRepository.findAllByStoreStoreIdAndBookingStatusAndBookingDateAfter(
        request.getStoreId(), Status.EXISTENT, LocalDate.now());

    if (!bookings.isEmpty()) {
      throw new yummyException(ErrorCode.FAIL_TO_DELETE_STORE_BOOKING);
    }

    // 수정
    if (!store.getStoreName().equals(request.getStoreName())) {
      store.setStoreName(request.getStoreName());
    }

    if (!store.getStation().equals(request.getStation())) {
      store.setStation(request.getStation());
    }

    if (!store.getMenu().equals(request.getMenu())) {
      store.setMenu(request.getMenu());
    }

    if (store.getCapacity() != request.getCapacity()) {
      store.setCapacity(request.getCapacity());
    }

    return StoreDto.formEntity(storeRepository.save(store));
  }

}
