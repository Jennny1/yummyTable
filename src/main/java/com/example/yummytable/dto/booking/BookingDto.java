package com.example.yummytable.dto.booking;

import com.example.yummytable.domain.Booking;
import com.example.yummytable.type.Status;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDto {

  @Id
  @GeneratedValue
  private Long bookingId;
  private Long storeId;
  private Long memberId;

  private int capacity;
  private int numberOfApplicants;

  private LocalDate bookingDate;
  private Status bookingStatus;


  @CreatedDate
  private LocalDateTime registeredAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;

  public static BookingDto formEntity(Booking booking) {
    return BookingDto.builder()
        .bookingId(booking.getBookingId())
        .storeId(booking.getStore().getStoreId())
        .memberId(booking.getMember().getMemberId())
        .capacity(booking.getStore().getCapacity())
        .numberOfApplicants(booking.getNumberOfApplicants())
        .bookingDate(booking.getBookingDate())
        .bookingStatus(booking.getBookingStatus())
        .registeredAt(booking.getRegisteredAt())
        .updatedAt(booking.getUpdatedAt())
        .unregisteredAt(booking.getUnregisteredAt())
        .build();
  }
}
