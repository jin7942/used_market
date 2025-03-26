package com.jinfw.infra.usedmarket.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * API 응답 형식 통일을 위한 응답용 객체
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVo<T> {
  private boolean success;
  private String message;
  private T data;


}
