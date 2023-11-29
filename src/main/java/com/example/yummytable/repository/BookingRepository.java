package com.example.yummytable.repository;

import com.example.yummytable.domain.Booking;
import java.awt.print.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

/*
  List<Booking> findAllByStoreIdAndBookingDate(Long storeId, String bookingDate);

  List<Booking> findAllByStoreId(long storeId);*/
}
