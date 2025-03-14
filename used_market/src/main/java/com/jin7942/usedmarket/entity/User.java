package com.jin7942.usedmarket.entity;

import java.time.LocalDateTime;
import com.jin7942.usedmarket.common.utill.Utill;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private int seq; // PK

  // 유저 이메일
  @Column(name = "userEmail", nullable = false)
  private String userEmail;

  // 유저 비밀번호
  @Column(name = "userPassword", nullable = false)
  private String userPassword;

  // 유저 별명
  @Column(name = "userNickName", nullable = false)
  private String userNickName;

  // 유저 프로필 이미지 URL
  // TODO: 이미지 처리기능 만들어야됨
  @Column(name = "userProfileImg")
  private String userProfileImg;

  // 유저 생성 날짜
  @Column(name = "userCreateDT", nullable = false)
  private LocalDateTime userCreateDT;

  // 유저 수정 날짜
  @Column(name = "userUpdateDT", nullable = false)
  private LocalDateTime userUpdateDT;

  // 엔티티가 처음 생성될 때 자동으로 현재 날짜 저장
  @PrePersist
  protected void onCreate() {
    this.userCreateDT = Utill.getCurrentTime();
  }

  // 엔티티가 업데이트될 때 자동으로 현재 날짜 저장
  @PreUpdate
  protected void onUpdate() {
    this.userUpdateDT = Utill.getCurrentTime();
  }

  /**
   * 공통 코드 TODO: 공통 코드 ERUM 설정 해야됨
   */
  // 유저 권한
  @Column(name = "userRoleCode", nullable = false)
  private int userRoleCode;

  // 유저 상태
  @Column(name = "userStateCode")
  private int userStateCode;


}
