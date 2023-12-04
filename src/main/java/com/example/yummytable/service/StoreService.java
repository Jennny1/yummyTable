package com.example.yummytable.service;

import com.example.yummytable.domain.Booking;
import com.example.yummytable.domain.Store;
import com.example.yummytable.dto.store.StoreDto;
import com.example.yummytable.dto.store.UpdateStore.Request;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.BookingRepository;
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

  /*상점 등록*/
  public StoreDto createStore(
      long storeId, String storeName, String keyword, Double locationX, Double locationY,
      String menu, int capacity, int numberOfApplicants) {

    // 상점 이름 검색
    List<Store> storeNames = storeRepository.findAllByStoreName(storeName);

    if (!storeNames.isEmpty()) {
      throw new yummyException(ErrorCode.STORE_NAME_IS_ALREADY_EXIST);
    }

    return StoreDto.formEntity(
        storeRepository.save(
            Store.builder()
                .storeId(storeId)
                .storeName(storeName)
                .keyword(keyword)
                .locationX(locationX)
                .locationY(locationY)
                .menu(menu)
                .storeStatus(Status.EXISTENT)
                .capacity(capacity)
                .numberOfApplicants(numberOfApplicants)
                .registeredAt(LocalDateTime.now())
                .build()));
  }


  /*
  상점 삭제
  - input : storeId
  - output : StoreStatus.DELETE로 변경
  - 예약된 이력이 있을때 삭제 불가
  */
  public StoreDto deleteStore(long storeId) {
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
