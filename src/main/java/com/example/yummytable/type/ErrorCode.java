package com.example.yummytable.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  PASSWORD_NOT_MATCH("패스워드가 일치하지 않습니다."),
  BOARD_NOT_FOUND("게시글이 없습니다.");
  private String description;

}
