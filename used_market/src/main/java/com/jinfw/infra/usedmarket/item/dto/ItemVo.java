package com.jinfw.infra.usedmarket.item.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.jinfw.infra.usedmarket.user.entity.User;
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
  // private String itemImgUrl;

  // 이미지 정보
  private String imgUploadPath;
  private String imgUploadUuidname;
  private String imgUploadExt;

}
