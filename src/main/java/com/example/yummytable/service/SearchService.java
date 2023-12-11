package com.example.yummytable.service;

import com.example.yummytable.domain.Board;
import com.example.yummytable.dto.board.BoardDto;
import com.example.yummytable.dto.search.SearchDto;
import com.example.yummytable.repository.BoardRepository;
import com.example.yummytable.repository.StoreRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {

  private final BoardRepository boardRepository;
  private final StoreRepository storeRepository;

  public List<BoardDto> searchByKeyword(String keyword) {
    if (!keyword.equals("")) {
      keyword = "%"+keyword+"%";
    }

    List<Board> searchByKeyword = boardRepository.findAllByKeywordLike(keyword);

    return searchByKeyword.stream().map(SearchDto::formEntity).collect(Collectors.toList());
  }
}
