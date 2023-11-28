package com.example.yummytable.domain;

import com.example.yummytable.type.StoreStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "store")
public class Store {

  // 식당 관련 정보
  @Id
  @GeneratedValue
  private long storeId;
  private String storeName;
  private String keyword;

  private Double locationX;
  private Double locationY;

  private String menu;
  @Enumerated(EnumType.STRING)
  private StoreStatus storeStatus;

  private int capacity;
  private int numberOfApplicants;


  @CreatedDate
  private LocalDateTime registeredAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;
  private LocalDateTime unregisteredAt;

  // 예약정보
  @OneToMany
  @JoinColumn(name = "storeID")
  private List<Booking> booking;

}
