package com.example.yummytable.dto.search;

import com.example.yummytable.domain.Board;
import com.example.yummytable.domain.Booking;
import com.example.yummytable.domain.Store;
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

}
