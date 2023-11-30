package com.example.yummytable.repository;

import com.example.yummytable.domain.Booking;
import com.example.yummytable.type.BookingStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

  List<Booking> findAllByBookingDate(String bookingDate);

  Optional<Booking> findByBookingIdAndBookingDate(Long bookingId, String bookingDate);

  List<Booking> findAllByStoreStoreIdAndBookingDateAndBookingStatus(
      Long storeId, String bookingDate, BookingStatus bookingStatus);




  /*  List<Booking> findAllByBookingIdAndBookingDate(Long bookingId, String bookingDate);*/
}
