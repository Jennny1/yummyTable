package com.example.yummytable.service;

import com.example.yummytable.domain.Booking;
import com.example.yummytable.domain.Store;
import com.example.yummytable.dto.StoreDto;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.BookingRepository;
import com.example.yummytable.repository.StoreRepository;
import com.example.yummytable.type.BookingStatus;
import com.example.yummytable.type.ErrorCode;
import com.example.yummytable.type.StoreStatus;
import jakarta.transaction.Transactional;
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
                .storeStatus(StoreStatus.EXISTENT)
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
    if (store.isEmpty() || store.get().getStoreStatus().equals(StoreStatus.DELETE)) {
      throw new yummyException(ErrorCode.STORE_IS_NOT_EXIST);
    }

    // 예약 등록 여부 검색
    List<Booking> bookings = bookingRepository.findAllByStoreStoreIdAndBookingStatusAndBookingDateAfter(
        storeId, BookingStatus.EXISTENT, LocalDate.now());

    if (!bookings.isEmpty()) {
      throw new yummyException(ErrorCode.FAIL_TO_DELETE_STORE);
    }

    // 상점 삭제
    store.get().setStoreStatus(StoreStatus.DELETE);
    store.get().setUnregisteredAt(LocalDateTime.now());
    
    return StoreDto.formEntity(storeRepository.save(store.get()));

  }
}
