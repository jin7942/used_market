package com.jin7942.usedmarket.common.utill;

import java.time.LocalDateTime;

public class Utill {

  /**
   * 현재 시간 반환 함수
   * 
   * @return 'YYYY-MM-DD HH:MM:SS'
   */
  public static LocalDateTime getCurrentTime() {
    return LocalDateTime.now();
  }

}
