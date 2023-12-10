package com.example.yummytable.dto.comment;

import com.example.yummytable.domain.Comment;
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
public class CommentDto {

  @Id
  @GeneratedValue
  private Long commentId;
  private Long boardId;
  private Long memberId;

  private String content;
  private String password;
  private Status commentStatus;

  private LocalDateTime registeredAt;
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;


  public static CommentDto formEntity(Comment comment) {
    return CommentDto.builder()
        .commentId(comment.getCommentId())
        .boardId(comment.getBoard().getBoardId())
        .memberId(comment.getMember().getMemberId())
        .content(comment.getContent())
        .password(comment.getPassword())
        .registeredAt(comment.getRegisteredAt())
        .updatedAt(comment.getUpdatedAt())
        .unregisteredAt(comment.getUnregisteredAt())
        .build();
  }
}
