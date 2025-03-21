package com.jinfw.infra.usedmarket.item;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.jinfw.infra.usedmarket.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemVo {

  private int seq; // PK
  private User userSeq; // 판매자

  // 상품 제목
  private String itemTitle;

  // 상품 설명
  private String itemDescription;

  // 상품 가격
  private BigDecimal itemPrice;

  // 업데이트 날짜
  private LocalDateTime updateDT;

  // 대표 이미지 URL
  private String itemImgUrl;

  // item을 파라미터로 받는 생성자
  public ItemVo(Item item) {
    this.userSeq = item.getUserSeq();
    this.itemTitle = item.getItemTitle();
    this.itemPrice = item.getItemPrice();
    this.updateDT = item.getUpdateDT();
  }

}
