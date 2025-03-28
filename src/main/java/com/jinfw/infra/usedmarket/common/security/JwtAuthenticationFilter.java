package com.jinfw.infra.usedmarket.common.security;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.jinfw.infra.usedmarket.common.util.UtilJwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final UtilJwt utiljwt;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    // 인증이 필요한 요청인데 Authorization 헤더가 아예 없거나 잘못된 경우 → 차단
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response); // 비인증 요청은 그대로 진행 (permitAll 에 한함)
      return;
    }

    // 토큰 추출 및 검증
    String token = authHeader.substring(7);
    if (!utiljwt.validateToken(token)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or Expired Token");
      return;
    }


    filterChain.doFilter(request, response);
  }
}
