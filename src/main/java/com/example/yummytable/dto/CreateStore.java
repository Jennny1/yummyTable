package com.example.yummytable.dto;

import com.example.yummytable.type.StoreStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;


public class CreateStore {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    @NotNull
    private long storeId;
    @NotNull
    private String storeName;
    @NotNull
    private String keyword;

    @NotNull
    private Double locationX;
    @NotNull
    private Double locationY;

    private String menu;
    private StoreStatus storeStatus;

    @NotNull
    private int capacity;
    @NotNull
    private int numberOfApplicants;

    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private LocalDateTime unregisteredAt;

  }


  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {

    private long storeId;
    private String storeName;
    private String keyword;

    private Double locationX;
    private Double locationY;

    private String menu;
    private StoreStatus storeStatus;

    private int capacity;

    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private LocalDateTime unregisteredAt;


    public static Response from(StoreDto storeDto) {
      return Response.builder()
          .storeId(storeDto.getStoreId())
          .storeName(storeDto.getStoreName())
          .keyword(storeDto.getKeyword())
          .storeStatus(storeDto.getStoreStatus())
          .locationX(storeDto.getLocationX())
          .locationY(storeDto.getLocationY())
          .menu(storeDto.getMenu())
          .capacity(storeDto.getCapacity())
          .registeredAt(storeDto.getRegisteredAt())
          .updatedAt(storeDto.getUpdatedAt())
          .unregisteredAt(storeDto.getUnregisteredAt())
          .build();
    }
  }
}
