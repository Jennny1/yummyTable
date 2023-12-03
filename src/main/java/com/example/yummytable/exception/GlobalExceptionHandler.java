package com.example.yummytable.exception;

import com.example.yummytable.dto.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(yummyException.class)
  public ErrorResponse handleBoardException(yummyException e) {
    log.error("{} is occurred.", e.getErrorCode());
    return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
  }

}
