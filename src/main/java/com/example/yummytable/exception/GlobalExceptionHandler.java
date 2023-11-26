package com.example.yummytable.exception;

import com.example.yummytable.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BoardException.class)
  public ErrorResponse handleBoardException(BoardException e) {
    log.error("{} is occurred.", e.getErrorCode());
    return new com.example.yummytable.dto.ErrorResponse(e.getErrorCode(), e.getErrorMessage());
  }

}
