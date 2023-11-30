package com.example.yummytable.controller;

import com.example.yummytable.dto.CreateBooking;
import com.example.yummytable.dto.DeleteBooking;
import com.example.yummytable.dto.ReadBooking;
import com.example.yummytable.dto.ReadBooking.Response;
import com.example.yummytable.dto.UpdateBooking;
import com.example.yummytable.service.BookingService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookingController {

  private final BookingService bookingService;

  /*예약 등록*/
  @PostMapping("/booking")
  public CreateBooking.Response createBooking(
      @RequestParam Long storeId,
      @RequestBody @Valid CreateBooking.Request request) {

    return CreateBooking.Response.from(
        bookingService.createBooking(
            storeId,
            request.getBookingDate(),
            request.getNumberOfApplicants())
    );
  }


  /*예약 보기*/
  @GetMapping("/booking")
  public List<Response> getBooking(
      @RequestParam Long storeId,
      @RequestBody @Valid ReadBooking.Request request) {

    return bookingService.readBooking(storeId, request.getBookingDate()).stream()
        .map(bookingDto -> ReadBooking.Response.builder()
            .bookingId(bookingDto.getBookingId())
            .storeId(bookingDto.getStoreId())
            .capacity(bookingDto.getCapacity())
            .numberOfApplicants(bookingDto.getNumberOfApplicants())
            .bookingDate(bookingDto.getBookingDate())
            .bookingStatus(bookingDto.getBookingStatus())
            .registeredAt(bookingDto.getRegisteredAt())
            .updatedAt(bookingDto.getUpdatedAt()).build()).collect(Collectors.toList());
  }


  /*예약 삭제*/
  @DeleteMapping("/booking")
  public DeleteBooking.Response deleteBooking(
      @RequestParam Long storeId, @RequestParam Long bookingId,
      @RequestBody @Valid DeleteBooking.Request request) {

    return DeleteBooking.Response.from(
        bookingService.deleteBooking(
            storeId,
            bookingId,
            request.getBookingDate(),
            request.getNumberOfApplicants()));
  }


  /*예약 수정*/
  @PatchMapping("/booking")
  public UpdateBooking.Response updateBooking(
      @RequestParam Long storeId, @RequestParam Long bookingId,
      @RequestBody @Valid UpdateBooking.Request request) {

    return UpdateBooking.Response.from(
        bookingService.updateBooking(
            storeId,
            bookingId,
            request.getBookingDate(),
            request.getNumberOfApplicants(),
            request.getNewBookingDate(),
            request.getNewNumberOfApplicants()));
  }


}
