package com.example.yummytable.dto.favorit;

import com.example.yummytable.domain.Favorit;
import com.example.yummytable.domain.Store;
import com.example.yummytable.type.Status;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoritDto {

  @Id
  @GeneratedValue
  private long favoritId;
  private long storeId;
  private long memberId;

  private Status favoritStatus;
  private Store store;

  private LocalDateTime registeredAt;
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;

  public static FavoritDto formEntity(Favorit favorit) {
    return FavoritDto.builder()
        .favoritId(favorit.getFavoritId())
        .favoritStatus(favorit.getFavoritStatus())
        .registeredAt(favorit.getRegisteredAt())
        .updatedAt(favorit.getUpdatedAt())
        .unregisteredAt(favorit.getUnregisteredAt())
        .build();
  }
}
