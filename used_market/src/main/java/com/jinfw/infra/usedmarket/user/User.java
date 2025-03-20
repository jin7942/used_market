package com.jinfw.infra.usedmarket.user;

import com.jinfw.infra.usedmarket.common.base.BaseEntity;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.UserRoleCode;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.UserStatusCode;
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
 * user 엔티티 정의
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private String seq; // PK

  // 유저 이메일
  @Column(name = "userEmail", nullable = false, unique = true)
  private String userEmail;

  // 유저 비밀번호
  @Column(name = "userPassword", nullable = false)
  private String userPassword;

  // 유저 별명
  @Column(name = "userNickname", nullable = false)
  private String userNickname;

  // 유저 프로필 이미지 URL
  @Column(name = "userProfileImg", nullable = false)
  private String userProfileImg;

  // 유저 권한(공통 코드)
  @Enumerated(EnumType.STRING)
  @Column(name = "userRoleCode", nullable = false)
  private UserRoleCode userRoleCode = UserRoleCode.USER;

  // 유저 상태
  @Enumerated(EnumType.STRING)
  @Column(name = "userStateCode", nullable = false)
  private UserStatusCode userStateCode = UserStatusCode.ACTIVE;

}
