package com.jin7942.usedmarket.entity;

import java.time.LocalDateTime;
import com.jin7942.usedmarket.common.enums.CommonCode.NotificationTypeCode;
import com.jin7942.usedmarket.common.utill.Utill;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * notification 엔티티 정의
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "notification")
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private int seq; // PK

  @ManyToOne
  @JoinColumn(name = "user_seq", nullable = false) // FK
  private User userSeq; // 보낸사람

  // 알림 타입(공통 코드)
  @Enumerated(EnumType.STRING)
  @Column(name = "notificationTypeCode", nullable = false)
  private NotificationTypeCode notificationTypeCode;

  // 알림 메시지
  @Column(name = "notificationMessage", nullable = false, columnDefinition = "TEXT")
  private String notificationMessage;

  // 알림 수신 여부
  @Column(name = "notificationReadNY", nullable = false)
  private boolean notificationReadNY = false;

  // 알림 등록 날짜 (자동 설정)
  @Column(name = "notificationCreateDT", nullable = false, updatable = false)
  private LocalDateTime notificationCreateDT;

  // 엔티티가 처음 생성될 때 자동으로 현재 날짜 저장
  @PrePersist
  protected void onCreate() {
    this.notificationCreateDT = Utill.getCurrentTime();
  }

}
