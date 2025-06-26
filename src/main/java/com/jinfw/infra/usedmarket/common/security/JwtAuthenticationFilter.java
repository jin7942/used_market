package com.jinfw.infra.usedmarket.common.security;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
			"/api/users/auth", "/api/items", "/swagger-ui.html", "/swagger-ui/**", "/swagger-ui/**", "/v3/api-docs/**",
			"/docs/**", "/docs");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String path = request.getRequestURI();
		String token = null;

		// 화이트리스트는 필터 통과
		if (WHITELIST.stream().anyMatch(path::startsWith)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 1. 헤더로 먼저 시도
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
		}

		// 2. 헤더가 없고, 특정 SSE 경로라면 쿼리 파라미터에서 시도
		if (token == null && request.getRequestURI().startsWith("/api/notification")) {
			token = request.getParameter("token");
		}

		// 화이트리스트는 필터 통과
		if (WHITELIST.stream().anyMatch(path::startsWith)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 3. 토큰 없으면 실패
		if (token == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Token");
			return;
		}

		// 4. 검증
		if (!utiljwt.validateToken(token)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or Expired Token");
			return;
		}

		// 5. 인증 객체 주입
		String userEmail = utiljwt.extractUserEmail(token);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userEmail, null,
				List.of());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 6. 다음 필터로 넘김
		filterChain.doFilter(request, response);
	}
}
