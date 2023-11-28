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
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

  private final BookingRepository bookingRepository;
  private final StoreRepository storeRepository;


  // 예약 등록
  public BookingDto createBooking(Long bookingId, Long storeId, String bookingDate,
      int numberOfApplicants) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
/*    // 가게 등록 확인
    storeRepository.findByStoreId(storeId).orElseThrow(() -> new yummyException(
        ErrorCode.STORE_IS_NOT_EXIST));*/


    // 날짜 확인
    if (!LocalDate.now().isBefore(LocalDate.parse(bookingDate, formatter))) {
      throw new yummyException(ErrorCode.DATE_IS_BEFORE);
    }

    // 예약 가능 확인

    return BookingDto.formEntity(bookingRepository.save(
        Booking.builder()
            .bookingId(bookingId)
            /*.storeId(storeId)*/
            .registeredAt(LocalDateTime.now())
            .bookingDate(bookingDate)
            .numberOfApplicants(numberOfApplicants)
            .build()));
  }
}