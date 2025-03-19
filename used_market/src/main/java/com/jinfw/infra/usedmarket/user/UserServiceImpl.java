package com.jinfw.infra.usedmarket.user;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.UserRoleCode;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.UserStatusCode;
import com.jinfw.infra.usedmarket.common.exception.InvalidLoginException;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import com.jinfw.infra.usedmarket.common.util.UtilJwt;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder; // 비밀번호 암호화
  private final UtilDtoConverter dtoConverter; // DTO 변환 유틸
  private final UtilJwt utilJwt;

  /**
   * 로그인
   * 
   * @param reqDto 로그인 요청 데이터
   * @return 로그인 성공 시 사용자 정보 반환 (seq, userNickName)
   */
  public Map<String, Object> loginUser(UserDto reqDto) throws Exception {

    // 1. 이메일로 사용자 조회
    User user = userRepository.findByUserEmail(reqDto.getUserEmail())
        .orElseThrow(() -> new InvalidLoginException("이메일 또는 비밀번호가 올바르지 않습니다."));

    // 2. 비밀번호 검증
    if (!passwordEncoder.matches(reqDto.getUserPassword(), user.getUserPassword())) {
      throw new InvalidLoginException("이메일 또는 비밀번호가 올바르지 않습니다.");
    }

    // 3. JWT 토큰 생성
    String token = utilJwt.generateToken(user.getUserEmail());

    // 4. 로그인 성공 시 필요한 데이터만 반환
    Map<String, Object> res = new HashMap<>();
    res.put("seq", user.getSeq());
    res.put("userNickname", user.getUserNickname());
    res.put("token", token);

    return res;
  }

  /**
   * 이메일 중복 체크
   * 
   * @param reqDto
   * @return 중복 이메일 true, 없으면 false
   */
  public boolean checkUserEmail(UserDto reqDto) throws Exception {
    return userRepository.existsByUserEmail(reqDto.getUserEmail());
  }

  /**
   * 회원가입
   * 
   * @param reqDto
   * @return true or false
   */
  public boolean registerUser(UserDto reqDto) throws Exception {

    // 1. 이메일 중복 체크
    if (checkUserEmail(reqDto)) {
      throw new InvalidLoginException("이미 사용 중인 이메일입니다.");
    }

    try {
      // 2. 비밀번호 암호화
      String encryptedPassword = passwordEncoder.encode(reqDto.getUserPassword());

      // 3. DTO → 엔티티 변환
      User user = dtoConverter.toEntity(reqDto, User.class);
      user.setUserPassword(encryptedPassword);
      user.setUserRoleCode(UserRoleCode.USER);
      user.setUserStateCode(UserStatusCode.ACTIVE);

      // 4. DB에 저장
      userRepository.save(user);

      return true; // 회원가입 성공
    } catch (Exception e) {
      return false; // 회원가입 실패
    }
  }
}
