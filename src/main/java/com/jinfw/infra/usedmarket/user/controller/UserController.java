package com.jinfw.infra.usedmarket.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jinfw.infra.usedmarket.common.dto.ResponseVo;
import com.jinfw.infra.usedmarket.user.dto.UserDto;
import com.jinfw.infra.usedmarket.user.dto.UserVo;
import com.jinfw.infra.usedmarket.user.entity.User;
import com.jinfw.infra.usedmarket.user.service.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "User API", description = "회원 관련 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserServiceImpl userService;

	// 로그인 API
	@Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인합니다.")
	@PostMapping("/auth/login")
	public ResponseEntity<ResponseVo<UserVo>> loginUser(@RequestBody UserDto dto) throws Exception {

		UserVo vo = userService.loginUser(dto);
		ResponseVo<UserVo> res = new ResponseVo<>(true, "로그인 성공", vo);

		return ResponseEntity.ok(res);
	}

	// 회원가입 API
	@Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임으로 회원가입합니다.")
	@PostMapping("/auth/register")
	public ResponseEntity<ResponseVo<Boolean>> instUser(@RequestBody UserDto dto) throws Exception {

		userService.instUser(dto);
		ResponseVo<Boolean> res = new ResponseVo<>(true, "회원가입 성공", true);

		return ResponseEntity.ok(res);
	}

	// 이메일 중복 체크 API
	@Operation(summary = "이메일 중복 체크", description = "이메일이 이미 사용 중인지 확인합니다.")
	@PostMapping("/auth/check-email")
	public ResponseEntity<ResponseVo<Boolean>> checkUserEmail(@RequestBody UserDto dto) throws Exception {

		ResponseVo<Boolean> res = new ResponseVo<>(true, "이메일 중복 체크 조회 성공", !userService.checkUserEmail(dto));

		return ResponseEntity.ok(res);
	}

	// 닉네임 중복 체크 API
	@Operation(summary = "닉네임 중복 체크", description = "닉네임이 이미 사용 중인지 확인합니다.")
	@PostMapping("/auth/check-nickname")
	public ResponseEntity<ResponseVo<Boolean>> checkUserNickname(@RequestBody UserDto dto) throws Exception {

		ResponseVo<Boolean> res = new ResponseVo<>(true, "닉네임 중복 체크 조회 성공", !userService.checkUserNickname(dto));

		return ResponseEntity.ok(res);
	}

	// 유저 기본정보 조회 API
	@Operation(summary = "유저 기본 정보 조회", description = "사용자의 기본 정보를 조회 합니다.")
	@GetMapping("/info")
	public ResponseEntity<ResponseVo<Map<String, Object>>> getUserInfo(HttpServletRequest req) throws Exception {
		User user = userService.getUserInfo(req);
		Map<String, Object> result = new HashMap<>();

		result.put("userEmail", user.getUserEmail());
		result.put("userNickname", user.getUserNickname());
		result.put("userProfileImg", user.getUserProfileImg());

		ResponseVo<Map<String, Object>> res = new ResponseVo<>(true, "사용자 정보 조회 성공", result);

		return ResponseEntity.ok(res);
	}

}
