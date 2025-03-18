package com.jinfw.infra.usedmarket.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 스프링 부트 시큐리티 설정 파일
 */
@Configuration
public class SecurityConfig {
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()) // 최신 방식으로 CSRF 비활성화
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
        // 로그인, 회원가입, 메인페이지는 인증없이 접근
        .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**", "/api/main").permitAll()

            .requestMatchers("/api/admin/**").hasRole("ADMIN") // 관리자 전용
            .anyRequest().authenticated() // 나머지 요청은 인증 필요
        ).httpBasic(httpBasic -> httpBasic.disable()); // 기본 로그인 폼 비활성화

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // 비밀번호 암호화
  }

}
