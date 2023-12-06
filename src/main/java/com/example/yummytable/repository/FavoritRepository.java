package com.example.yummytable.repository;

import com.example.yummytable.domain.Favorit;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritRepository extends JpaRepository<Favorit, Long> {


  Optional<Favorit> findByStoreStoreIdAndMemberMemberId(Long storeId, Long memberId);
}
