package com.jinfw.infra.usedmarket.modules.entity;

import java.time.LocalDateTime;
import com.jinfw.infra.usedmarket.common.utill.Utill;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BaseEntity
 * 
 * 날짜 생성, 업데이트 함수 정의
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {
  @Column(name = "createDT", nullable = false, updatable = false)
  private LocalDateTime createDT; // 생성 날짜

  @Column(name = "updateDT", nullable = false)
  private LocalDateTime updateDT; // 업데이트 날짜

  /**
   * 생성 날짜 지정 함수
   * 
   * 현재 시간으로 생성 및 업데이트 날짜 지정 객체 생성시 자동으로 실행
   */
  @PrePersist
  protected void onCreate() {
    if (this.createDT == null && this.updateDT == null) {
      this.createDT = Utill.getCurrentTime();
      this.updateDT = Utill.getCurrentTime();
    }
  }

  /**
   * 업데이트 발생시 자동으로 실행
   */
  @PreUpdate
  protected void onUpdate() {
    this.updateDT = Utill.getCurrentTime();
  }
}
