package com.jinfw.infra.usedmarket.img.dto;

import com.jinfw.infra.usedmarket.common.constants.CommonCode.ImageUploadTypeCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImguploadVo {

  private int imgPseq; // 등록한 유저

  // 정렬 순서
  private int imgSort;

  // 이미지 파일명
  private String imgUploadName;

  // uuid 파일명
  private String imgUploadUuidName;

  // 이미지 저장 경로
  private String imgUploadPath;

  // 이미지 확장명
  private String imgUploadExt;

  // 이미지 타입(공통 코드)
  private ImageUploadTypeCode imgUploadTypeCode;

  public ImguploadVo(int imgPseq, int imgSort, String imgUploadName, String imgUploadUuidName,
      String imgUploadPath, String imgUploadExt, ImageUploadTypeCode imgUploadTypeCode) {
    this.imgPseq = imgPseq;
    this.imgSort = imgSort;
    this.imgUploadName = imgUploadName;
    this.imgUploadUuidName = imgUploadUuidName;
    this.imgUploadPath = imgUploadPath;
    this.imgUploadExt = imgUploadExt;
    this.imgUploadTypeCode = imgUploadTypeCode;
  }


}
