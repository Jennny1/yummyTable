package com.example.yummytable.repository;

import com.example.yummytable.domain.Store;
import com.example.yummytable.type.Status;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

  List<Store> findAllByStoreName(String storeName);

  Optional<Store> findByStoreId(Long storeId);

  List<Store> findAllByStoreId(long storeId);

  Optional<Store> findByStoreIdAndStoreStatus(Long sotreId, Status storeStatus);


  Optional<Store> findByMemberMemberId(Long memberId);
}
