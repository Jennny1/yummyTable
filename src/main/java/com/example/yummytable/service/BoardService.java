package com.example.yummytable.service;

import static com.example.yummytable.type.ErrorCode.BOARD_NOT_FOUND;
import static com.example.yummytable.type.ErrorCode.PASSWORD_NOT_MATCH;

import com.example.yummytable.domain.Board;
import com.example.yummytable.dto.BoardDto;
import com.example.yummytable.exception.BoardException;
import com.example.yummytable.repository.BoardRepository;
import com.example.yummytable.type.BoardStatus;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

  private final BoardRepository boardRepository;

  // 게시글 생성
  public BoardDto createBoard(Long boardId, String title, String content, BoardStatus boardStatus,
      String password, String storeName, String keyword, Double locationX, Double locationY,
      String menu, int capacity) {

    return BoardDto.formEntity(
        boardRepository.save(
            Board.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .boardStatus(boardStatus)
                .password(password)
                .registeredAt(LocalDateTime.now())
                .storeName(storeName)
                .keyword(keyword)
                .locationX(locationX)
                .locationY(locationY)
                .menu(menu)
                .capacity(capacity)
                .build())
    );
  }

  // 게시글 삭제
  public BoardDto deleteBoard(Long boardId, String password) {
    // boardId 확인
    Board board = boardRepository.findByBoardId(boardId)
        .orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));

    // password 확인
    if (!board.getPassword().equals(password)) {
      throw new BoardException(PASSWORD_NOT_MATCH);
    }

    // BoardStatus 확인
    if (!board.getBoardStatus().equals(1)) {
      throw new BoardException(BOARD_NOT_FOUND);
    }

    // BoardStatus 변경
    board.setBoardStatus(BoardStatus.DELETE);
    board.setUnregisteredAt(LocalDateTime.now());
    board.setUpdatedAt(LocalDateTime.now());

    // 저장
    boardRepository.save(board);

    return BoardDto.formEntity(board);

  }
}
