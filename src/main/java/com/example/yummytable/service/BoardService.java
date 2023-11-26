package com.example.yummytable.service;

import com.example.yummytable.domain.Board;
import com.example.yummytable.dto.BoardDto;
import com.example.yummytable.dto.CreateBoard.Response;
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
  public BoardDto createBoard(Long boardId, String title, String content, String password) {
    // 게시글 저장
    return BoardDto.formEntity(
        boardRepository.save(
            Board.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .password(password)
                .registeredAt(LocalDateTime.now())
                .build())
    );
  }
}
