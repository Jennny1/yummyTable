package com.example.yummytable.dto.comment;

import com.example.yummytable.type.CommentStatus;
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

  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {
    private Long commentId;
    private Long boardId;
    private Long memberID;

    private String content;
    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;

    private LocalDateTime registeredAt;

    public static Response from(CommentDto commentDto) {
      return Response.builder()
          .commentId(commentDto.getCommentId())
          .boardId(commentDto.getBoardId())
          .memberID(commentDto.getMemberId())
          .content(commentDto.getContent())
          .commentStatus(CommentStatus.EXISTENT)
          .registeredAt(commentDto.getRegisteredAt())
          .build();
    }
  }
}
