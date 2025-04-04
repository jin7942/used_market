package com.jinfw.infra.usedmarket.notification.entity;

import com.jinfw.infra.usedmarket.common.base.BaseEntity;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.NotificationTypeCode;
import com.jinfw.infra.usedmarket.item.entity.Item;
import com.jinfw.infra.usedmarket.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Notification extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private int seq; // PK

  @ManyToOne
  @JoinColumn(name = "user_seq", nullable = false) // FK
  private User userSeq;

  @ManyToOne
  @JoinColumn(name = "item_seq", nullable = true) // FK
  private Item itemSeq;

  // 알림 타입(공통 코드)
  @Enumerated(EnumType.STRING)
  @Column(name = "notificationTypeCode", nullable = false)
  private NotificationTypeCode notificationTypeCode;

  // 알림 메시지
  @Column(name = "notificationMessage", nullable = false, columnDefinition = "TEXT")
  private String notificationMessage;

  // 알림 수신 여부
  @Column(name = "notificationIsReadNY", nullable = false)
  private boolean notificationIsReadNY = false;

}
