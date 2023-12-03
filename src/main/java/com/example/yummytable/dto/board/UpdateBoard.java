package com.example.yummytable.dto.board;

import com.example.yummytable.dto.board.BoardDto;
import com.example.yummytable.type.BoardStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class UpdateBoard {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    // 게시글 관련 정보
    private String title;
    private String content;

    @NotNull(message = "게시글 비밀번호를 입력하세요")
    private String password;
    private BoardStatus boardStatus;

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
    private BoardStatus boardStatus;
    private LocalDateTime registeredAt;


    public static Response from(BoardDto boardDto) {
      return Response.builder()
          .boardId(boardDto.getBoardId())
          .title(boardDto.getTitle())
          .content(boardDto.getContent())
          .boardStatus(BoardStatus.EXISTENT)
          .registeredAt(boardDto.getRegisteredAt())
          .build();
    }
  }
}
