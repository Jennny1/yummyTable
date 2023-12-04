package com.example.yummytable.controller;

import com.example.yummytable.dto.comment.CreateComment;
import com.example.yummytable.dto.comment.DeleteComment;
import com.example.yummytable.dto.comment.GetComment;
import com.example.yummytable.dto.comment.GetComment.Response;
import com.example.yummytable.dto.comment.UpdateComment;
import com.example.yummytable.dto.comment.UpdateComment.Request;
import com.example.yummytable.service.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
      @RequestBody @Valid Request request) {

    return DeleteComment.Response.from(commentService.deleteComment(boardId, commentId, request));
  }


  /*댓글 수정*/
  @PatchMapping("/comment/{boardId}/{commentId}")
  public UpdateComment.Response updateComment(
      @PathVariable Long boardId, @PathVariable Long commentId,
      @RequestBody @Valid UpdateComment.Request request) {

    return UpdateComment.Response.from(commentService.updateComment(boardId, commentId, request));
  }


  /*댓글 보기*/
  @GetMapping("/comment/{boardId}")
  public List<Response> getCommentBylatest(@PathVariable Long boardId) {

    return commentService.getCommentBylatest(boardId).stream()
        .map(CommentDto -> GetComment.Response.builder()
            .commentId(CommentDto.getCommentId())
            .memberID(CommentDto.getMemberId())
            .commentStatus(CommentDto.getCommentStatus())
            .content(CommentDto.getContent())
            .registeredAt(CommentDto.getRegisteredAt()).build())
        .collect(Collectors.toList());
  }


}