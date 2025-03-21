package com.jinfw.infra.usedmarket.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * UserDto 유저 요청용 객체
 */
@Getter
@Setter
public class UserDto {

  // 유저 이메일
  private String userEmail;

  // 유저 비밀번호
  private String userPassword;

  // 유저 별명
  private String userNickname;

  // 유저 프로필 이미지 URL
  private String userProfileImg;


}
