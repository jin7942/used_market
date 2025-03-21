package com.jinfw.infra.usedmarket.user.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
   * @param dto 로그인 요청 데이터
   * @return JWT 토큰 및 사용자 정보(seq, userNickname) 반환
   */
  @PostMapping("/auth/login")
  public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserDto dto) throws Exception {

    Map<String, Object> res = userService.loginUser(dto);

    return ResponseEntity.ok(res);
  }

  /**
   * 회원가입 API
   * 
   * @param dto 회원가입 요청 데이터
   * @return true or false
   */
  @PostMapping("/auth/register")
  public ResponseEntity<Boolean> instUser(@RequestBody UserDto dto) throws Exception {

    boolean res = userService.instUser(dto);

    return ResponseEntity.ok(res);
  }

  /**
   * 이메일 중복 체크 API
   * 
   * @param dto 체크할 이메일 주소 (쿼리 파라미터)
   * @return 있으면 true, 없으면 false
   */
  @GetMapping("/auth/checkUserEmail")
  public ResponseEntity<Boolean> checkUserEmail(@RequestParam UserDto dto) throws Exception {

    boolean res = userService.checkUserEmail(dto);

    return ResponseEntity.ok(res);
  }

  /**
   * 유저 닉네임 중복 체크 API
   * 
   * @param dto 체크할 유저 닉네임 (쿼리 파라미터)
   * @return 있으면 true, 없으면 false
   */
  @GetMapping("/auth/checkUserNickname")
  public ResponseEntity<Boolean> checkUserNickname(@RequestParam UserDto dto) throws Exception {

    boolean res = userService.checkUserNickname(dto);

    return ResponseEntity.ok(res);
  }
}
