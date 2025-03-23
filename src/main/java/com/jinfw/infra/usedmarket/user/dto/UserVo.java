package com.jinfw.infra.usedmarket.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserVo {

  // PK
  private int seq;

  // 유저 별명
  private String userNickname;

  // 토큰
  private String token;

}
