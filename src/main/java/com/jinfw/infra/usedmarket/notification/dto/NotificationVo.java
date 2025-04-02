package com.jinfw.infra.usedmarket.notification.dto;

import java.time.LocalDateTime;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.NotificationTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationVo {

  private int seq;

  // 상품 정보
  private int itemSeq;
  private String itemTitle;

  // 유저 닉네임
  private String userNickname;

  // 알림 메시지
  private String notificationMessage;

  // 알림 타입(공통 코드)
  private NotificationTypeCode notificationTypeCode;

  // 알림 수신 여부
  private boolean notificationIsReadNY = false;

  private LocalDateTime updateDT;

}
