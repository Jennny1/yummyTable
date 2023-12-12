package com.example.yummytable.service;

import com.example.yummytable.domain.Board;
import com.example.yummytable.dto.board.BoardDto;
import com.example.yummytable.dto.search.SearchDto;
import com.example.yummytable.exception.yummyException;
import com.example.yummytable.repository.BoardRepository;
import com.example.yummytable.repository.StoreRepository;
import com.example.yummytable.type.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {

  private final BoardRepository boardRepository;
  private final StoreRepository storeRepository;

  /*키워드 검색*/
  public List<BoardDto> searchByKeyword(String keyword) {
    if (!keyword.equals("")) {
      keyword = "%"+keyword+"%";
    } else {
      throw new yummyException(ErrorCode.SEARCH_INFO_IS_NULL_KEYWORD);
    }

    List<Board> searchByKeywords = boardRepository.findAllByKeywordLike(keyword);

    return searchByKeywords.stream().map(SearchDto::formEntity).collect(Collectors.toList());
  }

  /*상점 이름 검색*/
  public List<BoardDto> searchByStoreName(String storeName) {
    if (!storeName.equals("")) {
      storeName = "%"+storeName+"%";
    } else {
      throw new yummyException(ErrorCode.SEARCH_INFO_IS_NULL_STORENAME);
    }

    List<Board> searchByStoreNames = boardRepository.findAllByStoreStoreNameLike(storeName);

    return searchByStoreNames.stream().map(SearchDto::formEntity).collect(Collectors.toList());

  }

  /*상점 이름 검색*/
  public List<BoardDto> searchByStoreName(String storeName) {
    if (!storeName.equals("")) {
      storeName = "%"+storeName+"%";
    } else {
      throw new yummyException(ErrorCode.SEARCH_INFO_IS_NULL_STORENAME);
    }

    List<Board> searchByStoreNames = boardRepository.findAllByStoreStoreNameLike(storeName);

    return searchByStoreNames.stream().map(SearchDto::formEntity).collect(Collectors.toList());

  }


}
