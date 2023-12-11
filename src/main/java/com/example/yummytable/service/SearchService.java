package com.example.yummytable.service;

import com.example.yummytable.dto.board.BoardDto;
import com.example.yummytable.repository.BoardRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {

  private final BoardRepository boardRepository;



}
