package com.jinfw.infra.usedmarket.modules.entity;

import java.math.BigDecimal;
import com.jinfw.infra.usedmarket.common.enums.CommonCode.ItemStatusCode;
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
 * item 엔티티 정의
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "item")
public class Item extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private int seq; // PK

  @ManyToOne
  @JoinColumn(name = "user_seq", nullable = false) // FK
  private User userSeq; // 판매자

  // 상품 제목
  @Column(name = "itemTitle", nullable = false, length = 255)
  private String itemTitle;

  // 상품 설명
  @Column(name = "itemDescription", columnDefinition = "TEXT")
  private String itemDescription;

  // 상품 가격
  @Column(name = "itemPrice", nullable = false)
  private BigDecimal itemPrice;

  // 상품 상태(공통 코드)
  @Enumerated(EnumType.STRING)
  @Column(name = "itemStateCode", nullable = false)
  private ItemStatusCode itemStateCode = ItemStatusCode.SELLING;

}
