package com.example.yummytable.dto.search;

import com.example.yummytable.type.Status;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


public class GetSearch {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    private String station;
    private String keyword;
    private String storeName;

  }

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
    @NotNull
    private int capacity;

  }

  public static Response from(SearchDto searchDto) {
    return Response.builder()
        .boardId(searchDto.getBoardId())
        .storeId(searchDto.getStoreId())
        .memberId(searchDto.getMemberId())
        .title(searchDto.getTitle())
        .boardStatus(searchDto.getBoardStatus())
        .registeredAt(searchDto.getRegisteredAt())
        .updatedAt(searchDto.getUpdatedAt())
        .unregisteredAt(searchDto.getUnregisteredAt())
        .storeName(searchDto.getStoreName())
        .keyword(searchDto.getKeyword())
        .capacity(searchDto.getCapacity())
        .build();

  }


}
