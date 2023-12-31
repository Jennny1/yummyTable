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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  /*댓글 입력*/
  @PostMapping("/comment")
  public CreateComment.Response createComment(
      @RequestParam Long boardId, @RequestParam Long memberId,
      @RequestBody @Valid CreateComment.Request request) {

    return CreateComment.Response.from(commentService.createComment(boardId, memberId, request));
  }


  /*댓글 삭제*/
  @DeleteMapping("/comment")
  public DeleteComment.Response deleteComment(
      @RequestParam Long boardId,
      @RequestParam Long commentId,
      @RequestParam Long memberId,
      @RequestBody @Valid Request request) {

    return DeleteComment.Response.from(
        commentService.deleteComment(boardId, commentId, memberId, request));
  }


  /*댓글 수정*/
  @PatchMapping("/comment")
  public UpdateComment.Response updateComment(
      @RequestParam Long boardId,
      @RequestParam Long commentId,
      @RequestParam Long memberId,
      @RequestBody @Valid UpdateComment.Request request) {

    return UpdateComment.Response.from(commentService.updateComment(boardId, commentId, memberId, request));
  }


  /*댓글 보기*/
  @GetMapping("/comment")
  public List<Response> getCommentBylatest(@RequestParam Long boardId, @RequestParam Long memberId) {

    return commentService.getCommentBylatest(boardId, memberId).stream()
        .map(CommentDto -> GetComment.Response.builder()
            .commentId(CommentDto.getCommentId())
            .memberID(CommentDto.getMemberId())
            .commentStatus(CommentDto.getCommentStatus())
            .content(CommentDto.getContent())
            .registeredAt(CommentDto.getRegisteredAt()).build())
        .collect(Collectors.toList());
  }


}
