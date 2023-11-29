package com.example.yummytable.dto;

import com.example.yummytable.domain.Booking;
import com.example.yummytable.domain.Store;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
  private long storeId;
  private int capacity;
  private int numberOfApplicants;

  private String bookingDate;

  @CreatedDate
  private LocalDateTime registeredAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;

  public static BookingDto formEntity(Booking booking) {
    return BookingDto.builder()
        .storeId(booking.getStore().getStoreId())
        .capacity(booking.getStore().getCapacity())
/*        .storeId(Store.builder().storeId(booking.getStore().getStoreId()).build())
        .capacity(Store.builder().capacity(booking.getStore().getCapacity()).build())*/
        .bookingId(booking.getBookingId())
        .numberOfApplicants(booking.getNumberOfApplicants())
        .bookingDate(booking.getBookingDate())
        .registeredAt(booking.getRegisteredAt())
        .updatedAt(booking.getUpdatedAt())
        .unregisteredAt(booking.getUnregisteredAt())
        .build();
  }
}
