package com.example.yummytable.service;

import com.example.yummytable.domain.Booking;
import com.example.yummytable.domain.Store;
import com.example.yummytable.dto.BookingDto;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.BookingRepository;
import com.example.yummytable.repository.StoreRepository;
import com.example.yummytable.type.BookingStatus;
import com.example.yummytable.type.ErrorCode;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

  private final BookingRepository bookingRepository;
  private final StoreRepository storeRepository;
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


  /*예약 등록*/
  public BookingDto createBooking(
      Long storeId, String bookingDate, int numberOfApplicants) {

    // 가게 등록 확인
    Store store = getStore(storeId);

    // 날짜 확인
    extracted(bookingDate, formatter);

    // 예약 인원 확인
    List<Booking> bookings = bookingRepository.findAllByBookingDate(bookingDate);

    // 예약 인원 조절
    if (bookings.size() != 0) {
      int sumNumberOfApplicants = sumNumberOfApplicants(storeId, bookings);

      if (sumNumberOfApplicants >= store.getCapacity() ||
          store.getCapacity() - sumNumberOfApplicants < numberOfApplicants) {
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
            .bookingStatus(BookingStatus.EXISTENT)
            .numberOfApplicants(numberOfApplicants)
            .build()));
  }


  /*예약 삭제*/
  public BookingDto deleteBooking(
      Long storeId, Long bookingId, String bookingDate, int numberOfApplicants) {

    // 가게 등록 확인
    Store store = getStore(storeId);

    // 날짜 확인
    extracted(bookingDate, formatter);

    // 예약 인원 확인
    List<Booking> bookings = bookingRepository.findAllByBookingDate(bookingDate);

    // 예약 번호 확인
    for (Booking book : bookings) {
      if (book.getBookingId() == bookingId) {
        // 예약 취소 상태 확인
        if (book.getBookingStatus() == BookingStatus.DELETE) {
          throw new yummyException(ErrorCode.BOOKING_ALREADY_DELETE);
        }
        // 예약 인원 확인
        if (book.getNumberOfApplicants() != numberOfApplicants) {
          throw new yummyException(ErrorCode.APPLICANTS_IS_DIFFERENT);
        }
        // 예약 취소 처리
        book.setBookingStatus(BookingStatus.DELETE);
        book.setUpdatedAt(LocalDateTime.now());
        book.setUnregisteredAt(LocalDateTime.now());

        bookingRepository.save(book);
        return BookingDto.formEntity(book);

      }
    }

    throw new yummyException(ErrorCode.BOOKING_NOT_FOUND);
  }


  // 가게 등록 확인
  private Store getStore(Long storeId) {

    return storeRepository.findByStoreId(storeId).orElseThrow(() -> new yummyException(
        ErrorCode.STORE_IS_NOT_EXIST));
  }

  // 날짜 확인
  private static void extracted(String bookingDate, DateTimeFormatter formatter) {
    if (!LocalDate.now().isBefore(LocalDate.parse(bookingDate, formatter))) {
      throw new yummyException(ErrorCode.DATE_IS_BEFORE);
    }
  }


  // 예약 신청자 합계
  private int sumNumberOfApplicants(Long storeId, List<Booking> bookings) {
    int sum = 0;
    for (Booking book : bookings) {
      if (book.getStore().getStoreId() == storeId
          && book.getBookingStatus() == BookingStatus.EXISTENT) {
        sum += book.getNumberOfApplicants();
      }
    }
    return sum;
  }
}