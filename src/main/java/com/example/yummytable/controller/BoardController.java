package com.example.yummytable.controller;

import com.example.yummytable.dto.board.CreateBoard;
import com.example.yummytable.dto.board.DeleteBoard;
import com.example.yummytable.dto.board.GetBoard;
import com.example.yummytable.dto.board.UpdateBoard;
import com.example.yummytable.dto.board.UpdateBoard.Response;
import com.example.yummytable.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  /*게시글 입력*/
  @PostMapping("/boards")
  public CreateBoard.Response creatBoard(
      @RequestParam Long memberId,
      @RequestBody @Valid CreateBoard.Request request) {

    return CreateBoard.Response.from(boardService.createBoard(memberId, request));
  }


  /*게시글 삭제*/
  @DeleteMapping("/boards")
  public DeleteBoard.Response deleteBoard(
      @RequestParam Long boardId,
      @RequestParam Long memberId,
      @RequestBody @Valid DeleteBoard.Request request) {

    return DeleteBoard.Response.from(
        boardService.deleteBoard(boardId, memberId, request)
    );
  }


  /*게시글 수정*/
  @PatchMapping("/boards")
  public Response updateBoard(
      @RequestParam Long boardId,
      @RequestParam Long memberId,
      @RequestBody @Valid UpdateBoard.Request request) {

    return UpdateBoard.Response.from(boardService.updateBoard(boardId, memberId, request));
  }


  /*게시글 읽기*/
  @GetMapping("/boards")
  public GetBoard.Response getBoard(@RequestParam Long boardId) {

    return GetBoard.Response.from(boardService.getBoard(boardId));
  }
}
