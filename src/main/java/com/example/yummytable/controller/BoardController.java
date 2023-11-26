package com.example.yummytable.controller;

import com.example.yummytable.dto.CreateBoard;
import com.example.yummytable.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @PostMapping("/board")
  public CreateBoard.Response creatBoard(@RequestBody @Valid CreateBoard.Request request) {

    return CreateBoard.Response.from(
        boardService.createBoard(
            request.getBoardId(),
            request.getTitle(),
            request.getContent(),
            request.getPassword()));
  }
}
