package com.example.yummytable.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  PASSWORD_NOT_MATCH("패스워드가 일치하지 않습니다."),
  DATE_IS_BEFORE("오늘 이후 날짜만 예약 가능합니다."),
  STORE_NAME_IS_ALREADY_EXIST("상점이름이 이미 존재합니다."),
  STORE_IS_NOT_EXIST("상점이 존재하지 않습니다."),
  BOARD_NOT_FOUND("게시글이 없습니다.");
  private String description;

}
