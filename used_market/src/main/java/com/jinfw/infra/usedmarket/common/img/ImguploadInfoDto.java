package com.jinfw.infra.usedmarket.common.img;

import com.jinfw.infra.usedmarket.common.constants.CommonCode.ImageUploadTypeCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImguploadInfoDto {

  private String imgPseq; // 등록한 유저

  // 정렬 순서
  private String imgSort;

  // 이미지 파일명
  private String imgUploadName;

  // uuid 파일명
  private String imgUploadUuidName;

  // 이미지 저장 경로
  private String imgUploadPath;

  // 이미지 확장명
  private String imgUploadExt;

  // 이미지 파일 사이즈(byte)
  private String imgUploadSize;

  // 이미지 타입(공통 코드)
  private ImageUploadTypeCode imgUploadTypeCode;

}
