package com.jinfw.infra.usedmarket.item.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemVo {

  private int seq; // PK
  private String userNickname; // 판매자

  // 상품 제목
  private String itemTitle;

  // 상품 설명
  private String itemDescription;

  // 상품 가격
  private int itemPrice;

  // 업데이트 날짜
  private LocalDateTime updateDT;

  // 이미지 정보
  private String imgUploadPath;
  private String imgUploadUuidName;
  private String imgUploadExt;

}
