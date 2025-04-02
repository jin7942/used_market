package com.jinfw.infra.usedmarket.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import com.jinfw.infra.usedmarket.common.exception.InvalidLoginException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class UtilJwt {

  // TODO: 비밀키는 외부에서 주입받도록 변경
  private static final String SECRET_KEY = "SEXY_GUY_OF_THE_WORLD_23894723894820"; // 토큰 서명 키
  private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 1; // 1시간

  private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

  /**
   * JWT 토큰 생성
   * 
   * @param userEmail 유저 이메일
   * @return JWT 토큰
   */
  public String generateToken(String userEmail) {
    return Jwts.builder().subject(userEmail).issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(key).compact();
  }

  /**
   * JWT 토큰에서 사용자 이메일 추출
   * 
   * @param token JWT 토큰
   * @return userEmail 유저 이메일
   */
  public String extractUserEmail(String token) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
  }

  /**
   * http 헤더에서 사용자 이메일 추출
   * 
   * @param HttpServletRequest
   * @return
   */
  public String extractUserEmailFromRequest(HttpServletRequest request) {
    try {
      String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        return extractUserEmail(token); // 여기서 Exception 발생 가능
      }
      throw new InvalidLoginException("토큰이 없습니다.");
    } catch (Exception e) {
      throw new InvalidLoginException("토큰 파싱에 실패했습니다.");
    }
  }

  /**
   * JWT 토큰 유효성 검증
   * 
   * @param token JWT 토큰
   * @return 유효 여부 (true/false)
   */
  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
