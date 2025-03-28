package com.jinfw.infra.usedmarket.img.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImguploadVo {

  private int imgPseq; // 등록한 유저

  // 정렬 순서
  private int imgUploadSort;

  // 이미지 파일명
  private String imgUploadName;

  // uuid 파일명
  private String imgUploadUuidName;

  // 이미지 저장 경로
  private String imgUploadPath;

  // 이미지 확장명
  private String imgUploadExt;

  // 이미지 타입(공통 코드)
  private String imgUploadTypeCode;

}
