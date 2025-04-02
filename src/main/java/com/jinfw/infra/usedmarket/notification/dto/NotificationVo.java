package com.jinfw.infra.usedmarket.notification.dto;

import java.time.LocalDateTime;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.NotificationTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationVo {

  // 알림 메시지
  private String notificationMessage;

  // 알림 타입(공통 코드)
  private NotificationTypeCode notificationTypeCode;

  // 알림 수신 여부
  private boolean notificationReadNY = false;

  private LocalDateTime updateDT;

}
