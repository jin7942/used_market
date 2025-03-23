package com.jinfw.infra.usedmarket.common.img.entity;

import com.jinfw.infra.usedmarket.common.base.BaseEntity;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.ImageUploadStatusCode;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.ImageUploadTypeCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * imgupload 엔티티 정의
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "imgupload")
public class Imgupload extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private int seq; // PK

  @Column(name = "imgPseq")
  private int imgPseq; // 등록한 유저

  @Column(name = "imgUploadSort")
  private int imgUploadSort; // 정렬 순서

  // 이미지 파일명
  @Column(name = "imgUploadName", nullable = false, length = 255)
  private String imgUploadName;

  // uuid 파일명
  @Column(name = "imgUploadUuidName", nullable = false, length = 255)
  private String imgUploadUuidName;

  // 이미지 저장 경로
  @Column(name = "imgUploadPath", nullable = false, length = 255)
  private String imgUploadPath;

  // 이미지 확장명
  @Column(name = "imgUploadExt", nullable = false, length = 255)
  private String imgUploadExt;

  // 이미지 파일 사이즈(byte)
  @Column(name = "imgUploadSize", nullable = false)
  private String imgUploadSize;

  // 이미지 타입(공통 코드)
  @Enumerated(EnumType.STRING)
  @Column(name = "imgUploadTypeCode", nullable = false)
  private ImageUploadTypeCode imgUploadTypeCode;

  // 이미지 상태(공통코드)
  @Enumerated(EnumType.STRING)
  @Column(name = "imgUploadStateCode", nullable = false)
  private ImageUploadStatusCode imgUploadStateCode = ImageUploadStatusCode.ENABLED;

}
