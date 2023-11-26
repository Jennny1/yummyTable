package com.example.yummytable.dto;

import com.example.yummytable.domain.StoreInfo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;


public class CreateBoard {
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    // 게시글 관련 정보
    @NotNull
    private Long boardId;
    @NotNull
    private String title;
    @NotNull

    private String content;
    @NotNull
    private String password;

    // 식당 관련 정보
    private StoreInfo storeInfo;

  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {

    // 게시글 관련 정보
    private Long boardId;
    private String title;
    private String content;

    private LocalDateTime registeredAt;

    // 식당 관련 정보
    StoreInfo storeInfo;

    public static Response from(BoardDto boardDto) {
      return Response.builder()
          .boardId(boardDto.getBoardId())
          .title(boardDto.getTitle())
          .content(boardDto.getContent())
          .registeredAt(boardDto.getRegisteredAt())
          .build();
    }
  }
}
