package com.example.yummytable.dto;

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

  // 식당 관련 정보
  private String storeName;
  private String keyword;

  private Double locationX;
  private Double locationY;

  private String menu;
  private int capacity;

  public static BoardDto formEntity(Board board) {
    return BoardDto.builder()
        .boardId(board.getBoardId())
        .title(board.getTitle())
        .content(board.getContent())
        .password(board.getPassword())
        .boardStatus(BoardStatus.EXISTENT)
        .registeredAt(board.getRegisteredAt())
        .storeName(board.getStoreName())
        .keyword(board.getKeyword())
        .locationX(board.getLocationX())
        .locationY(board.getLocationY())
        .menu(board.getMenu())
        .capacity(board.getCapacity())
        .build();
  }
}
