package com.jinfw.infra.usedmarket.common.security;

import java.io.IOException;
import java.util.List;

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

	private static final List<String> WHITELIST = List.of("/api/users/auth/login", // 로그인
			"/api/users/auth", "/api/items", "/swagger-ui.html");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String path = request.getRequestURI();

		System.out.println("요청 경로: " + request.getRequestURI());
		System.out.println("Authorization 헤더: " + authHeader);
		System.out.println("필터 통과 여부: " + (WHITELIST.stream().anyMatch(path::startsWith)));

		// 화이트리스트는 필터 통과
		if (WHITELIST.stream().anyMatch(path::startsWith)) {
			filterChain.doFilter(request, response);
			return;
		}

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
