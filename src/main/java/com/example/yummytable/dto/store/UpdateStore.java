package com.example.yummytable.dto.store;

import com.example.yummytable.type.Status;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;


public class UpdateStore {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    @NotNull
    private long storeId;
    @NotNull
    private long memberId;

    @NotNull
    private String storeName;
    @NotNull
    private String keyword;
    @NotNull
    private String station;

    private String menu;
    private Status storeStatus;

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
    private long memberId;

    private String storeName;
    private String station;
    private String menu;
    private Status storeStatus;

    private int capacity;

    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private LocalDateTime unregisteredAt;


    public static Response from(StoreDto storeDto) {
      return Response.builder()
          .storeId(storeDto.getStoreId())
          .memberId(storeDto.getMemberId())
          .storeName(storeDto.getStoreName())
          .storeStatus(storeDto.getStoreStatus())
          .station(storeDto.getStation())
          .menu(storeDto.getMenu())
          .capacity(storeDto.getCapacity())
          .registeredAt(storeDto.getRegisteredAt())
          .updatedAt(storeDto.getUpdatedAt())
          .unregisteredAt(storeDto.getUnregisteredAt())
          .build();
    }
  }
}
