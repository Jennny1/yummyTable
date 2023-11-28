package com.example.yummytable.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


public class CreateBooking {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    @NotNull
    private LocalDate bookingDate;
    @NotNull
    private int numberOfApplicants;

  }


  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {

    private Long bookingId;
    private Long storeId;
    private LocalDate bookingDate;
    private int numberOfApplicants;

    @CreatedDate
    private LocalDateTime registeredAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private LocalDateTime unregisteredAt;

    public static Response from(BookingDto bookingDto) {
      return Response.builder()
          .bookingId(bookingDto.getBookingId())
          .storeId(bookingDto.getStoreId())
          .bookingDate(bookingDto.getBookingDate())
          .registeredAt(bookingDto.getRegisteredAt())
          .build();
    }
  }
}
