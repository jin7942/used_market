package com.jinfw.infra.usedmarket.orders.entity;

import com.jinfw.infra.usedmarket.item.entity.Item;
import com.jinfw.infra.usedmarket.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * wishlist 엔티티 정의
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "wishlist")
public class Wishlist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private int seq; // PK

  @ManyToOne
  @Column(name = "user_seq")
  private User userSeq; // 찜한 유저 FK

  @ManyToOne
  @Column(name = "item_seq")
  private Item itemSeq; // 찜한 상품 FK



}
