package com.example.yummytable.controller;

import com.example.yummytable.dto.CreateStore;
import com.example.yummytable.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreController {

  private final StoreService storeService;

  // 상점 등록
  @PostMapping("/store")
  public CreateStore.Response createStore(@RequestBody @Valid CreateStore.Request request) {

    return CreateStore.Response.from(storeService.creatdStore(
        request.getStoreId(),
        request.getStoreName(),
        request.getKeyword(),
        request.getLocationX(),
        request.getLocationY(),
        request.getMenu(),
        request.getCapacity(),
        request.getNumberOfApplicants()));
  }
}
