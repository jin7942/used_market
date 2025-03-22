package com.jinfw.infra.usedmarket.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;

/**
 * 스프링 부트 시큐리티 설정 파일
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()) // CSRF 비활성화
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
        // 모든 페이지 인증없이 접근 가능(개발용)
        .authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll()
            .requestMatchers("/api/admin/**").hasRole("ADMIN") // 관리자 전용
            .anyRequest().authenticated() // 나머지 요청은 인증 필요
        ).httpBasic(httpBasic -> httpBasic.disable()) // 기본 로그인 폼 비활성화
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // 비밀번호 암호화
  }

}
