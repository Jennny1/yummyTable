package com.example.yummytable.controller;

import com.example.yummytable.dto.BoardDto;
import com.example.yummytable.dto.CreateBoard;
import com.example.yummytable.dto.DeleteBoard;
import com.example.yummytable.dto.ReadBoard;
import com.example.yummytable.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  /*
  게시글 입력
   */
  @PostMapping("/board")
  public CreateBoard.Response creatBoard(@RequestBody @Valid CreateBoard.Request request) {

    return CreateBoard.Response.from(
        boardService.createBoard(
            request.getBoardId(),
            request.getTitle(),
            request.getContent(),
            request.getPassword(),
            request.getStoreName(),
            request.getKeyword(),
            request.getLocationX(),
            request.getLocationY(),
            request.getMenu(),
            request.getCapacity())
    );
  }

  /*
  게시글 읽기
   */
  @GetMapping("/board/{boardId}")
  public ReadBoard.Response getBoard(@PathVariable Long boardId) {

    BoardDto board = boardService.getBoard(boardId);

    return ReadBoard.Response.from(board);
  }


  /*
  게시글 삭제
   */
  @DeleteMapping("/board")
  public DeleteBoard.Response deleteBoard(@RequestBody @Valid DeleteBoard.Request request) {

    return DeleteBoard.Response.from(
        boardService.deleteBoard(
            request.getBoardId(),
            request.getPassword())
    );
  }
}
