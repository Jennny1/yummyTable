package com.example.yummytable.dto;

import com.example.yummytable.type.BookingStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


public class UpdateBooking {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    @NotNull
    @Pattern(regexp = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])", message = "yyyy-MM-dd 형태로 입력해주세요")
    private String bookingDate;

    @NotNull @Min(1)
    private int numberOfApplicants;

    @Pattern(regexp = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])", message = "yyyy-MM-dd 형태로 입력해주세요")
    private String newBookingDate;

    @Min(0)
    private int newNumberOfApplicants;

  }


  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {
    private Long bookingId;
    private Long storeId;

    private int capacity;
    private int numberOfApplicants;

    private String bookingDate;
    private BookingStatus bookingStatus;

    @CreatedDate
    private LocalDateTime registeredAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private LocalDateTime unregisteredAt;


    public static Response from(BookingDto bookingDto) {
      return Response.builder()
          .bookingId(bookingDto.getBookingId())
          .storeId(bookingDto.getStoreId())
          .capacity(bookingDto.getCapacity())
          .numberOfApplicants(bookingDto.getNumberOfApplicants())
          .bookingDate(bookingDto.getBookingDate())
          .bookingStatus(BookingStatus.EXISTENT)
          .registeredAt(bookingDto.getRegisteredAt())
          .updatedAt(bookingDto.getUpdatedAt())
          .unregisteredAt(bookingDto.getUnregisteredAt())
          .build();
    }
  }
}