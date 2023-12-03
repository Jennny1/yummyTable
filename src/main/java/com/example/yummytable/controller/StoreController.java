package com.example.yummytable.controller;

import com.example.yummytable.dto.store.CreateStore;
import com.example.yummytable.dto.store.DeleteStore;
import com.example.yummytable.dto.store.StoreDto;
import com.example.yummytable.dto.store.UpdateStore;
import com.example.yummytable.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreController {

  private final StoreService storeService;

  /*상점 등록*/
  @PostMapping("/store")
  public CreateStore.Response createStore(@RequestBody @Valid CreateStore.Request request) {

    return CreateStore.Response.from(storeService.createStore(
        request.getStoreId(),
        request.getStoreName(),
        request.getKeyword(),
        request.getLocationX(),
        request.getLocationY(),
        request.getMenu(),
        request.getCapacity(),
        request.getNumberOfApplicants()));
  }

  /*상점 삭제*/
  @DeleteMapping("/store/{storeId}")
  public DeleteStore.Response deleteStore(@PathVariable Long storeId) {

    return DeleteStore.Response.from(storeService.deleteStore(storeId));
  }

  /*상점 수정*/
  @PatchMapping("/store")
  public UpdateStore.Response updateStore(@RequestBody @Valid UpdateStore.Request request) {

    StoreDto storeDto = storeService.updateStore(request);

    return null;
  }


}
