package com.example.yummytable.domain;

import com.example.yummytable.type.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "board")
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long boardId;
  private String title;
  private String content;
  private String keyword;
  private String password;
  @Enumerated(EnumType.STRING)
  private Status boardStatus;

  @CreatedDate
  private LocalDateTime registeredAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;


  // 게시글 - 회원
  @ManyToOne
  @JoinColumn(name = "memberId")
  private Member member;

  // 게시글 - 상점
  @ManyToOne
  @JoinColumn(name = "storeId")
  private Store store;

  // 게시글 - 댓글
  @OneToMany(mappedBy = "board")
  private List<Comment> comments;


  public Long getMemberId(Member member) {
    return member.getMemberId();
  }

  public Long getStoreId(Store store) {
    return store.getStoreId();
  }

  public String getStoreName(Store store) {
    return store.getStoreName();
  }

  public int getCapacity(Store store) {
    return store.getCapacity();
  }

  public String getStation(Store store) {
    return store.getStation();
  }
}
