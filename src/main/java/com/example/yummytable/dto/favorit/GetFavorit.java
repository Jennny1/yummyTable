package com.example.yummytable.dto.favorit;

import com.example.yummytable.type.Status;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class GetFavorit {


  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {

    private long favoritId;
    private long memberId;

    private Status favoritStatus;

    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private LocalDateTime unregisteredAt;

    public static Response from(FavoritDto favoritDto) {
      return Response.builder()
          .favoritId(favoritDto.getFavoritId())
          .memberId(favoritDto.getMemberId())
          .favoritStatus(favoritDto.getFavoritStatus())
          .registeredAt(favoritDto.getRegisteredAt())
          .unregisteredAt(favoritDto.getUnregisteredAt())
          .updatedAt(favoritDto.getUpdatedAt())
          .build();
    }
  }
}
