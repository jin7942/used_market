package com.jinfw.infra.usedmarket.item.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Item request Dto
 */
@Getter
@Setter
public class ItemDto {

  private int userSeq; // 등록한 유저

  // 상품 제목
  private String itemTitle;

  // 상품 설명
  private String itemDescription;

  // 상품 가격
  private int itemPrice;

}
