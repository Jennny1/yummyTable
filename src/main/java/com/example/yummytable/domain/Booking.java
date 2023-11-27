package com.example.yummytable.domain;

import com.example.yummytable.type.BoardStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
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
public class Booking {

  // 게시글 관련 정보
  @Id
  @GeneratedValue
  private Long boardId;
  private String title;
  private String content;
  private String password;
  @Enumerated(EnumType.STRING)
  private BoardStatus boardStatus;

  @CreatedDate
  private LocalDateTime registeredAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;

  // 식당 관련 정보
  private String storeName;
  private String keyword;
  private Double locationX;
  private Double locationY;
  private String menu;
  private int capacity;

}