package com.example.yummytable.controller;

import com.example.yummytable.dto.favorit.CreateFavorit;
import com.example.yummytable.dto.favorit.DeleteFavorit;
import com.example.yummytable.service.FavoritService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FavoritController {

  private final FavoritService favoritService;

  /*좋아요 등록*/
  @PostMapping("favorit/{storeId}/{memberId}")
  public CreateFavorit.Response createFavorit(@PathVariable Long storeId, @PathVariable Long memberId) {

    return CreateFavorit.Response.from(favoritService.createFavorit(storeId, memberId));
  }


  /*좋아요 취소*/
  @DeleteMapping("favorit/{storeId}/{memberId}")
  public DeleteFavorit.Response deleteFavorit(@PathVariable Long storeId, @PathVariable Long memberId) {

    return DeleteFavorit.Response.from(favoritService.deleteFavorit(storeId, memberId));
  }

  /*좋아요 리스트 보기*/
}
