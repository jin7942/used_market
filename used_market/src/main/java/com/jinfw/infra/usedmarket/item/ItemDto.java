package com.jinfw.infra.usedmarket.item;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Item request Dto
 */
@Getter
@Setter
public class ItemDto {

  // 상품 제목
  private String itemTitle;

  // 상품 설명
  private String itemDescription;

  // 상품 가격
  private BigDecimal itemPrice;

}
