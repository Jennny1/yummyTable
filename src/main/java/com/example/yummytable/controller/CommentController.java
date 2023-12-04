package com.example.yummytable.controller;

import com.example.yummytable.dto.comment.CreateComment;
import com.example.yummytable.dto.comment.DeleteComment;
import com.example.yummytable.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  /*댓글 입력*/
  @PostMapping("/comment/{boardId}/{memberId}")
  public CreateComment.Response createComment(
      @PathVariable Long boardId, @PathVariable Long memberId,
      @RequestBody @Valid CreateComment.Request request) {

    return CreateComment.Response.from(commentService.createComment(boardId, memberId, request));
  }


  /*댓글 삭제*/
  @DeleteMapping("/comment/{boardId}/{commentId}")
  public DeleteComment.Response deleteComment(
      @PathVariable Long boardId, @PathVariable Long commentId,
      @RequestBody @Valid DeleteComment.Request request) {

    return DeleteComment.Response.from(commentService.deleteComment(boardId, commentId, request));
  }


  /*댓글 수정*/
  /*댓글 보기*/

}
