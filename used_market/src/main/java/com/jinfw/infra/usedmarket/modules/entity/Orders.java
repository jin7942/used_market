package com.jinfw.infra.usedmarket.modules.entity;

import java.math.BigDecimal;
import com.jinfw.infra.usedmarket.common.enums.CommonCode.OrderStatusCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "orders")
public class Orders extends BaseEntity {

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

  @JoinColumn(name = "orderByerSeq", nullable = false) // FK
  private int orderByerSeq; // 판매자

  // 주문 상태 (공통 코드)
  @Enumerated(EnumType.STRING)
  @Column(name = "orderStateCode", nullable = false)
  private OrderStatusCode orderStateCode = OrderStatusCode.PENDING;

  // 결제 금액
  @Column(name = "orderPrice", nullable = false)
  private BigDecimal orderPrice;

}
