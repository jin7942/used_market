package com.jinfw.infra.usedmarket.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.UserRoleCode;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.UserStatusCode;
import com.jinfw.infra.usedmarket.common.exception.InvalidLoginException;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import com.jinfw.infra.usedmarket.common.util.UtilJwt;
import com.jinfw.infra.usedmarket.user.dto.UserDto;
import com.jinfw.infra.usedmarket.user.dto.UserVo;
import com.jinfw.infra.usedmarket.user.entity.User;
import com.jinfw.infra.usedmarket.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder; // 비밀번호 암호화
  private final UtilDtoConverter dtoConverter; // DTO 변환 유틸
  private final UtilJwt utilJwt;

  /**
   * http헤더에서 사용자 정보 추출 함수
   * 
   * @param req http 헤더
   * @return User 객체
   */
  public User getUserFromRequest(HttpServletRequest req) {
    String email = utilJwt.extractUserEmailFromRequest(req);
    return userRepository.findByUserEmail(email)
        .orElseThrow(() -> new InvalidLoginException("사용자 없음"));
  }

  /**
   * 로그인
   * 
   * @param dto 로그인 요청 데이터
   * @return 로그인 성공 시 사용자 정보 반환 (seq, userNickName)
   */
  public UserVo loginUser(UserDto dto) throws Exception {

    // 1. 이메일로 사용자 조회
    User user = userRepository.findByUserEmail(dto.getUserEmail())
        .orElseThrow(() -> new InvalidLoginException("이메일 또는 비밀번호가 올바르지 않습니다."));

    // 2. 비밀번호 검증
    if (!passwordEncoder.matches(dto.getUserPassword(), user.getUserPassword())) {
      throw new InvalidLoginException("이메일 또는 비밀번호가 올바르지 않습니다.");
    }

    // 3. JWT 토큰 생성
    String token = utilJwt.generateToken(user.getUserEmail());

    // 4. 로그인 성공 시 필요한 데이터만 반환
    return new UserVo(user.getSeq(), user.getUserNickname(), token);
  }

  /**
   * 이메일 중복 체크
   * 
   * @param dto
   * @return 있으면 true, 없으면 false
   */
  public boolean checkUserEmail(UserDto dto) throws Exception {
    return userRepository.existsByUserEmail(dto.getUserEmail());
  }

  /**
   * 닉네임 중복 체크
   * 
   * @param dto
   * @return 있으면 true, 없으면 false
   */
  public boolean checkUserNickname(UserDto dto) throws Exception {
    return userRepository.existsByUserNickname(dto.getUserNickname());
  }

  /**
   * 회원가입
   * 
   * @param dto
   * @return true or false
   */
  @Transactional
  public void instUser(UserDto dto) throws Exception {

    // 1. 이메일 중복 체크
    if (checkUserEmail(dto)) {
      throw new InvalidLoginException("이미 사용 중인 이메일입니다.");
    }

    // 2. 비밀번호 암호화
    String encryptedPassword = passwordEncoder.encode(dto.getUserPassword());

    // 3. Dto to Entity
    User user = dtoConverter.toEntity(dto, User.class);
    user.setUserPassword(encryptedPassword);
    user.setUserRoleCode(UserRoleCode.USER);
    user.setUserStateCode(UserStatusCode.ACTIVE);

    // 4. DB에 저장
    User res = userRepository.save(user);
    System.out.println("svae : " + res);

  }

  /**
   * 유저 기본 정보 조회
   * 
   * @param req http 헤더
   * @return User 객체
   * @throws Exception
   */
  public User getUserInfo(HttpServletRequest req) throws Exception {
    return getUserFromRequest(req);
  }
}
