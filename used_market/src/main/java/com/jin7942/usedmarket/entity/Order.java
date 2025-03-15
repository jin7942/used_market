package com.jin7942.usedmarket.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.jin7942.usedmarket.common.enums.CommonCode.OrderStatusCode;
import com.jin7942.usedmarket.common.utill.Utill;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * order 엔티티 정의
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order")
public class Order {

  // TODO: DB 수정, 판매자 정보 등록
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private int seq; // PK

  @ManyToOne
  @JoinColumn(name = "user_seq", nullable = false) // FK
  private User userSeq; // 주문한 사용자

  @ManyToOne
  @JoinColumn(name = "item_seq", nullable = false) // FK
  private Item itemSeq; // 주문한 상품

  // 주문 상태 (공통 코드)
  @Enumerated(EnumType.STRING)
  @Column(name = "orderStateCode", nullable = false)
  private OrderStatusCode orderStateCode = OrderStatusCode.PENDING;

  // 결제 금액
  @Column(name = "orderPrice", nullable = false)
  private BigDecimal orderPrice;

  // 주문 생성 및 수정 날짜
  @Column(name = "orderCreateDT", nullable = false, updatable = false)
  private LocalDateTime orderCreateDT;

  @Column(name = "orderUpdateDT", nullable = false)
  private LocalDateTime orderUpdateDT;

  @PrePersist
  protected void onCreate() {
    this.orderCreateDT = Utill.getCurrentTime();
    this.orderUpdateDT = Utill.getCurrentTime();
  }

  @PreUpdate
  protected void onUpdate() {
    this.orderUpdateDT = Utill.getCurrentTime();
  }

}
