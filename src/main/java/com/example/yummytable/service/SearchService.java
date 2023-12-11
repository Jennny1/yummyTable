package com.example.yummytable.service;

import com.example.yummytable.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {

  private final BoardRepository boardRepository;

  // 최신순
/*  public List<BoardDto> searchBoardByLatest() {


  }*/

}
