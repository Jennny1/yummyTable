package com.example.yummytable.dto.board;

import com.example.yummytable.type.Status;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class GetBoard {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {

    // 게시글 관련 정보
    private Long boardId;
    private Long memberId;

    private String title;
    private String content;
    private Status boardStatus;
    private LocalDateTime registeredAt;

    public static Response from(BoardDto boardDto) {
      return Response.builder()
          .boardId(boardDto.getBoardId())
          .memberId(boardDto.getMemberId())
          .title(boardDto.getTitle())
          .content(boardDto.getContent())
          .boardStatus(Status.EXISTENT)
          .registeredAt(boardDto.getRegisteredAt())
          .build();
    }
  }
}
