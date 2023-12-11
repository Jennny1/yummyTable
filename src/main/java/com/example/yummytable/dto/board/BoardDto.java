package com.example.yummytable.dto.board;

import com.example.yummytable.domain.Board;
import com.example.yummytable.domain.Store;
import com.example.yummytable.type.Status;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {

  @Id
  @GeneratedValue
  private Long boardId;
  private Long storeId;
  private Long memberId;

  private String title;
  private String content;
  private String keyword;
  private String password;
  private Status boardStatus;

  private Store storeName;

  @CreatedDate
  private LocalDateTime registeredAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;


  public static BoardDto formEntity(Board board) {
    return BoardDto.builder()
        .boardId(board.getBoardId())
        .storeId(board.getStore().getStoreId())
        .memberId(board.getMember().getMemberId())

        .title(board.getTitle())
        .content(board.getContent())
        .keyword(board.getKeyword())
        .password(board.getPassword())
        .boardStatus(board.getBoardStatus())
        .storeName(Store.builder().storeName(board.getStore().getStoreName()).build())

        .registeredAt(board.getRegisteredAt())
        .unregisteredAt(board.getUnregisteredAt())
        .build();
  }
}
