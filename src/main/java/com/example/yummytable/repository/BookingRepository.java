package com.example.yummytable.repository;

import com.example.yummytable.domain.Booking;
import com.example.yummytable.type.BookingStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

  List<Booking> findAllByBookingDate(LocalDate bookingDate);

  Optional<Booking> findByBookingIdAndBookingDate(Long bookingId, LocalDate bookingDate);

  List<Booking> findAllByStoreStoreIdAndBookingDateAndBookingStatus(
      Long storeId, LocalDate bookingDate, BookingStatus bookingStatus);

  List<Booking> findAllByStoreStoreIdAndBookingStatusAndBookingDateAfter(
      long storeId, BookingStatus bookingStatus, LocalDate bookingDate);
}
