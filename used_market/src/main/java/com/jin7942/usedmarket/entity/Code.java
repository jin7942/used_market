package com.jin7942.usedmarket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Code 엔티티 정의
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "code")
public class Code {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private int seq; // PK

  @ManyToOne
  @JoinColumn(name = "codeGroup_seq", nullable = false) // FK
  private CodeGroup codeGroupSeq; // 코드 그룹

  // 코드 이름
  @Column(name = "codeName", nullable = false)
  private String codeName;

  // 코드 설명
  @Column(name = "codeDescription", nullable = false)
  private String codeDescription;

  // 코드 사용 여부
  @Column(name = "codeUseNY", nullable = false)
  private boolean codeUseNY = true; // 기본값은 true 설정

}
