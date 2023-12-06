package com.example.yummytable.domain;

import com.example.yummytable.type.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "favorit")
public class Favorit {

  @Id
  @GeneratedValue
  private Long favoritId;
  private Status favoritStatus;

  @CreatedDate
  private LocalDateTime registeredAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;

  @OneToOne
  @JoinColumn(name = "storeId")
  private Store store;

  @ManyToOne
  @JoinColumn(name = "memberId")
  private Member member;

/*  @ManyToOne
  @JoinColumn(name = "storeId")
  private Store store;

  @OneToMany(mappedBy = "board")
  private List<Comment> comments;*/

}
