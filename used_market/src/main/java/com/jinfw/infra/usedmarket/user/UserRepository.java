package com.jinfw.infra.usedmarket.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  // 이메일 중복 체크
  boolean existsByUserEmail(String userEmail);

  // 이메일을 기준으로 사용자 찾기
  Optional<User> findByUserEmail(String email);

}
