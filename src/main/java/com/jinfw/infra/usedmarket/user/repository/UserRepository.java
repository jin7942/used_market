package com.jinfw.infra.usedmarket.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jinfw.infra.usedmarket.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  // 이메일 중복 체크
  boolean existsByUserEmail(String userEmail);

  // 닉네임을 중복 체크
  boolean existsByUserNickname(String userNickname);

  // 이메일을 기준으로 사용자 찾기
  Optional<User> findByUserEmail(String email);

}
