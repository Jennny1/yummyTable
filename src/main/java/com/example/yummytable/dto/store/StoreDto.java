package com.example.yummytable.dto.store;

import com.example.yummytable.domain.Store;
import com.example.yummytable.type.Status;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {

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
  private Status storeStatus;

  @NotNull
  private int capacity;

  private LocalDateTime registeredAt;
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;

  public static StoreDto formEntity(Store store) {
    return StoreDto.builder()
        .storeId(store.getStoreId())
        .storeName(store.getStoreName())
        .keyword(store.getKeyword())
        .storeStatus(store.getStoreStatus())
        .locationX(store.getLocationX())
        .locationY(store.getLocationY())
        .menu(store.getMenu())
        .capacity(store.getCapacity())
        .registeredAt(store.getRegisteredAt())
        .updatedAt(store.getUpdatedAt())
        .unregisteredAt(store.getUnregisteredAt())
        .build();
  }
}
