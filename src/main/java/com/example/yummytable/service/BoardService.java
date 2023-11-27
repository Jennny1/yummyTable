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
  public BoardDto createBoard(Long boardId, String title, String content,
      String password, String storeName, String keyword, Double locationX, Double locationY,
      String menu, int capacity) {

    return BoardDto.formEntity(
        boardRepository.save(
            Board.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .boardStatus(BoardStatus.EXISTENT)
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

  // 게시글 읽기
  public BoardDto getBoard(Long boardId) {
    // boardId 확인
    Board board = validBoardId(boardId);

    // BoardStatus 확인
    if (board.getBoardStatus().equals(BoardStatus.DELETE)) {
      throw new BoardException(BOARD_NOT_FOUND);
    }

    // board 넘기기
    return BoardDto.formEntity(board);
  }


  // 게시글 수정
  public BoardDto updateBoard(Long boardId, String title, String content, String password,
      BoardStatus boardStatus, String storeName, String keyword,
      Double locationX, Double locationY, String menu, int capacity) {

    // boardId 확인
    Board board = validBoardId(boardId);

    // BoardStatus 확인
    if (board.getBoardStatus().equals(BoardStatus.DELETE)) {
      throw new BoardException(BOARD_NOT_FOUND);
    }

    // 비밀번호 확인
    if (!board.getPassword().equals(password)) {
      throw new BoardException(PASSWORD_NOT_MATCH);
    }

    // 수정 반영
    if (!board.getTitle().equals(title)) {
      board.setTitle(title);
    }

    if (!board.getContent().equals(content)) {
      board.setContent(content);
    }

    if (!board.getStoreName().equals(storeName)) {
      board.setStoreName(storeName);
    }

    if (!board.getKeyword().equals(keyword)) {
      board.setKeyword(keyword);
    }

    if (!board.getLocationX().equals(locationX)) {
      board.setLocationX(locationX);
    }

    if (!board.getLocationY().equals(locationY)) {
      board.setLocationY(locationY);
    }

    if (!board.getMenu().equals(menu)) {
      board.setMenu(menu);
    }

    if (board.getCapacity() != capacity) {
      board.setCapacity(capacity);
    }

    board.setUpdatedAt(LocalDateTime.now());

    // 저장
    boardRepository.save(board);
    return BoardDto.formEntity(board);
  }


  // 게시글 삭제
  public BoardDto deleteBoard(Long boardId, String password) {
    // boardId 확인
    Board board = validBoardId(boardId);

    // password 확인
    if (!board.getPassword().equals(password)) {
      throw new BoardException(PASSWORD_NOT_MATCH);
    }

    // BoardStatus 확인
    if (board.getBoardStatus().equals(1)) {
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


  private Board validBoardId(Long boardId) {

    Board board = boardRepository.findByBoardId(boardId)
        .orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));

    return board;
  }


}
