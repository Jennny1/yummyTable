package com.example.yummytable.service;

import com.example.yummytable.domain.Board;
import com.example.yummytable.domain.Comment;
import com.example.yummytable.domain.Member;
import com.example.yummytable.dto.comment.CommentDto;
import com.example.yummytable.dto.comment.CreateComment;
import com.example.yummytable.dto.comment.UpdateComment;
import com.example.yummytable.dto.comment.UpdateComment.Request;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.BoardRepository;
import com.example.yummytable.repository.CommentRepository;
import com.example.yummytable.repository.MemberRepository;
import com.example.yummytable.type.ErrorCode;
import com.example.yummytable.type.Status;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

  private final CommentRepository commentRepository;
  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;

  /**
   * 댓글 생성 게시글 생성 선행 필수
   *
   * @param boardId
   * @param memberId
   * @param request
   * @return
   */
  public CommentDto createComment(Long boardId, Long memberId,
      @Valid CreateComment.Request request) {

    // 로그인 여부 확인
    validLogin(memberId);

    // 게시글 존재 확인
    Optional<Board> board = Optional.ofNullable(boardRepository.findByBoardId(boardId)
        .orElseThrow(() -> new yummyException(ErrorCode.BOARD_NOT_FOUND)));

    return CommentDto.formEntity(commentRepository.save(
        Comment.builder()
            .board(Board.builder().boardId(boardId).build())
            .member(Member.builder().memberId(memberId).build())
            .content(request.getContent())
            .commentStatus(Status.EXISTENT)
            .password(request.getPassword())
            .registeredAt(LocalDateTime.now())
            .build()
    ));
  }

  /**
   * 댓글 삭제
   *
   * @param commentId
   * @param boardId
   * @param memberId
   * @param request
   * @return
   */
  public CommentDto deleteComment(Long commentId, Long boardId, Long memberId,
      @Valid Request request) {
    // 로그인 여부 확인
    validLogin(memberId);

    Optional<Comment> comment = validCommentInfo(commentId, boardId, request);
    comment.get().setCommentStatus(Status.DELETE);
    comment.get().setUnregisteredAt(LocalDateTime.now());
    commentRepository.save(comment.get());

    return CommentDto.formEntity(comment.get());


  }

  /**
   * 댓글 수정
   *
   * @param boardId
   * @param commentId
   * @param memberId
   * @param request
   * @return
   */
  public CommentDto updateComment(Long boardId, Long commentId, Long memberId,
      @Valid UpdateComment.Request request) {

    // 로그인 여부 확인
    validLogin(memberId);

    Optional<Comment> comment = validCommentInfo(commentId, boardId, request);
    if (request.getContent().equals("")) {
      throw new yummyException(ErrorCode.CONTENT_IS_NULL);
    }

    comment.get().setContent(request.getContent());
    comment.get().setUpdatedAt(LocalDateTime.now());
    commentRepository.save(comment.get());

    return CommentDto.formEntity(comment.get());
  }


  /**
   * 댓글 보기 최신순
   * @param boardId
   * @param memberId
   * @return
   */
  public List<CommentDto> getCommentBylatest(Long boardId, Long memberId) {
    // 로그인 여부 확인
    validLogin(memberId);

    // 게시글 존재 확인
    Board board = boardRepository.findByBoardId(boardId)
        .orElseThrow(() -> new yummyException(ErrorCode.BOARD_NOT_FOUND));

    // 댓글 리스트 가져오기
    List<Comment> comments = commentRepository.findByBoardBoardIdOrderByBoardRegisteredAtAsc(
        boardId);

    return comments.stream().map(CommentDto::formEntity).collect(Collectors.toList());
  }


  private Optional<Comment> validCommentInfo(Long commentId, Long boardId,
      UpdateComment.@Valid Request request) {
    // 게시글 존재 확인
    Optional<Board> board = Optional.ofNullable(boardRepository.findByBoardId(boardId)
        .orElseThrow(() -> new yummyException(ErrorCode.BOARD_NOT_FOUND)));

    // 댓글 존재 확인
    Optional<Comment> comment = Optional.ofNullable(
        commentRepository.findByCommentIdAndCommentStatus(commentId, Status.EXISTENT)
            .orElseThrow(() -> new yummyException(ErrorCode.COMMENT_ALREADY_DELETE)));

    // 비밀번호 일치여부 확인
    if (!comment.get().getPassword().equals(request.getPassword())) {
      throw new yummyException(ErrorCode.PASSWORD_NOT_MATCH);
    }
    return comment;
  }

  private void validLogin(Long memberId) {
    // 로그인 여부 확인
    Member byMemberId = memberRepository.findByMemberIdAndMemberStatus(memberId, Status.EXISTENT)
        .orElseThrow(() -> new yummyException(ErrorCode.MEMBER_IS_NOT_EXIST));

    if (byMemberId.getToken() == null || byMemberId.getToken().isEmpty()) {
      throw new yummyException(ErrorCode.NOT_LOGGED_IN);
    }
  }
}
