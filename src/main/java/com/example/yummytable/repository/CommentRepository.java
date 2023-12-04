package com.example.yummytable.repository;

import com.example.yummytable.domain.Comment;
import com.example.yummytable.type.CommentStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


  Optional<Comment> findByCommentIdAndCommentStatus(Long commentId, CommentStatus commentStatus);

  List<Comment> findByBoardBoardIdOrderByBoardRegisteredAtAsc(Long boardId);

  List<Comment> findByBoardBoardId(Long boardId);
}
