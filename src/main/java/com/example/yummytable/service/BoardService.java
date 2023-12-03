package com.example.yummytable.service;

import static com.example.yummytable.type.ErrorCode.BOARD_NOT_FOUND;
import static com.example.yummytable.type.ErrorCode.PASSWORD_NOT_MATCH;

import com.example.yummytable.domain.Board;
import com.example.yummytable.domain.Store;
import com.example.yummytable.dto.board.BoardDto;
import com.example.yummytable.dto.board.CreateBoard.Request;
import com.example.yummytable.dto.board.DeleteBoard;
import com.example.yummytable.dto.board.UpdateBoard;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.BoardRepository;
import com.example.yummytable.repository.StoreRepository;
import com.example.yummytable.type.BoardStatus;
import com.example.yummytable.type.ErrorCode;
import com.example.yummytable.type.StoreStatus;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

  private final BoardRepository boardRepository;
  private final StoreRepository storeRepository;

  /*
  게시글 생성
  - 상점 등록 선행 필수
  */
  public BoardDto createBoard(@Valid Request request) {

    // 상점 존재 확인
    Store store = storeRepository.findByStoreIdAndStoreStatus(request.getStoreId(),
            StoreStatus.EXISTENT)
        .orElseThrow(() -> new yummyException(ErrorCode.STORE_IS_NOT_EXIST));

    return BoardDto.formEntity(
        boardRepository.save(
            Board.builder()
                .store(Store.builder()
                    .storeId(store.getStoreId()).build())
                .title(request.getTitle())
                .content(request.getContent())
                .boardStatus(BoardStatus.EXISTENT)
                .password(request.getPassword())
                .registeredAt(LocalDateTime.now())
                .build())
    );
  }


  /*게시글 삭제*/
  public BoardDto deleteBoard(DeleteBoard.Request request) {
    Board board = validBoardInfo(request.getBoardId(), request.getPassword());

    // BoardStatus 변경
    board.setBoardStatus(BoardStatus.DELETE);
    board.setUnregisteredAt(LocalDateTime.now());

    // 저장
    boardRepository.save(board);
    return BoardDto.formEntity(board);
  }


  /*게시글 수정*/
  public BoardDto updateBoard(Long boardId, @Valid UpdateBoard.Request request) {
    Optional<Store> store = storeRepository.findByStoreId(request.getStoreId());

    Board board = validBoardInfo(boardId, request.getPassword());

    // 수정 반영
    if (request.getStoreId() != null && board.getStore().getStoreId() != request.getStoreId()) {
      board.setStore(Store.builder().storeId(store.get().getStoreId()).build());
    }

    if (!request.getTitle().equals("") && !board.getTitle().equals(request.getTitle())) {
      board.setTitle(request.getTitle());
    }

    if (!request.getContent().equals("") && !board.getContent().equals(request.getContent())) {
      board.setContent(request.getContent());
    }

    if (!request.getPassword().equals("") && !board.getPassword().equals(request.getPassword())) {
      board.setPassword(request.getPassword());
    }

    board.setUpdatedAt(LocalDateTime.now());

    // 저장
    boardRepository.save(board);
    return BoardDto.formEntity(board);
  }


  /*게시글 읽기*/
  public BoardDto getBoard(Long boardId) {
    Board board = validBoardId(boardId);

    // board 넘기기
    return BoardDto.formEntity(board);
  }


  private Board validBoardId(Long boardId) {
    // boardId 확인
    Board board = boardRepository.findByBoardId(boardId)
        .orElseThrow(() -> new yummyException(BOARD_NOT_FOUND));

    // BoardStatus 확인
    if (board.getBoardStatus().equals(BoardStatus.DELETE)) {
      throw new yummyException(BOARD_NOT_FOUND);
    }
    return board;
  }


  private Board validBoardInfo (Long boardId, String password) {
    // boardId 확인
    Board board = boardRepository.findByBoardId(boardId)
        .orElseThrow(() -> new yummyException(BOARD_NOT_FOUND));

    // password 확인
    if (!board.getPassword().equals(password)) {
      throw new yummyException(PASSWORD_NOT_MATCH);
    }

    // BoardStatus 확인
    if (board.getBoardStatus().equals(BoardStatus.DELETE)) {
      throw new yummyException(BOARD_NOT_FOUND);
    }
    return board;
  }
}
