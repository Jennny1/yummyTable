package com.example.yummytable.service;

import com.example.yummytable.domain.Booking;
import com.example.yummytable.dto.BookingDto;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.BookingRepository;
import com.example.yummytable.repository.StoreRepository;
import com.example.yummytable.type.ErrorCode;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

  private final BookingRepository bookingRepository;
  private final StoreRepository storeRepository;


  // 예약 등록
  public BookingDto createBooking(Long bookingId, Long storeId, LocalDate bookingDate,
      int numberOfApplicants) {

    // 가게 등록 확인
    storeRepository.findByStoreId(storeId).orElseThrow(() -> new yummyException(
        ErrorCode.STORE_IS_NOT_EXIST));

    return BookingDto.formEntity(bookingRepository.save(
        Booking.builder()
            .bookingId(bookingId)
            .storeId(storeId)
            .registeredAt(LocalDateTime.now())
            .bookingDate(bookingDate)
            .numberOfApplicants(numberOfApplicants)
            .build()));
  }
}