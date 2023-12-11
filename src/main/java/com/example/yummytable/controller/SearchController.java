package com.example.yummytable.controller;

import com.example.yummytable.dto.search.GetSearch.Response;
import com.example.yummytable.service.SearchService;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

  private final SearchService searchService;

  // 최신순
  @GetMapping("/searches")
  public List<Response> searchByKeyword(
      @RequestParam(value = "keyword", required = false) String keyword,
      @RequestParam @Positive int page,
      @RequestParam @Positive int size) {


    return searchService.searchByKeyword(keyword).stream()
        .map(searchDto -> Response.builder()
            .boardId(searchDto.getBoardId())
            .storeId(searchDto.getStoreId())
            .memberId(searchDto.getMemberId())
/*            .storeName(searchDto.getStoreName())
            .capacity(searchDto.getCapacity())*/
            .title(searchDto.getTitle())
            .boardStatus(searchDto.getBoardStatus())
            .keyword(searchDto.getKeyword())
            .registeredAt(searchDto.getRegisteredAt())
            .build()).collect(Collectors.toList());
  }
}
