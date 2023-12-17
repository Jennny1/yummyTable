package com.example.yummytable.controller;

import com.example.yummytable.dto.favorit.CreateFavorit;
import com.example.yummytable.dto.favorit.DeleteFavorit;
import com.example.yummytable.dto.favorit.GetFavorit.Response;
import com.example.yummytable.service.FavoritService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
public class FavoritController {

  private final FavoritService favoritService;

  /*좋아요 등록*/
  @PostMapping("/favorits")
  public CreateFavorit.Response createFavorit(
      @RequestParam Long storeId,
      @RequestParam Long memberId) {

    return CreateFavorit.Response.from(favoritService.createFavorit(storeId, memberId));
  }


  /*좋아요 취소*/
  @DeleteMapping("/favorits")
  public DeleteFavorit.Response deleteFavorit(
      @RequestParam Long storeId,
      @RequestParam Long memberId) {

    return DeleteFavorit.Response.from(favoritService.deleteFavorit(storeId, memberId));
  }

  /*좋아요 리스트 보기*/
  @GetMapping("/favorits")
  public List<Response> getFavorit(@RequestParam Long storeId, @RequestParam Long memberId) {

    return favoritService.getFavofit(storeId, memberId).stream()
        .map(FavoritDto -> Response.builder()
            .favoritId(FavoritDto.getFavoritId())
            .memberId(FavoritDto.getMemberId())
            .favoritStatus(FavoritDto.getFavoritStatus())
            .registeredAt(FavoritDto.getRegisteredAt())
            .build()).collect(Collectors.toList());
  }
}
