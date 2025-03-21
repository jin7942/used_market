package com.jinfw.infra.usedmarket.user;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserServiceImpl userService;

  /**
   * 로그인 API
   * 
   * @param reqDto 로그인 요청 데이터
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
   * @param reqDto 회원가입 요청 데이터
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
   * @param reqDto 체크할 이메일 주소 (쿼리 파라미터)
   * @return 있으면 true, 없으면 false
   */
  @GetMapping("/auth/checkUserEmail")
  public ResponseEntity<Boolean> checkUserEmail(@RequestParam UserDto dto) throws Exception {

    boolean res = userService.checkUserEmail(dto);

    return ResponseEntity.ok(res);
  }
}
