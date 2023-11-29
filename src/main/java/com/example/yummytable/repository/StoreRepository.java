package com.example.yummytable.repository;

import com.example.yummytable.domain.Store;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

  List<Store> findAllByStoreName(String storeName);

  Optional<Store> findByStoreId(Long storeId);
}