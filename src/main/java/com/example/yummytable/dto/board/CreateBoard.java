package com.example.yummytable.dto.board;

import com.example.yummytable.type.BoardStatus;
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

    @NotNull
    private Long storeId;
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private String password;

  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {

    private Long boardId;
    private Long storeId;

    private String title;
    private String content;
    private BoardStatus boardStatus;

    private LocalDateTime registeredAt;

    public static Response from(BoardDto boardDto) {
      return Response.builder()
          .boardId(boardDto.getBoardId())
          .storeId(boardDto.getStoreId())
          .title(boardDto.getTitle())
          .content(boardDto.getContent())
          .boardStatus(BoardStatus.EXISTENT)
          .registeredAt(boardDto.getRegisteredAt())
          .build();
    }
  }
}
