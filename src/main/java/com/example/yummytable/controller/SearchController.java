package com.example.yummytable.controller;

import com.example.yummytable.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

  private final SearchService searchService;

  // 최신순
  @GetMapping("/searches")
  public void searchBoardByLatest() {


  }
}
