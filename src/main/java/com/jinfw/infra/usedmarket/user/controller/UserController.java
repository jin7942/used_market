package com.jinfw.infra.usedmarket.user.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jinfw.infra.usedmarket.user.dto.UserDto;
import com.jinfw.infra.usedmarket.user.dto.UserVo;
import com.jinfw.infra.usedmarket.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "User API", description = "유저 관련 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserServiceImpl userService;

  /**
   * 로그인 API
   * 
   * @param dto 로그인 요청 데이터
   * @return JWT 토큰 및 사용자 정보(seq, userNickname) 반환
   */
  @PostMapping("/auth/login")
  public ResponseEntity<UserVo> loginUser(@RequestBody UserDto dto) throws Exception {
    return ResponseEntity.ok(userService.loginUser(dto));
  }

  /**
   * 회원가입 API
   * 
   * @param dto 회원가입 요청 데이터
   * @return { "success": true } 또는 false
   */
  @PostMapping("/auth/register")
  public ResponseEntity<Map<String, Boolean>> instUser(@RequestBody UserDto dto) throws Exception {
    return ResponseEntity.ok(Map.of("success", userService.instUser(dto)));
  }

  /**
   * 이메일 중복 체크 API
   * 
   * @param dto 이메일 포함된 JSON 바디
   * @return { "available": true } or false
   */
  @PostMapping("/auth/check-email")
  public ResponseEntity<Map<String, Boolean>> checkUserEmail(@RequestBody UserDto dto)
      throws Exception {
    return ResponseEntity.ok(Map.of("available", !userService.checkUserEmail(dto)));
  }

  /**
   * 닉네임 중복 체크 API
   * 
   * @param dto 닉네임 포함된 JSON 바디
   * @return { "available": true } or false
   */
  @PostMapping("/auth/check-nickname")
  public ResponseEntity<Map<String, Boolean>> checkUserNickname(@RequestBody UserDto dto)
      throws Exception {
    return ResponseEntity.ok(Map.of("available", !userService.checkUserNickname(dto)));
  }
}
