package com.example.yummytable.service;

import static com.example.yummytable.type.ErrorCode.BOARD_NOT_FOUND;
import static com.example.yummytable.type.ErrorCode.PASSWORD_NOT_MATCH;

import com.example.yummytable.domain.Board;
import com.example.yummytable.domain.Member;
import com.example.yummytable.domain.Store;
import com.example.yummytable.dto.board.BoardDto;
import com.example.yummytable.dto.board.CreateBoard.Request;
import com.example.yummytable.dto.board.DeleteBoard;
import com.example.yummytable.dto.board.UpdateBoard;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.BoardRepository;
import com.example.yummytable.repository.MemberRepository;
import com.example.yummytable.repository.StoreRepository;
import com.example.yummytable.type.ErrorCode;
import com.example.yummytable.type.Status;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

  private final BoardRepository boardRepository;
  private final StoreRepository storeRepository;
  private final MemberRepository memberRepository;
  @Autowired
  BCryptPasswordEncoder encoder;


  /**
   * 게시글 생성 상점 등록 선행 필수
   *
   * @param memberId
   * @param request
   * @return
   */
  public BoardDto createBoard(Long memberId, @Valid Request request) {
    // 멤버 확인
    Member member = memberRepository.findByMemberId(memberId)
        .orElseThrow(() -> new yummyException(ErrorCode.MEMBER_IS_NOT_EXIST));

    // 로그인 확인
    if (member.getToken() == null || member.getToken().isEmpty()) {
      throw new yummyException(ErrorCode.NOT_LOGGED_IN);
    }

    // 상점 존재 확인
    Store store = storeRepository.
        findByStoreIdAndStoreStatus(request.getStoreId(), Status.EXISTENT)
        .orElseThrow(() -> new yummyException(ErrorCode.STORE_IS_NOT_EXIST));

    return BoardDto.formEntity(
        boardRepository.save(
            Board.builder()
                .store(Store.builder()
                    .storeId(store.getStoreId())
                    .storeName(store.getStoreName())
                    .capacity(store.getCapacity())
                    .station(store.getStation())
                    .build())
                .member(Member.builder().memberId(memberId).build())
                .title(request.getTitle())
                .content(request.getContent())
                .keyword(request.getKeyword())
                .boardStatus(Status.EXISTENT)
                .password(encoder.encode(request.getPassword()))
                .registeredAt(LocalDateTime.now())
                .build())
    );
  }


  /**
   * 게시글 삭제
   *
   * @param boardId
   * @param memberId
   * @param request
   * @return
   */
  public BoardDto deleteBoard(Long boardId, Long memberId, DeleteBoard.Request request) {
    Board board = validBoardInfo(boardId, request.getPassword());


    // 작성자 확인
    if (memberId != board.getMemberId(board.getMember())) {
      throw new yummyException(ErrorCode.MEMBER_NOT_MATCH);
    }

    Member byMemberId = memberRepository.findByMemberIdAndMemberStatus(
        memberId, Status.EXISTENT).orElseThrow(() -> new yummyException(ErrorCode.MEMBER_IS_NOT_EXIST));

    // 로그인 확인
    if (byMemberId.getToken() == null || byMemberId.getToken().isEmpty()) {
      throw new yummyException(ErrorCode.NOT_LOGGED_IN);
    }

    // BoardStatus 변경
    board.setBoardStatus(Status.DELETE);
    board.setUnregisteredAt(LocalDateTime.now());

    // 저장
    boardRepository.save(board);
    return BoardDto.formEntity(board);
  }


  /**
   * 게시글 수정
   *
   * @param boardId
   * @param memberId
   * @param request
   * @return
   */
  public BoardDto updateBoard(Long boardId, Long memberId, @Valid UpdateBoard.Request request) {
    Optional<Store> store = storeRepository.findByStoreId(request.getStoreId());

    Board board = validBoardInfo(boardId, request.getPassword());

    // 작성자 확인
    if (memberId != board.getMemberId(board.getMember())) {
      throw new yummyException(ErrorCode.MEMBER_NOT_MATCH);
    }

    Member byMemberId = memberRepository.findByMemberIdAndMemberStatus(
        memberId, Status.EXISTENT).orElseThrow(() -> new yummyException(ErrorCode.MEMBER_IS_NOT_EXIST));

    // 로그인 확인
    if (byMemberId.getToken() == null || byMemberId.getToken().isEmpty()) {
      throw new yummyException(ErrorCode.NOT_LOGGED_IN);
    }

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

    if (!request.getKeyword().equals("") && !board.getKeyword().equals(request.getKeyword())) {
      board.setKeyword(request.getKeyword());
    }

    if (!request.getPassword().equals("") && !board.getPassword().equals(request.getPassword())) {
      board.setPassword(request.getPassword());
    }

    board.setUpdatedAt(LocalDateTime.now());

    // 저장
    boardRepository.save(board);
    return BoardDto.formEntity(board);
  }


  /**
   * 게시글 읽기
   *
   * @param boardId
   * @return
   */
  /**/
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
    if (board.getBoardStatus().equals(Status.DELETE)) {
      throw new yummyException(BOARD_NOT_FOUND);
    }
    return board;
  }


  private Board validBoardInfo(Long boardId, String password) {
    // boardId 확인
    Board board = boardRepository.findByBoardId(boardId)
        .orElseThrow(() -> new yummyException(BOARD_NOT_FOUND));

    // password 확인
    if (!encoder.matches(password, board.getPassword())) {
      throw new yummyException(PASSWORD_NOT_MATCH);
    }

    // BoardStatus 확인
    if (board.getBoardStatus().equals(Status.DELETE)) {
      throw new yummyException(BOARD_NOT_FOUND);
    }
    return board;
  }
}
