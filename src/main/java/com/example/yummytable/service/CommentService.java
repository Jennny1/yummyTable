package com.example.yummytable.service;

import com.example.yummytable.domain.Board;
import com.example.yummytable.domain.Comment;
import com.example.yummytable.dto.comment.CommentDto;
import com.example.yummytable.dto.comment.CreateComment;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.BoardRepository;
import com.example.yummytable.repository.CommentRepository;
import com.example.yummytable.type.CommentStatus;
import com.example.yummytable.type.ErrorCode;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

  private final CommentRepository commentRepository;
  private final BoardRepository boardRepository;

  /*댓글 생성
   * - 게시글 생성 선행 필수
   * */
  public CommentDto createComment(Long boardId, Long memberId,
      @Valid CreateComment.Request request) {
    // 게시글 존재 확인
    Optional<Board> board = Optional.ofNullable(boardRepository.findByBoardId(boardId)
        .orElseThrow(() -> new yummyException(ErrorCode.BOARD_NOT_FOUND)));

    return CommentDto.formEntity(commentRepository.save(
        Comment.builder()
            .board(Board.builder().boardId(boardId).build())
            .memberID(memberId)
            .content(request.getContent())
            .commentStatus(CommentStatus.EXISTENT)
            .password(request.getPassword())
            .registeredAt(LocalDateTime.now())
            .build()
    ));
  }
}
