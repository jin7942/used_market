package com.jin7942.usedmarket.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품 엔티티 정의
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "item")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private int seq; // PK

  @ManyToOne
  @JoinColumn(name = "user_seq", nullable = false) // FK
  private User seller;

  // 상품 제목
  @Column(name = "itemTitle", nullable = false, length = 255)
  private String itemTitle;

  // 상품 설명
  @Column(name = "itemDescription", columnDefinition = "TEXT")
  private String itemDescription;

  // 상품 가격
  @Column(name = "itemPrice", nullable = false)
  private BigDecimal itemPrice;

  // 상품 상태
  @Column(name = "itemStateCode", nullable = false)
  private int itemStateCode;

  // 등록 날짜 (자동 설정)
  @Column(name = "itemCreateDT", updatable = false)
  private LocalDateTime itemCreateDT;

  // 수정 날짜 (자동 설정)
  @Column(name = "itemUpdateDT")
  private LocalDateTime itemUpdateDT;

  // 엔티티가 처음 생성될 때 자동으로 현재 날짜 저장
  @PrePersist
  protected void onCreate() {
    this.itemCreateDT = LocalDateTime.now();
  }

  // 엔티티가 업데이트될 때 자동으로 현재 날짜 저장
  @PreUpdate
  protected void onUpdate() {
    this.itemUpdateDT = LocalDateTime.now();
  }

}
