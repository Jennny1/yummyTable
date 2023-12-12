package com.example.yummytable.repository;

import com.example.yummytable.domain.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

  Optional<Board> findByBoardId(Long boardId);

  List<Board> findAllByKeywordLike(String keyword);

  List<Board> findAllByStoreStoreNameLike(String storeName);

  List<Board> findAllByStoreStationLike(String station);
}
