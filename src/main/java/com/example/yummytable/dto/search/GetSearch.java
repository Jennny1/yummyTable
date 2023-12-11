package com.example.yummytable.dto.search;

import com.example.yummytable.type.Status;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
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


public class GetSearch {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    private String storeName;
    private String station;
    private String keyword;

    @Pattern(regexp = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])", message = "yyyy-MM-dd 형태로 입력해주세요")
    private LocalDate startDate;
    @Pattern(regexp = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])", message = "yyyy-MM-dd 형태로 입력해주세요")
    private LocalDate endDate;

  }
/*
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {

    // board
    @Id
    @GeneratedValue
    private Long boardId;
    private Long storeId;
    private Long memberId;

    private String title;
    private Status boardStatus;

    @CreatedDate
    private LocalDateTime registeredAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private LocalDateTime unregisteredAt;

    // store
    @NotNull
    private String storeName;
    @NotNull
    private String keyword;

    // booking
    @NotNull
    private int capacity;
    @NotNull
    private int numberOfApplicants;
    private LocalDate bookingDate;
  }

  public static Response from(SearchDto searchDto) {
    return Response.builder()
        .boardId(searchDto.getBoard().getBoardId())
        .storeId(searchDto.getStore().getStoreId())
        .memberId(searchDto.getBoard().getMember().getMemberId())
        .title(searchDto.getBoard().getTitle())
        .boardStatus(searchDto.getBoard().getBoardStatus())
        .registeredAt(searchDto.getBoard().getRegisteredAt())
        .updatedAt(searchDto.getBoard().getUpdatedAt())
        .unregisteredAt(searchDto.getBoard().getUnregisteredAt())
        .storeName(searchDto.getStore().getStoreName())
        .capacity(searchDto.getStore().getCapacity())
        .numberOfApplicants(searchDto.getStore().getNumberOfApplicants())
        .bookingDate(searchDto.getBooking().getBookingDate())
        .build();

  }*/


}
