package com.jinfw.infra.usedmarket.user.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jinfw.infra.usedmarket.user.dto.UserDto;
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
   * @param dto 로그인 요청 데이터 (email, password 포함)
   * @return { "token": "...", "seq": "123", "userNickname": "닉네임" }
   */
  @PostMapping("/auth/login")
  public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserDto dto) throws Exception {
    Map<String, Object> res = userService.loginUser(dto);
    return ResponseEntity.ok(res);
  }

  /**
   * 회원가입 API
   *
   * @param dto 회원가입 요청 데이터 (email, password, nickname 포함)
   * @return { "success": true } 또는 { "success": false }
   */
  @PostMapping("/auth/register")
  public ResponseEntity<Map<String, Boolean>> instUser(@RequestBody UserDto dto) throws Exception {
    boolean result = userService.instUser(dto);

    Map<String, Boolean> res = new HashMap<>();
    res.put("success", result);

    return ResponseEntity.ok(res);
  }

  /**
   * 이메일 중복 체크 API
   *
   * @param dto 이메일을 포함한 UserDto (JSON body)
   * @return { "available": true } - 사용 가능
   */
  @PostMapping("/auth/check-email")
  public ResponseEntity<Map<String, Boolean>> checkUserEmail(@RequestBody UserDto dto)
      throws Exception {
    boolean exists = userService.checkUserEmail(dto); // true면 이미 존재
    Map<String, Boolean> result = new HashMap<>();
    result.put("available", !exists);
    return ResponseEntity.ok(result);
  }

  /**
   * 닉네임 중복 체크 API
   *
   * @param dto 닉네임을 포함한 UserDto (JSON body)
   * @return { "available": true } - 사용 가능
   */
  @PostMapping("/auth/check-nickname")
  public ResponseEntity<Map<String, Boolean>> checkUserNickname(@RequestBody UserDto dto)
      throws Exception {
    boolean exists = userService.checkUserNickname(dto); // true면 이미 존재
    Map<String, Boolean> result = new HashMap<>();
    result.put("available", !exists);
    return ResponseEntity.ok(result);
  }
}
