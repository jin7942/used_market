package com.jin7942.usedmarket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * codeGroup 엔티티 정의
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "codeGroup")
public class CodeGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private int seq; // PK

  // 코드 그룹 이름
  @Column(name = "codeGroupName")
  private String codeGroupName;

}

// TODO: DB에 공통코드 데이터 입력
