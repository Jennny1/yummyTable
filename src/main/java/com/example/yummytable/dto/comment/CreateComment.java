package com.example.yummytable.dto.comment;

import com.example.yummytable.type.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;


public class CreateComment {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    @NotNull
    private String content;
    @NotNull
    private String password;
    private Long memberId;

  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {
    private Long commentId;
    private Long boardId;
    private Long memberId;

    private String content;
    @Enumerated(EnumType.STRING)
    private Status commentStatus;

    private LocalDateTime registeredAt;

    public static Response from(CommentDto commentDto) {
      return Response.builder()
          .commentId(commentDto.getCommentId())
          .boardId(commentDto.getBoardId())
          .memberId(commentDto.getMemberId())
          .content(commentDto.getContent())
          .commentStatus(Status.EXISTENT)
          .registeredAt(commentDto.getRegisteredAt())
          .build();
    }
  }
}
