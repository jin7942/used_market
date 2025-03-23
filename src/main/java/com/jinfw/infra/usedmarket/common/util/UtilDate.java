package com.jinfw.infra.usedmarket.common.util;

import java.time.LocalDateTime;

public class UtilDate {

  /**
   * 현재 시간 반환 함수
   * 
   * @return 'YYYY-MM-DD HH:MM:SS'
   */
  public static LocalDateTime getCurrentTime() {
    return LocalDateTime.now();
  }

}
