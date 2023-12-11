package com.example.yummytable.dto.search;

import com.example.yummytable.domain.Board;
import com.example.yummytable.domain.Booking;
import com.example.yummytable.domain.Store;
import com.example.yummytable.dto.board.BoardDto;
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
public class SearchDto {

  private Board board;
  private Store store;
  private Booking booking;

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
        .registeredAt(board.getRegisteredAt())
        .unregisteredAt(board.getUnregisteredAt())
        .build();
  }
}
