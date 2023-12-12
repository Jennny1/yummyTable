package com.example.yummytable.dto.search;

import com.example.yummytable.domain.Board;
import com.example.yummytable.domain.Booking;
import com.example.yummytable.domain.Store;
import com.example.yummytable.dto.board.BoardDto;
import com.example.yummytable.type.Status;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
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
public class SearchDto {

  // board
  @Id
  @GeneratedValue
  private Long boardId;
  private Long storeId;
  private Long memberId;

  private String title;
  private Status boardStatus;

  @CreatedDate
  private LocalDateTime registeredAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;

  // store
  @NotNull
  private String storeName;
  @NotNull
  private String keyword;
  @NotNull
  private int capacity;
  @NotNull
  private String station;
  public static BoardDto formEntity(Board board) {
    return BoardDto.builder()
        .boardId(board.getBoardId())
        .storeId(board.getStoreId(board.getStore()))
        .storeName(board.getStoreName(board.getStore()))
        .capacity(board.getCapacity(board.getStore()))
        .station(board.getStation(board.getStore()))
        .memberId(board.getMemberId(board.getMember()))
        .title(board.getTitle())
        .content(board.getContent())
        .keyword(board.getKeyword())
        .password(board.getPassword())
        .boardStatus(board.getBoardStatus())
        .registeredAt(board.getRegisteredAt())
        .unregisteredAt(board.getUnregisteredAt())
        .build();
  }
}
