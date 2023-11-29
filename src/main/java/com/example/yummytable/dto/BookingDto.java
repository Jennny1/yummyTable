package com.example.yummytable.dto;

import com.example.yummytable.domain.Booking;
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
  @Id
  @GeneratedValue
  private Long storeId;

  private String bookingDate;
  private int numberOfApplicants;

  @CreatedDate
  private LocalDateTime registeredAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;

  public static BookingDto formEntity(Booking booking) {
    return BookingDto.builder()
        .build();
  }
}
