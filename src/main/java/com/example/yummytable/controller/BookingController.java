package com.example.yummytable.controller;

import com.example.yummytable.dto.CreateBooking;
import com.example.yummytable.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookingController {

  private final BookingService bookingService;

  // 예약등록
  @PostMapping("/booking")
  public CreateBooking.Response createStore(
      @RequestParam Long storeId,
      @RequestBody @Valid CreateBooking.Request request) {

    return CreateBooking.Response.from(
        bookingService.createBooking(
        storeId,
        request.getBookingDate(),
        request.getNumberOfApplicants())
    );
  }
}
