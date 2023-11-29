package com.example.yummytable.service;

import com.example.yummytable.domain.Booking;
import com.example.yummytable.domain.Store;
import com.example.yummytable.dto.BookingDto;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.BookingRepository;
import com.example.yummytable.repository.StoreRepository;
import com.example.yummytable.type.ErrorCode;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

  private final BookingRepository bookingRepository;
  private final StoreRepository storeRepository;


  // 예약 등록
  public BookingDto createBooking(Long storeId, String bookingDate,
      int numberOfApplicants) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // 가게 등록 확인
    Store store = storeRepository.findByStoreId(storeId).orElseThrow(() -> new yummyException(
        ErrorCode.STORE_IS_NOT_EXIST));

    // 날짜 확인
    if (!LocalDate.now().isBefore(LocalDate.parse(bookingDate, formatter))) {
      throw new yummyException(ErrorCode.DATE_IS_BEFORE);
    }

    // 예약 인원 확인
    List<Booking> bookings = bookingRepository.findAllByBookingDate(bookingDate);

    if (!bookings.isEmpty()) {
      int sumNumberOfApplicants = sumNumberOfApplicants(storeId, bookings);

      if (sumNumberOfApplicants >= store.getCapacity()) {
        throw new yummyException(ErrorCode.END_OF_BOOKING);
      }
    }

    return BookingDto.formEntity(bookingRepository.save(
        Booking.builder().store(
            Store.builder()
                .storeId(store.getStoreId())
                .capacity(store.getCapacity()).build())
            .registeredAt(LocalDateTime.now())
            .bookingDate(bookingDate)
            .numberOfApplicants(numberOfApplicants)
            .build()));
  }

  private int sumNumberOfApplicants(Long storeId, List<Booking> bookings) {
    int sum = 0;
    for(Booking book : bookings) {
      if (book.getStore().getStoreId() == storeId) {
        sum += book.getStore().getNumberOfApplicants();
      }
    }
    return sum;
  }
}