package com.example.yummytable.service;

import com.example.yummytable.domain.Board;
import com.example.yummytable.dto.BoardDto;
import com.example.yummytable.dto.CreateBoard.Request;
import com.example.yummytable.repository.BoardRepository;
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
  public BoardDto createBoard(Request request) {

    boardRepository.save(boardRepository.save(Board.builder()
        .boardId(request.getBoardId())
        .title(request.getTitle())
        .content(request.getContent())
        .password(request.getPassword())
        .registeredAt(LocalDateTime.now())
        .build()));


    return
  }
}
