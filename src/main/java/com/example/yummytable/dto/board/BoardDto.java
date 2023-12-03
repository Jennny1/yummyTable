package com.example.yummytable.dto.board;

import com.example.yummytable.domain.Board;
import com.example.yummytable.type.BoardStatus;
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
public class BoardDto {

  // 게시글 관련 정보
  @Id
  @GeneratedValue
  private Long boardId;
  private String title;
  private String content;
  private String password;
  private BoardStatus boardStatus;

  private LocalDateTime registeredAt;
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;


  public static BoardDto formEntity(Board board) {
    return BoardDto.builder()
        .boardId(board.getBoardId())
        .title(board.getTitle())
        .content(board.getContent())
        .password(board.getPassword())
        .boardStatus(board.getBoardStatus())
        .registeredAt(board.getRegisteredAt())
        .build();
  }
}
