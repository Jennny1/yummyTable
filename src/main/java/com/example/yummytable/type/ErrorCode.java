package com.example.yummytable.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  PASSWORD_NOT_MATCH("패스워드가 일치하지 않습니다."),
  MEMBER_ALREADY_DELETE("이미 탈퇴한 아이디입니다."),
  CONTENT_IS_NULL("수정할 내용을 입력해주세요"),
  EMAIL_ALREADY_EXIST("이미 존재하는 이메일입니다."),
  PLEASE_INPUT_ANOTHER_PASSWORD("이전과 다른 비밀번호를 입력해주세요"),
  COMMENT_ALREADY_DELETE("이미 삭제된 댓글입니다."),
  EMAIL_NOT_MATCH("이메일을 찾을 수 없습니다."),
  BOOKING_NOT_FOUND("예약이 없습니다."),
  BOOKING_ALREADY_DELETE("이미 취소된 예약입니다."),
  FAIL_TO_DELETE_STORE("등록된 예약이 있어 상점 등록을 취소할 수 없습니다."),
  APPLICANTS_IS_DIFFERENT("예약 취소 인원이 동일하지 않습니다."),
  FAIL_TO_FIND_EMAIL("요청한 이메일을 찾을 수 없습니다."),
  END_OF_BOOKING("수용인원 초과, 예약이 불가합니다."),
  DATE_IS_BEFORE("오늘 이후 날짜만 예약 가능합니다."),
  STORE_NAME_IS_ALREADY_EXIST("상점이름이 이미 존재합니다."),
  STORE_IS_NOT_EXIST("상점이 존재하지 않습니다."),
  BOARD_NOT_FOUND("게시글이 없습니다.");
  private String description;

}
